package com.chantiercontrole.app.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chantiercontrole.app.data.ActionSuivi
import com.chantiercontrole.app.data.Chantier
import com.chantiercontrole.app.data.Controle
import com.chantiercontrole.app.data.EntreeJournal
import com.chantiercontrole.app.ui.MainViewModel

private fun construireRapport(
    c: Chantier,
    journal: List<EntreeJournal>,
    controles: List<Controle>,
    actions: List<ActionSuivi>
): String {
    val sb = StringBuilder()
    sb.appendLine("RAPPORT DE SUIVI - ${c.nom}")
    sb.appendLine("Tronçon : ${c.tronconDebut} - ${c.tronconFin}")
    sb.appendLine("Édité le ${formaterDate(System.currentTimeMillis())}")
    sb.appendLine()

    sb.appendLine("JOURNAL (${journal.size})")
    journal.forEach {
        val par = if (it.auteur.isNotBlank()) " (${it.auteur})" else ""
        sb.appendLine("- ${formaterDate(it.date)}$par : ${it.texte}")
    }
    sb.appendLine()

    sb.appendLine("CONTRÔLES (${controles.size})")
    controles.forEach {
        sb.appendLine("- ${formaterDate(it.date)} : ${it.titre} -> ${it.resultat}")
        if (it.observations.isNotBlank()) sb.appendLine("  Observations : ${it.observations}")
    }
    sb.appendLine()

    sb.appendLine("ACTIONS (${actions.size})")
    actions.forEach {
        val resp = if (it.responsable.isNotBlank()) " - ${it.responsable}" else ""
        sb.appendLine("- ${it.titre}$resp [${it.statut}]")
    }
    return sb.toString()
}

@Composable
fun RapportScreen(vm: MainViewModel) {
    val contexte = LocalContext.current
    val chantier by vm.chantierActif.collectAsState()
    val journal by vm.journal.collectAsState()
    val controles by vm.controles.collectAsState()
    val actions by vm.actions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Rapport",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        val c = chantier
        if (c == null) {
            Text("Créez d'abord un chantier dans l'onglet Accueil.")
        } else {
            val texte = construireRapport(c, journal, controles, actions)
            Button(
                onClick = {
                    val envoi = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Rapport de suivi - ${c.nom}")
                        putExtra(Intent.EXTRA_TEXT, texte)
                    }
                    contexte.startActivity(Intent.createChooser(envoi, "Partager le rapport"))
                }
            ) {
                Text("Partager le rapport")
            }
            Text(texte)
        }
    }
}
