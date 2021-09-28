package tools.luminite.codec.encoders

import tools.luminite.codec.decoders.ItemDefinitionDecoder
import tools.luminite.codec.definitions.ItemDefinition
import java.nio.ByteBuffer
import java.util.*

object ItemDefinitionEncoder : Encoder<ItemDefinitionEncoder.Operation, ItemDefinition>() {

    // -1 Denotes String
    enum class Operation(val code: Int, val size: Int) : EncodingOperation {
        DESCRIPTION(3, -1),
        VALUE(12, Int.SIZE_BYTES)
    }

    override fun definition(): ItemDefinition {
        return ItemDefinition()
    }

    fun storePositions(byteBuffer: ByteBuffer) {
        if (definitionPositions.isEmpty()) {
            ItemDefinitionDecoder.itemPositionMap.values.forEach {
                byteBuffer.position(it.first)
                println("${it.first} : ${it.second}")
                val byteList = LinkedList<Byte>()
                while (byteBuffer.position() != it.second) {
                    byteList.add(byteBuffer.get())
                }
                definitionPositions.add(byteList)
            }
        }
    }

    override fun encode(byteBuffer: ByteBuffer, data: Any, operation: Operation) {
        // Store the positions of eac
        storePositions(byteBuffer)
        val itemId = 4151
        val itemPosition = definitionPositions[itemId]

        // Assign variable 'size' to size of data and 1 byte for the opcode
        // An extra byte is allocated for String data types to read the line feed byte
//        val size = if(operation.size == -1) 2 + (data as String).length else 1 + operation.size
//        val newBuffer = ByteBuffer.allocate(size + byteBuffer.limit()).put(operation.code.toByte())
//        when(data.javaClass.kotlin) {
//            Int::class -> newBuffer.putInt(data as Int)
//            String::class -> newBuffer.writeString(data as String)
//            Byte::class -> newBuffer.put(data as Byte)
//            Long::class -> newBuffer.putLong(data as Long)
//        }
        modify(itemPosition, operation, data)

    }


    //Short = hi & 0xFF << 8 | lo & 0xFF
    override fun modify(definitionBytes: LinkedList<Byte>, operation: Operation, data: Any) {
        val tmp = definitionPositions[4151]
        val bytePosition = ItemDefinitionDecoder.operationPosition(ByteBuffer.wrap(tmp.toByteArray()), operation)
        tmp.subList(bytePosition.first, bytePosition.second).clear()
        var position = bytePosition.first
        tmp.add(position++, 3)
        tmp.addAll(position, "hey\n".encodeToByteArray().toList())
        println(tmp)
        println(ItemDefinitionDecoder.decode(ByteBuffer.wrap(tmp.toByteArray())).name)
    }
}


