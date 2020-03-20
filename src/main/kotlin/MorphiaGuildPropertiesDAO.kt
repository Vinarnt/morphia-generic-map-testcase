import dev.morphia.Datastore
import dev.morphia.query.experimental.filters.Filters.eq
import external.Guild
import org.bson.types.ObjectId
import property.GuildProperties
import property.GuildProperty


class MorphiaGuildPropertiesDAO(private val datastore: Datastore) {
    fun find(id: ObjectId): GuildProperties? =
            datastore.find(GuildProperties::class.java).filter(eq("_id", id)).first()

    fun findFor(guild: Guild): GuildProperties? =
            datastore.find(GuildProperties::class.java).filter(eq("guild_id", guild)).first()

    fun insert(entity: GuildProperties): GuildProperties =
        datastore.save(entity)

    fun updateProperty(entity: GuildProperties, property: GuildProperty, value:Any?): GuildProperties =
        datastore
                .find(GuildProperties::class.java)
                .filter(eq("_id", entity.id))
                .modify()
                .set("properties.${property.name}", value)
                .execute()

}