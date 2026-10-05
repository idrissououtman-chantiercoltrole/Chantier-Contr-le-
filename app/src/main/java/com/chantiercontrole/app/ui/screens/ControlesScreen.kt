package com.chantiercontrole.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chantiercontrole.app.ui.MainViewModel

private val resultats = listOf("Conforme", "Non conforme", "A verifier")

@Composable
fun ControlesScreen(vm: MainViewModel) {
    val chantier by vm.chantierActif.collectAsState()
    val controles by vm.controles.collectAsState()

    var titre by remember { mutableStateOf("") }
    var resultat by remember { mutableStateOf("A verifier") }
    var observations by remember { mutableStateOf("") }
    var auteur by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Contrôles",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        if (chantier == null) {
            Text("Créez d'abord un chantier dans l'onglet Accueil.")
        } else {
            OutlinedTextField(
                value = titre,
                onValueChange = { titre = it },
                label = { Text("Objet du contrôle") },
                modifier = Modifier.fillMaxWidth()
            )
            resultats.forEach { choix ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = resultat == choix,
                        onClick = { resultat = choix }
                    )
                    Text(choix)
                }
            }
            OutlinedTextField(
                value = observations,
                onValueChange = { observations = it },
                label = { Text("Observations") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = auteur,
                onValueChange = { auteur = it },
                label = { Text("Votre nom") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (titre.isNotBlank()) {
                        vm.ajouterControle(titre.trim(), resultat, observations.trim(), auteur.trim())
                        titre = ""
                        observations = ""
                        resultat = "A verifier"
                    }
                }
            ) {
                Text("Enregistrer le contrôle")
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(controles, key = { it.id }) { controle ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(controle.titre, style = MaterialTheme.typography.titleMedium)
                            Text("Résultat : ${controle.resultat}")
                            if (controle.observations.isNotBlank()) {
                                Text(controle.observations)
                            }
                            Text(
                                formaterDate(controle.date) +
                                    if (controle.auteur.isNotBlank()) " - ${controle.auteur}" else "",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
