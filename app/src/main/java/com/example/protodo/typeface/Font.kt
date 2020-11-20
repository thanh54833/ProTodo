package com.example.protodo.typeface

enum class Font(var isAction: Boolean, var start: Int, var end: Int, var color: Int = 0) {
    Bold(false, 0, 0),
    Italic(false, 0, 0),
    Underline(false, 0, 0),

    TextColor(false, 0, 0, color = 0),
    BackgroundColor(false, 0, 0, color = 0)

    ;
}