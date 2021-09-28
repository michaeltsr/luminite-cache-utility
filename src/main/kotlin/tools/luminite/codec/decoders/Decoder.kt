package tools.luminite.codec.decoders

import org.displee.CacheLibrary
import org.displee.CacheLibraryMode
import tools.luminite.codec.cachePath
import tools.luminite.codec.definitions.Definition
import tools.luminite.codec.encoders.ItemDefinitionEncoder
import tools.luminite.codec.uShort
import java.nio.ByteBuffer

abstract class Decoder<T : Definition> {

    private val definitionMap = mutableMapOf<Int, T>()

    // Item ID mapped to the start and end position of its data in the buffer
    val itemPositionMap = mutableMapOf<Int, Pair<Int, Int>>()

//    val itemModificationMap = mutableMapOf<Int, LinkedList<Int>>()

    fun init() {
        val definition = definition()
        val archive = CacheLibrary(cachePath, CacheLibraryMode.UN_CACHED).getIndex(definition.index())
                .getArchive(definition.archive())
        val dataBuffer = ByteBuffer.wrap(archive.getFile(definition.fileName()).data)
        val definitionCount: Int
        if (definition.indexName() != null) {
            var index = 2
            val indexBuffer = ByteBuffer.wrap(archive.getFile(definition.indexName()).data)
            definitionCount = indexBuffer.uShort()
            val indices = IntArray(definitionCount)
            for (i in 0 until definitionCount) {
                indices[i] = index
                index += indexBuffer.uShort()
            }
            for (i in 0 until definitionCount) {
                dataBuffer.position(indices[i])
                val startingPosition = dataBuffer.position()
                definitionMap[i] = decode(dataBuffer)
                itemPositionMap[i] = Pair(startingPosition, dataBuffer.position())
            }
            ItemDefinitionEncoder.storePositions(dataBuffer)
        } else {
            definitionCount = dataBuffer.uShort()
            for (i in 0 until definitionCount) {
                definitionMap[i] = decode(dataBuffer)
            }
            ItemDefinitionEncoder.storePositions(dataBuffer)
        }

    }

    fun definitionMap(): Map<Int, T> = definitionMap.toMap()
    protected abstract fun definition(): T
    abstract fun decode(buffer: ByteBuffer): T
}