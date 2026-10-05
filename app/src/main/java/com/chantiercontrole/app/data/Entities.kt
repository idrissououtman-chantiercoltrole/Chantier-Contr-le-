package com.chantiercontrole.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chantiers")
data class Chantier(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nom: String,
    val tronconDebut: String = "",
    val tronconFin: String = "",
    val creeLe: Long = System.currentTimeMillis(),
    val modifieLe: Long = System.currentTimeMillis()
)

@Entity(tableName = "journal")
data class EntreeJournal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chantierId: Long,
    val date: Long = System.currentTimeMillis(),
    val texte: String,
    val auteur: String = "",
    val creeLe: Long = System.currentTimeMillis(),
    val modifieLe: Long = System.currentTimeMillis(),
    val motifModification: String = ""
)

@Entity(tableName = "controles")
data class Controle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chantierId: Long,
    val titre: String,
    val resultat: String = "A verifier",
    val observations: String = "",
    val photoChemin: String = "",
    val date: Long = System.currentTimeMillis(),
    val auteur: String = "",
    val creeLe: Long = System.currentTimeMillis(),
    val modifieLe: Long = System.currentTimeMillis(),
    val motifModification: String = ""
)

@Entity(tableName = "actions")
data class ActionSuivi(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chantierId: Long,
    val titre: String,
    val responsable: String = "",
    val echeance: Long = 0,
    val statut: String = "Ouverte",
    val creeLe: Long = System.currentTimeMillis(),
    val modifieLe: Long = System.currentTimeMillis(),
    val motifModification: String = ""
)
