package tools.luminite.codec.encoders

import tools.luminite.codec.decoders.ItemDefinitionDecoder
import tools.luminite.codec.definitions.Definition
import tools.luminite.codec.encoders.ItemDefinitionEncoder.Operation
import java.nio.ByteBuffer
import java.util.*

abstract class Encoder<E : Encoder.EncodingOperation, T : Definition> {

    interface EncodingOperation
    protected val definitionPositions = LinkedList<LinkedList<Byte>>()
    private lateinit var dataBuffer: ByteBuffer

    fun init() {
        storeOperationPositions(dataBuffer)
    }

    private fun storeOperationPositions(byteBuffer: ByteBuffer) {
        if (ItemDefinitionEncoder.definitionPositions.isEmpty()) {
            ItemDefinitionDecoder.itemPositionMap.values.forEach {
                byteBuffer.position(it.first)
                println("${it.first} : ${it.second}")
                val byteList = LinkedList<Byte>()
                while (byteBuffer.position() != it.second) {
                    byteList.add(byteBuffer.get())
                }
                ItemDefinitionEncoder.definitionPositions.add(byteList)
            }
        }
    }

    abstract fun definition(): T
    abstract fun modify(definitionBytes: LinkedList<Byte>, operation: E, data: Any)
    abstract fun encode(byteBuffer: ByteBuffer, data: Any, operation: Operation)
}