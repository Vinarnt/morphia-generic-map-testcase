package property


enum class GuildProperty(val key: String, val defaultValue: PropertyValue<*>) {
    AUTOROLE("autorole", RolePropertyValue());

    companion object {
        fun get(key: String): GuildProperty? = values().find { it.key.contentEquals(key) }
    }
}