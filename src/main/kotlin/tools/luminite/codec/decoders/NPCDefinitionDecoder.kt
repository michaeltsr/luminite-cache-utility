package tools.luminite.codec.decoders

import tools.luminite.codec.byte
import tools.luminite.codec.definitions.NPCDefinition
import tools.luminite.codec.readString
import tools.luminite.codec.uShort
import tools.luminite.codec.unsignedByte
import java.nio.ByteBuffer

object NPCDefinitionDecoder : Decoder<NPCDefinition>() {

    override fun definition(): NPCDefinition {
        return NPCDefinition()
    }

    private fun wrap(value: Int) = if (value == 65_535) -1 else value

    override fun decode(buffer: ByteBuffer): NPCDefinition {
        val npcDefinition = definition()
        while (true) {
            when (val opcode = buffer.unsignedByte()) {
                0 -> return npcDefinition
                1 -> {
                    npcDefinition.modelIds = IntArray(buffer.unsignedByte())
                    for (i in npcDefinition.modelIds.indices) {
                        npcDefinition.modelIds[i] = buffer.uShort()
                    }
                }
                2 -> npcDefinition.name = buffer.readString()
                3 -> npcDefinition.description = buffer.readString()
                12 -> npcDefinition.size = buffer.byte()
                13 -> npcDefinition.idleAnimation = buffer.uShort()
                14 -> npcDefinition.walkingAnimation = buffer.uShort()
                17 -> {
                    npcDefinition.walkingAnimation = buffer.uShort()
                    npcDefinition.halfTurnAnimation = buffer.uShort()
                    npcDefinition.rotateClockwiseAnimation = buffer.uShort()
                    npcDefinition.rotateAntiClockwiseAnimation = buffer.uShort()
                }
                in 30..39 -> {
                    npcDefinition.actions[opcode - 30] = buffer.readString()
                    if (npcDefinition.actions[opcode - 30].equals("hidden", ignoreCase = true)) {
                        npcDefinition.actions[opcode - 30] = null
                    }
                }
                40 -> {
                    val count = buffer.unsignedByte()
                    npcDefinition.originalColors = IntArray(count)
                    npcDefinition.replacementColors = IntArray(count)
                    for (i in 0 until count) {
                        npcDefinition.originalColors[i] = buffer.uShort()
                        npcDefinition.replacementColors[i] = buffer.uShort()
                    }
                }
                60 -> {
                    npcDefinition.additionalModels = IntArray(buffer.unsignedByte())
                    for (i in npcDefinition.additionalModels.indices) {
                        npcDefinition.additionalModels[i] = buffer.uShort()
                    }
                }
                90 -> buffer.uShort()
                91 -> buffer.uShort()
                92 -> buffer.uShort()
                93 -> npcDefinition.drawMinimapDot = false
                95 -> npcDefinition.combat = buffer.uShort()
                97 -> npcDefinition.scaleXY = buffer.uShort()
                98 -> npcDefinition.scaleZ = buffer.uShort()
                99 -> npcDefinition.priorityRender = true
                100 -> npcDefinition.lightModifier = buffer.byte()
                101 -> npcDefinition.shadowModifier = buffer.byte() * 5
                102 -> npcDefinition.headIcon = buffer.uShort()
                103 -> npcDefinition.rotation = buffer.uShort()
                106 -> {
                    npcDefinition.varbit = wrap(buffer.uShort())
                    npcDefinition.varp = wrap(buffer.uShort())
                    npcDefinition.morphisms = IntArray(buffer.unsignedByte() + 1)
                    for (i in npcDefinition.morphisms.indices) {
                        npcDefinition.morphisms[i] = wrap(buffer.uShort())
                    }
                }
                107 -> npcDefinition.clickable = false
                else -> println("[${npcDefinition.javaClass.simpleName}] Error: Unrecognized opcode: $opcode at position ${buffer.position()}")
            }
        }
    }
}