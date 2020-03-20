import codec.CustomCodecProvider
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import dev.morphia.Morphia
import dev.morphia.mapping.MapperOptions
import dev.morphia.query.DefaultQueryFactory
import external.Guild
import external.Role
import external.Snowflake
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import property.GuildProperty
import property.GuildPropertyManager
import property.RolePropertyValue
import kotlin.test.assertEquals


object MorphiaGenericMapTest : Spek({
    describe("Morphia") {
        it("should load back a Role object") {
            val mapperOptions = MapperOptions
                .builder()
                .storeEmpties(true)
                .storeNulls(true)
                .build()
            val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(CustomCodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry()
            )

            val datastore = Morphia.createDatastore(
                MongoClients.create(
                    MongoClientSettings
                        .builder()
                        .codecRegistry(codecRegistry)
                        .build()
                ),
                "morphia_generic_map",
                mapperOptions
            )
            datastore.apply {
                queryFactory = DefaultQueryFactory()
                mapper.mapPackage("property")
            }
            val guildPropertiesDAO = MorphiaGuildPropertiesDAO(datastore)

            val guildSample = Guild(Snowflake.of(269552852760920068))
            val roleSample = Role(Snowflake.of(370194687635619842))
            val propertyManager = GuildPropertyManager(guildPropertiesDAO)

            // Load guild properties
            // Load and persist default values
            propertyManager.load(guildSample)

            // Update the AUTOROLE property
            propertyManager.set(guildSample, GuildProperty.AUTOROLE, RolePropertyValue(roleSample))

            // Reload value from database
            propertyManager.load(guildSample)

            // Get the decoded RolePropertyValue
            val decodedRolePropertyValue = propertyManager.get(guildSample, GuildProperty.AUTOROLE) as RolePropertyValue

            // Should be a Role object
            assertEquals(RolePropertyValue(roleSample), decodedRolePropertyValue)
        }
    }
})