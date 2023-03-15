package com.example.remember.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

class RecordatorioRepositorioImplementacion (
    private val dao: RecordatorioDAO
): RecordatorioRepositorio {

    override suspend fun insertRecordatorio(recordatorio: Recordatorio) {
        dao.insertRecordatorio(recordatorio)
    }

    override suspend fun deleteRecordatorio(recordatorio: Recordatorio) {
        dao.deleteRecordatorio(recordatorio)
    }

    override suspend fun getRecordatorioById(id: Int): Recordatorio? {
        return dao.getRecordatorioById(id)
    }

    override suspend fun getRecordatorioByDate(formattedDate: String): Recordatorio? {
        return dao.getRecordatorioByDate(formattedDate)
    }

    override suspend fun getRecordatorioByTime(formattedTime: String): Recordatorio? {
        return dao.getRecordatorioByTime(formattedTime)
    }

    /*override suspend fun getRecordatorioByCategory(category: String): Recordatorio? {
        return dao.getRecordatorioByCategory(category)
    }*/

    override suspend fun getRecordatorioByProgress(progress: Boolean): Recordatorio? {
        return dao.getRecordatorioByProgress(progress)
    }

    /*override suspend fun getRecordatorioLikeBuscado(buscado: String): Recordatorio? {
        return dao.getRecordatorioLikeBuscado(buscado)
    }*/

    override fun getRecordatorios(): Flow<List<Recordatorio>> {
        return dao.getRecordatorios()
    }
}