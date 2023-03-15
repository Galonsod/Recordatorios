package com.example.remember.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recordatorio::class],
    version = 1,
    exportSchema = true //para exportar la DB, para compartir / descargar recordatorios
)
abstract class RecordatorioDB: RoomDatabase() {

    abstract val dao: RecordatorioDAO
}