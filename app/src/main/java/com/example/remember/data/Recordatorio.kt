package com.example.remember.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Recordatorio(

    val formattedDate: String,      //requerido para crear Recordatorio, se inicializa como LocalDate.now() formateado
    val formattedTime: String?,     //si es nulo o blank, no se genera alarma
    //val category: String?,        //atributo para filtrar Recordatorios
    val title: String,              //requerido para crear Recordatorio
    val description: String?,
    val progress: Boolean,          //Check del Recordatorio, a cambiar por fail/success para filtrar Recordatorios
    @PrimaryKey val id: Int? = null //se autogenera, nunca sera null
)