package property

import dev.morphia.annotations.Embedded
import external.Role


@Embedded
class RolePropertyValue(value: Role? = null) : PropertyValue<Role?>(value) {
    override fun equals(other: Any?): Boolean =
        (other is RolePropertyValue && other.value == value)

    override fun hashCode(): Int {
        return 0
    }
}