package codec

import external.Guild
import external.Role
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry


class CustomCodecProvider : CodecProvider {
    override fun <T : Any> get(clazz: Class<T>, registry: CodecRegistry): Codec<T>? {
        val codec = if (clazz == Role::class.java) RoleCodec()
        else if (clazz == Guild::class.java) GuildCodec()
        else null

        return codec as? Codec<T>
    }
}