package com.chantiercontrole.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChantierDao {
    @Query("SELECT * FROM chantiers ORDER BY modifieLe DESC")
    fun tous(): Flow<List<Chantier>>

    @Insert
    suspend fun ajouter(chantier: Chantier): Long

    @Update
    suspend fun modifier(chantier: Chantier)
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal WHERE chantierId = :chantierId ORDER BY date DESC")
    fun duChantier(chantierId: Long): Flow<List<EntreeJournal>>

    @Insert
    suspend fun ajouter(entree: EntreeJournal): Long

    @Update
    suspend fun modifier(entree: EntreeJournal)
}

@Dao
interface ControleDao {
    @Query("SELECT * FROM controles WHERE chantierId = :chantierId ORDER BY date DESC")
    fun duChantier(chantierId: Long): Flow<List<Controle>>

    @Insert
    suspend fun ajouter(controle: Controle): Long

    @Update
    suspend fun modifier(controle: Controle)
}

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions WHERE chantierId = :chantierId ORDER BY echeance ASC")
    fun duChantier(chantierId: Long): Flow<List<ActionSuivi>>

    @Insert
    suspend fun ajouter(action: ActionSuivi): Long

    @Update
    suspend fun modifier(action: ActionSuivi)
}
