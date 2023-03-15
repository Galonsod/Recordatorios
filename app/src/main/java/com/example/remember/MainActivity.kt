package com.example.remember

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.remember.ui.add_edit_recordatorio.AddEditRecordatorioScreen
import com.example.remember.ui.recordatorio_list.RecordatorioListScreen
import com.example.remember.ui.theme.RememberTheme
import com.example.remember.util.Rutas
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberTheme {
                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ReminderCreation(ViewModelReminderCreation())*/


                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    //startDestination = Rutas.SIGNUP -> si sesion no recogida
                    startDestination = Rutas.RECORDATORIO_LIST
                ) {
                    composable(Rutas.RECORDATORIO_LIST) {
                        RecordatorioListScreen(
                            onNavigate = {
                                navController.navigate(it.ruta)
                            }
                        )
                    }
                    composable(
                        route = Rutas.ADD_EDIT_RECORDATORIO + "?recordatorioId={recordatorioId}",
                        arguments = listOf(
                            navArgument(name = "recordatorioId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditRecordatorioScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }

    /*private fun showNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setContentTitle("Hello wortld!")
            .setContentText("This is some content text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        notificationManager.notify(1, notification)
    }*/
}
