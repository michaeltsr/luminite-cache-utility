package tools.luminite.codec.decoders

import tools.luminite.codec.definitions.ItemDefinition
import tools.luminite.codec.encoders.ItemDefinitionEncoder
import tools.luminite.codec.readString
import tools.luminite.codec.uShort
import tools.luminite.codec.unsignedByte
import java.nio.ByteBuffer

object ItemDefinitionDecoder : Decoder<ItemDefinition>() {

    override fun definition(): ItemDefinition {
        return ItemDefinition()
    }

    override fun decode(buffer: ByteBuffer): ItemDefinition {
        val itemDefinition = definition()
        while (true) {
            val opcode = buffer.unsignedByte()
            if (opcode == 0)
                return itemDefinition
            else
                decode(buffer, opcode, itemDefinition)

//            when (opcode) {
//                0 -> return itemDefinition
//                1 -> itemDefinition.inventoryModel = buffer.uShort()
//                2 -> itemDefinition.name = buffer.readString()
//                3 -> itemDefinition.description = buffer.readString()
//                4 -> itemDefinition.modelZoom = buffer.uShort()
//                5 -> itemDefinition.pitch = buffer.uShort()
//                6 -> itemDefinition.roll = buffer.uShort()
//                7 -> {
//                    itemDefinition.translateX = buffer.uShort()
//                    if (itemDefinition.translateX > 32767) itemDefinition.translateX -= 0x10000
//                }
//                8 -> {
//                    itemDefinition.translateY = buffer.uShort()
//                    if (itemDefinition.translateY > 32767) itemDefinition.translateY -= 0x10000
//                }
//                10 -> {
//                    buffer.uShort()
//                }
//                11 -> itemDefinition.stackable = true
//                12 -> itemDefinition.value = buffer.int
//                16 -> itemDefinition.requiresMembership = true
//                23 -> {
//                    itemDefinition.maleEquipMain = buffer.uShort()
//                    itemDefinition.maleEquipTranslateY = buffer.unsignedByte()
//                }
//                24 -> itemDefinition.maleEquipAttachment = buffer.uShort()
//                25 -> {
//                    itemDefinition.femaleEquipMain = buffer.uShort()
//                    itemDefinition.femaleEquipTranslateY = buffer.unsignedByte()
//                }
//                26 -> itemDefinition.femaleEquipAttachment = buffer.uShort()
//                in 30..34 -> {
//                    itemDefinition.sceneActions[opcode - 30] = buffer.readString()
//                    if (itemDefinition.sceneActions[opcode - 30].equals("hidden", ignoreCase = true)) {
//                        itemDefinition.sceneActions[opcode - 30] = null
//                    }
//                }
//                in 35..39 -> {
//                    itemDefinition.widgetActions[opcode - 35] = buffer.readString()
//                    if (itemDefinition.widgetActions[opcode - 35].equals("hidden", ignoreCase = true)) {
//                        itemDefinition.widgetActions[opcode - 35] = null
//                    }
//                }
//                40 -> {
//                    val length = buffer.unsignedByte()
//                    itemDefinition.srcColor = IntArray(length)
//                    itemDefinition.destColor = IntArray(length)
//                    for (i in 0 until length) {
//                        itemDefinition.destColor[i] = buffer.uShort()
//                        itemDefinition.srcColor[i] = buffer.uShort()
//                    }
//                }
//                41 -> {
//                    val length = buffer.unsignedByte()
//                    itemDefinition.srcTexture = IntArray(length)
//                    itemDefinition.destTexture = IntArray(length)
//                    for (i in 0 until length) {
//                        itemDefinition.srcTexture[i] = buffer.uShort()
//                        itemDefinition.destTexture[i] = buffer.uShort()
//                    }
//                }
//                42 -> {
//                    buffer.unsignedByte()
//                }
//                65 -> itemDefinition.tradable = true
//                78 -> itemDefinition.maleEquipEmblem = buffer.uShort()
//                79 -> itemDefinition.femaleEquipEmblem = buffer.uShort()
//                90 -> itemDefinition.maleDialogueHead = buffer.uShort()
//                91 -> itemDefinition.femaleDialogueHead = buffer.uShort()
//                92 -> itemDefinition.maleDialogueHeadgear = buffer.uShort()
//                93 -> itemDefinition.femaleDialogueHeadgear = buffer.uShort()
//                95 -> itemDefinition.yaw = buffer.uShort()
//                97 -> itemDefinition.unNotedId = buffer.uShort()
//                98 -> itemDefinition.notedId = buffer.uShort()
//                in 100..109 -> {
//                    itemDefinition.stackVariantId[opcode - 100] = buffer.uShort()
//                    itemDefinition.stackVariantSize[opcode - 100] = buffer.uShort()
//                }
//                110 -> itemDefinition.modelScaleX = buffer.uShort()
//                111 -> itemDefinition.modelScaleY = buffer.uShort()
//                112 -> itemDefinition.modelScaleZ = buffer.uShort()
//                113 -> itemDefinition.ambient = buffer.unsignedByte()
//                114 -> itemDefinition.contrast = buffer.unsignedByte() * 5
//                115 -> itemDefinition.teamId = buffer.unsignedByte()
//                139 -> itemDefinition.boughtId = buffer.uShort()
//                140 -> itemDefinition.boughtTemplateId = buffer.uShort()
//                148 -> itemDefinition.placeholderId = buffer.uShort()
//                149 -> itemDefinition.placeholderTemplateId = buffer.uShort()
//                else -> println("[${itemDefinition.javaClass.simpleName}] Error: Unrecognized opcode: $opcode")
//            }

        }
    }


