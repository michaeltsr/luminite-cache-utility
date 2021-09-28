package tools.luminite.codec.definitions

class NPCDefinition : Definition {

    var name = ""
    var description = ""
    var size = 0
    var idleAnimation = 0
    var walkingAnimation = 0
    var halfTurnAnimation = 0
    var rotateClockwiseAnimation = 0
    var rotateAntiClockwiseAnimation = 0
    var combat = 0
    var scaleXY = 0
    var scaleZ = 0
    var lightModifier = 0
    var shadowModifier = 0
    var headIcon = 0
    var rotation = 0
    var varbit = -1
    var varp = -1
    var drawMinimapDot = true
    var priorityRender = false
    var clickable = true
    var actions = Array<String?>(10) { "" }
    lateinit var originalColors: IntArray
    lateinit var replacementColors: IntArray
    lateinit var additionalModels: IntArray
    lateinit var modelIds: IntArray
    lateinit var morphisms: IntArray

    override fun index() = 0
    override fun archive() = 2
    override fun fileName() = "npc.dat"
    override fun indexName() = "npc.idx"

}
