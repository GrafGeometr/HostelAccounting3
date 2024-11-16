package com.example.hostelaccounting3

import androidx.compose.ui.graphics.Color

object Palette {
    private var theme: Theme = Theme.Light

    val background: Color
        get() = when (theme) {
            Theme.Light -> Color(223, 226, 235)
            Theme.Dark -> Color(0xff1e1e1e.toInt())
        }

    val text: Color
        get() = when (theme) {
            Theme.Light -> Color(0xff1e1f21.toInt())
            Theme.Dark -> Color(0xffc9c9c9.toInt())
        }

    val textInverse: Color
        get() = when (theme) {
            Theme.Light -> Color(0xffc9c9c9.toInt())
            Theme.Dark -> Color(0xff1e1f21.toInt())
        }

    val container1: Color
        get() = when (theme) {
            Theme.Light -> Color(0xffa2c0fc.toInt())
            Theme.Dark -> Color(0xff1e1f21.toInt())
        }

    val container2: Color
        get() = when (theme) {
            Theme.Light -> Color(0xffe3f6ff.toInt())
            Theme.Dark -> Color(0xff1e1f21.toInt())
        }

    val border2: Color
        get() = when (theme) {
            Theme.Light -> Color(0xff8fdbff.toInt())
            Theme.Dark -> Color(0xff1e1f21.toInt())
        }

    val button1: Color
        get() = when (theme) {
            Theme.Light -> Color(0xff215fdb.toInt())
            Theme.Dark -> Color(0xff1e1f21.toInt())
        }
}

sealed class Theme {
    data object Light : Theme()
    data object Dark : Theme()

}
