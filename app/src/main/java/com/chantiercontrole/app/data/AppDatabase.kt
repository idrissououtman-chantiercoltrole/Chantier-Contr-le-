package com.chantiercontrole.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Chantier::class, EntreeJournal::class, Controle::class, ActionSuivi::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chantierDao(): ChantierDao
    abstract fun journalDao(): JournalDao
    abstract fun controleDao(): ControleDao
    abstract fun actionDao(): ActionDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun obtenir(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "chantier_controle.db"
                ).build().also { instance = it }
            }
        }
    }
}
