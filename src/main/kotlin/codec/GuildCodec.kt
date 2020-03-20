package codec

import external.Guild
import external.Snowflake
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext


class GuildCodec : Codec<Guild> {
    override fun getEncoderClass(): Class<Guild> = Guild::class.java

    override fun encode(writer: BsonWriter, value: Guild, encoderContext: EncoderContext) {
        writer.writeInt64(value.id.asLong())
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Guild? {
        val guildId = reader.readInt64()
        // Fetch guild object from remote source
        return Guild(Snowflake.of(guildId))
    }
}