    fun operationPosition(buffer: ByteBuffer, operation: ItemDefinitionEncoder.Operation): Pair<Int, Int> {
        var start = -1
        while (buffer.hasRemaining()) {
            val opcode = buffer.unsignedByte()
            if (opcode == operation.code) {
                start = buffer.position()
            }
            if (start != -1 && opcode != operation.code) {
                return Pair(start - 1, buffer.position() - 1)
            }
            decode(buffer, opcode, null)
        }
        throw IllegalArgumentException()
    }

    private fun decode(buffer: ByteBuffer, opcode: Int, itemDef: ItemDefinition?): ItemDefinition {
        val itemDefinition = itemDef ?: ItemDefinition()
        println(opcode)
        when (opcode) {
            0 -> {
                println("Returning")
                return itemDefinition
            }
            1 -> itemDefinition.inventoryModel = buffer.uShort()
            2 -> itemDefinition.name = buffer.readString()
            3 -> itemDefinition.description = buffer.readString()
            4 -> itemDefinition.modelZoom = buffer.uShort()
            5 -> itemDefinition.pitch = buffer.uShort()
            6 -> itemDefinition.roll = buffer.uShort()
            7 -> {
                itemDefinition.translateX = buffer.uShort()
                if (itemDefinition.translateX > 32767) itemDefinition.translateX -= 0x10000
            }
            8 -> {
                itemDefinition.translateY = buffer.uShort()
                if (itemDefinition.translateY > 32767) itemDefinition.translateY -= 0x10000
            }
            10 -> {
                buffer.uShort()
            }
            11 -> itemDefinition.stackable = true
            12 -> itemDefinition.value = buffer.int
            16 -> itemDefinition.requiresMembership = true
            23 -> {
                itemDefinition.maleEquipMain = buffer.uShort()
                itemDefinition.maleEquipTranslateY = buffer.unsignedByte()
            }
            24 -> itemDefinition.maleEquipAttachment = buffer.uShort()
            25 -> {
                itemDefinition.femaleEquipMain = buffer.uShort()
                itemDefinition.femaleEquipTranslateY = buffer.unsignedByte()
            }
            26 -> itemDefinition.femaleEquipAttachment = buffer.uShort()
            in 30..34 -> {
                itemDefinition.sceneActions[opcode - 30] = buffer.readString()
                if (itemDefinition.sceneActions[opcode - 30].equals("hidden", ignoreCase = true)) {
                    itemDefinition.sceneActions[opcode - 30] = null
                }
            }
            in 35..39 -> {
                itemDefinition.widgetActions[opcode - 35] = buffer.readString()
                if (itemDefinition.widgetActions[opcode - 35].equals("hidden", ignoreCase = true)) {
                    itemDefinition.widgetActions[opcode - 35] = null
                }
            }
            40 -> {
                val length = buffer.unsignedByte()
                itemDefinition.srcColor = IntArray(length)
                itemDefinition.destColor = IntArray(length)
                for (i in 0 until length) {
                    itemDefinition.destColor[i] = buffer.uShort()
                    itemDefinition.srcColor[i] = buffer.uShort()
                }
            }
            41 -> {
                val length = buffer.unsignedByte()
                itemDefinition.srcTexture = IntArray(length)
                itemDefinition.destTexture = IntArray(length)
                for (i in 0 until length) {
                    itemDefinition.srcTexture[i] = buffer.uShort()
                    itemDefinition.destTexture[i] = buffer.uShort()
                }
            }
            42 -> {
                buffer.unsignedByte()
            }
            65 -> itemDefinition.tradable = true
            78 -> itemDefinition.maleEquipEmblem = buffer.uShort()
            79 -> itemDefinition.femaleEquipEmblem = buffer.uShort()
            90 -> itemDefinition.maleDialogueHead = buffer.uShort()
            91 -> itemDefinition.femaleDialogueHead = buffer.uShort()
            92 -> itemDefinition.maleDialogueHeadgear = buffer.uShort()
            93 -> itemDefinition.femaleDialogueHeadgear = buffer.uShort()
            95 -> itemDefinition.yaw = buffer.uShort()
            97 -> itemDefinition.unNotedId = buffer.uShort()
            98 -> itemDefinition.notedId = buffer.uShort()
            in 100..109 -> {
                itemDefinition.stackVariantId[opcode - 100] = buffer.uShort()
                itemDefinition.stackVariantSize[opcode - 100] = buffer.uShort()
            }
            110 -> itemDefinition.modelScaleX = buffer.uShort()
            111 -> itemDefinition.modelScaleY = buffer.uShort()
            112 -> itemDefinition.modelScaleZ = buffer.uShort()
            113 -> itemDefinition.ambient = buffer.unsignedByte()
            114 -> itemDefinition.contrast = buffer.unsignedByte() * 5
            115 -> itemDefinition.teamId = buffer.unsignedByte()
            139 -> itemDefinition.boughtId = buffer.uShort()
            140 -> itemDefinition.boughtTemplateId = buffer.uShort()
            148 -> itemDefinition.placeholderId = buffer.uShort()
            149 -> itemDefinition.placeholderTemplateId = buffer.uShort()
            else -> println("[${itemDefinition.javaClass.simpleName}] Error: Unrecognized opcode: $opcode")
        }
        throw UnsupportedOperationException()
    }
}
