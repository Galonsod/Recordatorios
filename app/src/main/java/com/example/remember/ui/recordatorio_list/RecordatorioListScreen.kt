package com.example.remember.ui.recordatorio_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.remember.util.UIEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordatorioListScreen(
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: ViewModelRecordatorioList = hiltViewModel()
) {
    val recordatorios = viewModel.recordatorios.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.mensaje,
                        actionLabel = event.accion
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(RecordatorioListEvent.OnUndoDeleteClick)
                    }
                }
                is UIEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        //backgroundColor = Color(0xFF00168F),
        //contentColor = Color.White,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(RecordatorioListEvent.OnAddRecordatorioClick)
            }
                //backgroundColor = Color.Magenta,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(recordatorios.value) { recordatorio ->
                RecordatorioItem(
                    recordatorio = recordatorio,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(RecordatorioListEvent.OnRecordatorioClick(recordatorio))
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}