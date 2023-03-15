package com.example.remember.ui.recordatorio_list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remember.R
import com.example.remember.data.Recordatorio
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun RecordatorioItem(
    recordatorio: Recordatorio,
    onEvent: (RecordatorioListEvent) -> Unit,
    modifier: Modifier = Modifier
    //color: color = color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recordatorio.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = recordatorio.formattedDate.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.width(8.dp))


                //AQUI ICON BUTTON
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                recordatorio.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it)
                }

                Spacer(modifier = Modifier.width(8.dp))

                recordatorio.formattedTime?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it) //format adecuado aqui TODO
                }
            }
        }

        //ICON BUTON ORIGINALMENTE BAJO SPACER SEÑALADO
        IconButton(onClick = {
            onEvent(RecordatorioListEvent.OnDeleteRecordatorioClick(recordatorio))
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Borrar"
            )
        }
        //PRUEBA BORRAR SPACER SI NO

        Spacer(modifier = Modifier.width(8.dp))


        Checkbox(
            checked = recordatorio.progress,
            onCheckedChange = { isChecked ->
                onEvent(RecordatorioListEvent.OnProgressChange(recordatorio, isChecked))
            }
        )

    }
}

/*@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Swap() {
    Scaffold(

// Creating Content
        content = {
            // Creating a Column Layout
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Fetching local context
                val mLocalContext = LocalContext.current

                // Creating a Swipe Action for Calling;
                // setting icon, background and what
                // happens when swiped
                val swipeSuccess = SwipeAction(
                    icon = painterResource(R.drawable.ic_call),
                    background = Color.Green,
                    isUndo = true,
                    onSwipe = {
                        Toast.makeText(mLocalContext, "Calling", Toast.LENGTH_SHORT).show()
                    }
                )

                // Creating a Swipe Action for Sending a message;
                // setting icon, background and what happens when swiped
                val swipeFail = SwipeAction(
                    icon = painterResource(R.drawable.),
                    background = Color.Red,
                    isUndo = true,
                    onSwipe = {
                        Toast.makeText(mLocalContext, "Sending Message", Toast.LENGTH_SHORT).show()
                    }
                )

                // Creating a Swipe Action Box with start
                // action as calling and end action as sending message
                SwipeableActionsBox(
                    startActions = listOf(swipeSuccess),
                    endActions = listOf(swipeFail))
                {

                    // Creating a Button inside Swipe Action Box
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0XFF0F9D58)
                        )
                    ) {
                        Text(text = "Swipe Left or Right", fontSize = 25.sp, color = Color.White)
                    }
                }
            }
        }
    )
}*/