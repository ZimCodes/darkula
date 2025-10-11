package com.darkulatheme.jetbrains.listeners

import com.darkulatheme.jetbrains.enums.DarkulaVariant
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.editor.colors.EditorColorsManager

class DarkulaThemeChangeListener : LafManagerListener {

    private val editorColorsManager = EditorColorsManager.getInstance()

    private var previousUI = LafManager.getInstance().currentUIThemeLookAndFeel.name

    override fun lookAndFeelChanged(lafManager: LafManager) {
        val currentUI = lafManager.currentUIThemeLookAndFeel.name
        if (previousUI != currentUI) {
            if (currentUI == DarkulaVariant.Darkula.label) {
                editorColorsManager.setGlobalScheme(editorColorsManager.getScheme("_@user_$currentUI"))
            }
        }
        previousUI = currentUI
    }

}
