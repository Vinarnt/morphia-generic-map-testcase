package property

import MorphiaGuildPropertiesDAO
import external.Guild


class GuildPropertyManager(val dao: MorphiaGuildPropertiesDAO) {
    private val guildPropertiesMap: MutableMap<Guild, GuildProperties> = hashMapOf()

    fun load(guild: Guild) {
        var guildProperties = dao.findFor(guild)
        if (guildProperties == null) {
            guildProperties = GuildProperties(guild)
            GuildProperty.values().forEach {
                guildProperties.set(it, it.defaultValue)
            }
            dao.insert(guildProperties)
        }
        guildPropertiesMap[guild] = guildProperties
    }

    /**
     * Get a property value for a guild.
     *
     * @param guild witch guild to get property from
     * @param property the property to get value of
     * @return the property value if found, the property default value otherwise
     */
    fun get(guild: Guild, property: GuildProperty): PropertyValue<*> =
        guildPropertiesMap[guild]!!.get(property)

    /**
     * Set a property for a guild.
     *
     * @param guild which guild to set property to
     * @param property the property to set
     * @param value the property value to set
     * @return true if value has been set successfully, false otherwise
     */
    fun <T : PropertyValue<*>> set(guild: Guild, property: GuildProperty, value: T): Boolean {
        guildPropertiesMap[guild]?.let {
            it.set(property, value)
            dao.updateProperty(it, property, value)

            return true
        }
        return false
    }
}