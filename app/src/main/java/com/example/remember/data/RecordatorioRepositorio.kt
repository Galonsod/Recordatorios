package com.example.remember.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface RecordatorioRepositorio {

    suspend fun insertRecordatorio(recordatorio: Recordatorio)

    suspend fun deleteRecordatorio(recordatorio: Recordatorio)

    suspend fun getRecordatorioById(id: Int): Recordatorio?

    suspend fun getRecordatorioByDate(formattedDate: String): Recordatorio?

    suspend fun getRecordatorioByTime(formattedTime: String): Recordatorio?

    //suspend fun getRecordatorioByCategory(category: String): Recordatorio?

    suspend fun getRecordatorioByProgress(progress: Boolean): Recordatorio?

    //suspend fun getRecordatorioLikeBuscado(buscado: String): Recordatorio?

    fun getRecordatorios(): Flow<List<Recordatorio>>
}