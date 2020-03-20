package property

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Property
import external.Guild
import org.bson.types.ObjectId

@Entity("guild_properties")
class GuildProperties() {
    @Id
    val id: ObjectId? = null

    @Property("guild_id")
    lateinit var guild: Guild

    private val properties: MutableMap<GuildProperty, in PropertyValue<*>?> = mutableMapOf()

    constructor(guild: Guild) : this() {
        this.guild = guild
    }

    fun get(property: GuildProperty): PropertyValue<*> = (properties[property]
        ?: property.defaultValue) as PropertyValue<*>

    fun <T : PropertyValue<*>> set(property: GuildProperty, value: T) {
        properties[property] = value
    }
}