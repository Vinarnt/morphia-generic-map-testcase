package codec

import external.Role
import external.Snowflake
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext


class RoleCodec : Codec<Role> {
    override fun getEncoderClass(): Class<Role> = Role::class.java

    override fun encode(writer: BsonWriter, value: Role?, encoderContext: EncoderContext) {
        value?.let { writer.writeInt64(it.id.asLong()) } ?: writer.writeNull()
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Role? {
        val roleId: Long? = reader.readInt64()
       // Fetch role from remote source

        return roleId?.let { Role(Snowflake.of(it)) }
    }
}