package tools.luminite.codec

import java.nio.ByteBuffer

private const val STRING_TERMINATOR = 10
fun ByteBuffer.uShort() = this.short.toInt() and 0xFFFF
fun ByteBuffer.unsignedByte() = this.get().toInt() and 0xFF
fun ByteBuffer.byte() = this.get().toInt()
fun ByteBuffer.readString() : String {
    val bldr = StringBuilder()
    var character = '0'
    while (character.code != STRING_TERMINATOR) {
        character = this.get().toInt().toChar()
        bldr.append(character)
    }
    return bldr.toString()
}

fun ByteBuffer.writeString(value: String) : ByteBuffer {
    for (ch in value) {
        this.put(ch.code.toByte())
    }
    this.put(STRING_TERMINATOR.toByte())
    return this
}

