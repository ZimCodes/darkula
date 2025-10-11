package com.darkulatheme.jetbrains.activities

import com.darkulatheme.jetbrains.DarkulaMeta
import com.darkulatheme.jetbrains.notifications.DarkulaNotification
import com.darkulatheme.jetbrains.settings.DarkulaSettings
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class DarkulaStartupActivity : ProjectActivity, DumbAware {
    override suspend fun execute(project: Project) {
        val settings = DarkulaSettings.instance
        if (settings.version.isEmpty()) {
            settings.version = DarkulaMeta.currentVersion
            DarkulaNotification.notifyFirstlyDownloaded(project)
            return
        }
        if (DarkulaMeta.currentVersion != settings.version) {
            settings.version = DarkulaMeta.currentVersion
            DarkulaNotification.notifyReleaseNote(project)
        }
    }
}
