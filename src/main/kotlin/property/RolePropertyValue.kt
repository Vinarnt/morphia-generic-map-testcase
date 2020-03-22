package property

import dev.morphia.annotations.Embedded
import dev.morphia.annotations.PostLoad
import external.Role
import external.Snowflake


@Embedded
class RolePropertyValue(value: Role? = null) : PropertyValue<Role?>(value) {
    override fun equals(other: Any?): Boolean =
        (other is RolePropertyValue && other.value == value)

    override fun hashCode(): Int {
        return 0
    }

    @PostLoad
    fun onPostLoad() {
        value = Role(Snowflake.of(value as Long))
    }
}