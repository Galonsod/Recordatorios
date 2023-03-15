package com.example.remember.di



import android.app.Application
import androidx.room.Room
import com.example.remember.data.RecordatorioDB
import com.example.remember.data.RecordatorioRepositorio
import com.example.remember.data.RecordatorioRepositorioImplementacion
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloApp {

    @Provides
    @Singleton
    fun provideRecordatorioDB(app: Application): RecordatorioDB {
        return Room.databaseBuilder(
            app,
            RecordatorioDB::class.java,
            "recordatorio_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecordatorioRepositorio(db: RecordatorioDB): RecordatorioRepositorio {
        return RecordatorioRepositorioImplementacion(db.dao)
    }
}