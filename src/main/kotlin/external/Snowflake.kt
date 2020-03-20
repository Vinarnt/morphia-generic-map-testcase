package external


class Snowflake private constructor(private val id: Long) {
    companion object {
        fun of(id: Long): Snowflake = Snowflake(id)
    }

    fun asLong(): Long = id
}
