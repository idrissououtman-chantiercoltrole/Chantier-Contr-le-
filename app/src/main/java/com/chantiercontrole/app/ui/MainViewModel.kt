package com.chantiercontrole.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.chantiercontrole.app.data.ActionSuivi
import com.chantiercontrole.app.data.AppDatabase
import com.chantiercontrole.app.data.Chantier
import com.chantiercontrole.app.data.Controle
import com.chantiercontrole.app.data.EntreeJournal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val base = AppDatabase.obtenir(application)

    private val chantierChoisiId = MutableStateFlow(0L)

    val chantiers: StateFlow<List<Chantier>> = base.chantierDao().tous()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val chantierActif: StateFlow<Chantier?> = combine(chantiers, chantierChoisiId) { liste, id ->
        liste.firstOrNull { it.id == id } ?: liste.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val journal: StateFlow<List<EntreeJournal>> = chantierActif.flatMapLatest { c ->
        if (c == null) flowOf(emptyList()) else base.journalDao().duChantier(c.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val controles: StateFlow<List<Controle>> = chantierActif.flatMapLatest { c ->
        if (c == null) flowOf(emptyList()) else base.controleDao().duChantier(c.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val actions: StateFlow<List<ActionSuivi>> = chantierActif.flatMapLatest { c ->
        if (c == null) flowOf(emptyList()) else base.actionDao().duChantier(c.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun creerChantier(nom: String, debut: String, fin: String) {
        viewModelScope.launch {
            val id = base.chantierDao().ajouter(
                Chantier(nom = nom, tronconDebut = debut, tronconFin = fin)
            )
            chantierChoisiId.value = id
        }
    }

    fun ajouterJournal(texte: String, auteur: String) {
        val c = chantierActif.value ?: return
        viewModelScope.launch {
            base.journalDao().ajouter(
                EntreeJournal(chantierId = c.id, texte = texte, auteur = auteur)
            )
        }
    }

    fun ajouterControle(titre: String, resultat: String, observations: String, auteur: String) {
        val c = chantierActif.value ?: return
        viewModelScope.launch {
            base.controleDao().ajouter(
                Controle(
                    chantierId = c.id,
                    titre = titre,
                    resultat = resultat,
                    observations = observations,
                    auteur = auteur
                )
            )
        }
    }

    fun ajouterAction(titre: String, responsable: String) {
        val c = chantierActif.value ?: return
        viewModelScope.launch {
            base.actionDao().ajouter(
                ActionSuivi(chantierId = c.id, titre = titre, responsable = responsable)
            )
        }
    }
}
