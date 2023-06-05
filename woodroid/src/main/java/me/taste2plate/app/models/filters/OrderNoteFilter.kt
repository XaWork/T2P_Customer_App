package me.taste2plate.app.models.filters

class OrderNoteFilter : Filter() {

    internal lateinit var type: String

    fun getType(): String {
        return type
    }

    fun setType(type: String) {
        this.type = type

        addFilter("type", type)
    }
}
