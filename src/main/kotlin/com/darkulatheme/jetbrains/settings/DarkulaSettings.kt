package com.darkulatheme.jetbrains.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(name = "DarkulaSetting", storages = [Storage("darkula-theme.xml")])
class DarkulaSettings : PersistentStateComponent<DarkulaState> {
    companion object {
        val instance: DarkulaSettings
            get() = ApplicationManager.getApplication().getService(DarkulaSettings::class.java)
    }

    private var myState = DarkulaState()

    var version: String
        get() = myState.version
        set(value) {
            myState.version = value
        }

    override fun getState(): DarkulaState {
        return myState
    }

    override fun loadState(state: DarkulaState) {
        myState = state
    }

}
