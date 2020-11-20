package com.example.protodo.Shortcuts

class ShortCutModel(
    var shortLabel: String = "",
    var longLabel: String = "",
    var disabledMessage: String = "",
    var icon: Int = 0,
    var startIntent: Class<*>? = null
)
