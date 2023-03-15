package com.example.remember.ui.add_edit_recordatorio

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.remember.util.UIEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import android.Manifest
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.remember.R


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditRecordatorioScreen(
    onPopBackStack: () -> Unit,
    viewModel: ViewModelAddEditRecordatorio = hiltViewModel()
) {

    ////////////////////////////////////////////////////////
    //Peticion de permiso para envio de notificaciones con API 33+
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
        }
    )

    //////////////////////////////////////////////////////////////

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> onPopBackStack()
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.mensaje,
                        actionLabel = event.accion
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(AddEditRecordatorioEvent.OnCloseRecordatorioClick)
                    },
                    backgroundColor = Color.Gray
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar pantalla",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(220.dp))

                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(AddEditRecordatorioEvent.OnSaveRecordatorioClick)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Crear recordatorio",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Creación de tarea")

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditRecordatorioEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Título")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditRecordatorioEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "Descripción")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(text = "Creación de recordatorio (opcional)")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                dateDialogState.show()
            }) {
                Text(text = "Escoge una fecha")
            }

            Text(text = viewModel.formattedDate)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                if (hasNotificationPermission) {
                    timeDialogState.show()
                }
            }) {
                Text(text = "Escoge una hora")
            }

            Text(text = viewModel.formattedTime)
        }

        //MaterialDialogCALENDARIO
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Ok") {

                }
                negativeButton(text = "Cancelar")
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Escoge una fecha",
                allowedDateValidator = {
                    it >= LocalDate.now()
                },
            ) {
                viewModel.pruebaDate(it)
            }
        }

        //MaterialDialogRELOJ
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "Ok") {

                }
                negativeButton(text = "Cancelar")
            }
        ) {
            timepicker(
                initialTime = LocalTime.now(),
                title = "Escoge una hora",
                is24HourClock = true,
                timeRange = viewModel.limitarReloj
                //timeRange = LocalTime.now()..LocalTime.of(23,59) but if pickedDate != LocalDate.now(), full timeRange
            ) {
                viewModel.pruebaTime(it)
            }
        }
    }
}



