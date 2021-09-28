package tools.luminite.codec.definitions


class ItemDefinition : Definition {
    // TODO: Set default values for the appropriate properties
    var modelZoom = 2000
    var inventoryModel = 0
    var value = 1
    var name: String = "null"
    var description: String = "null"
    var stackable: Boolean = false
    var sceneActions = Array<String?>(5) { "" }
    var widgetActions = Array<String?>(5) { "" }
    var stackVariantId = IntArray(10)
    var stackVariantSize = IntArray(10)
    var srcColor = IntArray(5)
    var destColor = IntArray(5)
    var srcTexture = IntArray(5)
    var destTexture = IntArray(5)
    var pitch = 0
    var roll = 0
    var translateX = 0
    var translateY = 0
    var requiresMembership = false
    var maleEquipMain = 0
    var maleEquipTranslateY = 0
    var maleEquipAttachment = 0
    var femaleEquipMain = 0
    var femaleEquipTranslateY = 0
    var femaleEquipAttachment = 0
    var tradable = false
    var maleEquipEmblem = 0
    var femaleEquipEmblem = 0
    var maleDialogueHead = 0
    var femaleDialogueHead = 0
    var maleDialogueHeadgear = 0
    var femaleDialogueHeadgear = 0
    var yaw = 0
    var unNotedId = -1
    var notedId = 0
    var modelScaleX = 0
    var modelScaleY = 0
    var modelScaleZ = 0
    var ambient = 0
    var contrast = 0
    var teamId = 0
    var boughtId = 0
    var boughtTemplateId = 0
    var placeholderId = 0
    var placeholderTemplateId = 0


    override fun index() = 0

    override fun archive() = 2

    override fun fileName(): String {
        return "obj.dat"
    }

    override fun indexName(): String {
        return "obj.idx"
    }

    override fun toString(): String {
        return "ItemDefinition($name, $unNotedId, $inventoryModel)"
    }
}
