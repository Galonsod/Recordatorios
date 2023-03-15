package com.example.remember.ui.recordatorio_list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remember.data.Recordatorio
import com.example.remember.data.RecordatorioRepositorio
import com.example.remember.util.Rutas
import com.example.remember.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelRecordatorioList @Inject constructor(
    private val repositorio: RecordatorioRepositorio
) : ViewModel() {

    val recordatorios = repositorio.getRecordatorios()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedRecordatorio: Recordatorio? = null

    fun onEvent(event: RecordatorioListEvent) {
        when (event) {
            is RecordatorioListEvent.OnRecordatorioClick -> {
                sendUiEvent(UIEvent.Navigate(Rutas.ADD_EDIT_RECORDATORIO + "?recordatorioId=${event.recordatorio.id}}"))
            }
            is RecordatorioListEvent.OnAddRecordatorioClick -> {
                sendUiEvent(UIEvent.Navigate(Rutas.ADD_EDIT_RECORDATORIO))
            }
            is RecordatorioListEvent.OnUndoDeleteClick -> {
                deletedRecordatorio?.let { recordatorio ->
                    viewModelScope.launch {
                        repositorio.insertRecordatorio(recordatorio)
                    }
                }
            }
            is RecordatorioListEvent.OnDeleteRecordatorioClick -> {
                viewModelScope.launch {
                    deletedRecordatorio = event.recordatorio
                    repositorio.deleteRecordatorio(event.recordatorio)
                    sendUiEvent(
                        UIEvent.ShowSnackbar(
                            mensaje = "Recordatorio borrado",
                            accion = "Deshacer"
                        )
                    )
                }
            }
            is RecordatorioListEvent.OnProgressChange -> {
                viewModelScope.launch {
                    repositorio.insertRecordatorio(
                        event.recordatorio.copy(
                            progress = event.progress
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}