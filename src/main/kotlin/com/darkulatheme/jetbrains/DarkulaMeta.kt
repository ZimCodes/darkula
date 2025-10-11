package com.darkulatheme.jetbrains

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId

object DarkulaMeta {
    val currentVersion: String
        get() = PluginManagerCore.getPlugin(PluginId.getId("com.zimcodes.idea"))?.version ?: ""
}