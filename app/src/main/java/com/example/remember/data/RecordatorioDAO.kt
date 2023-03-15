package com.example.remember.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface RecordatorioDAO {

    //CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)   //evita problemas por repeticion de IDs
    suspend fun insertRecordatorio(recordatorio: Recordatorio)

    @Delete
    suspend fun deleteRecordatorio(recordatorio: Recordatorio)

    @Query("SELECT * FROM recordatorio WHERE id = :id")
    suspend fun getRecordatorioById(id: Int): Recordatorio?

    @Query("SELECT * FROM recordatorio WHERE formattedDate = :formattedDate")
    suspend fun getRecordatorioByDate(formattedDate: String): Recordatorio?

    @Query("SELECT * FROM recordatorio WHERE formattedTime = :formattedTime")
    suspend fun getRecordatorioByTime(formattedTime: String): Recordatorio?

    /*@Query("SELECT * FROM recordatorio WHERE category = :category")
    suspend fun getRecordatorioByCategory(category: String): Recordatorio?*/

    @Query("SELECT * FROM recordatorio WHERE progress = :progress")
    suspend fun getRecordatorioByProgress(progress: Boolean): Recordatorio?

    /*@Query("SELECT * FROM recordatorio WHERE title LIKE '%buscado%' OR description LIKE '%buscado%'")
    suspend fun getRecordatorioLikeBuscado(buscado: String): Recordatorio?*/

    @Query("SELECT * FROM recordatorio")
    fun getRecordatorios(): Flow<List<Recordatorio>>

}