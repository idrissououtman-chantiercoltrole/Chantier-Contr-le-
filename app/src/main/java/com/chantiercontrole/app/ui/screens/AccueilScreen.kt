package com.chantiercontrole.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chantiercontrole.app.ui.MainViewModel

@Composable
fun AccueilScreen(vm: MainViewModel) {
    val chantier by vm.chantierActif.collectAsState()
    val journal by vm.journal.collectAsState()
    val controles by vm.controles.collectAsState()
    val actions by vm.actions.collectAsState()

    var nom by remember { mutableStateOf("") }
    var debut by remember { mutableStateOf("") }
    var fin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Chantier Contrôle",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        val c = chantier
        if (c != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(c.nom, style = MaterialTheme.typography.titleLarge)
                    Text("Tronçon : ${c.tronconDebut} - ${c.tronconFin}")
                    Text("Entrées du journal : ${journal.size}")
                    Text("Contrôles : ${controles.size}")
                    Text("Actions ouvertes : ${actions.count { it.statut == "Ouverte" }}")
                }
            }
            Text("Ajouter un autre chantier", style = MaterialTheme.typography.titleMedium)
        } else {
            Text("Aucun chantier pour le moment. Créez le premier ci-dessous.")
        }

        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom du chantier") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = debut,
            onValueChange = { debut = it },
            label = { Text("Début du tronçon") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = fin,
            onValueChange = { fin = it },
            label = { Text("Fin du tronçon") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (nom.isNotBlank()) {
                    vm.creerChantier(nom.trim(), debut.trim(), fin.trim())
                    nom = ""
                    debut = ""
                    fin = ""
                }
            }
        ) {
            Text("Créer le chantier")
        }
    }
}
