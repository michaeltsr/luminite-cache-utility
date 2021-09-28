package tools.luminite.codec.definitions

interface Definition {
    fun index() : Int
    fun archive() : Int
    fun fileName() : String
    fun indexName() : String?
}