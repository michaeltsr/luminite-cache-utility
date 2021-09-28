package tools.luminite.codec

import tools.luminite.codec.decoders.ItemDefinitionDecoder
import tools.luminite.codec.encoders.ItemDefinitionEncoder
import java.lang.invoke.MethodHandles


val cachePath: String = MethodHandles.lookup().lookupClass().getResource("/cache/")!!.path
val decoderList = listOf(ItemDefinitionDecoder)
val encoderList = listOf(ItemDefinitionEncoder)


fun main() {
    initializeDecoders()
    initializeEncoder()
//    println(ItemDefinitionDecoder.itemPositionMap[4151])
//    println(ItemDefinitionDecoder.definitionMap()[4151]!!.description)
}


fun initializeDecoders() {
    decoderList.forEach { it.init() }
}

fun initializeEncoder() {
    encoderList.forEach { it.init() }
}