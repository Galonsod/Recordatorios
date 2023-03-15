package com.example.remember.ui.recordatorio_list

import com.example.remember.data.Recordatorio


sealed class RecordatorioListEvent {
    data class OnDeleteRecordatorioClick(val recordatorio: Recordatorio) : RecordatorioListEvent()
    data class OnProgressChange(val recordatorio: Recordatorio, val progress: Boolean) :
        RecordatorioListEvent()

    object OnUndoDeleteClick : RecordatorioListEvent()
    data class OnRecordatorioClick(val recordatorio: Recordatorio) : RecordatorioListEvent()
    object OnAddRecordatorioClick : RecordatorioListEvent()
}