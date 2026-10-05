package com.chantiercontrole.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
fun ActionsScreen(vm: MainViewModel) {
    val chantier by vm.chantierActif.collectAsState()
    val actions by vm.actions.collectAsState()

    var titre by remember { mutableStateOf("") }
    var responsable by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Actions à mener",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        if (chantier == null) {
            Text("Créez d'abord un chantier dans l'onglet Accueil.")
        } else {
            OutlinedTextField(
                value = titre,
                onValueChange = { titre = it },
                label = { Text("Action à mener") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = responsable,
                onValueChange = { responsable = it },
                label = { Text("Responsable") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (titre.isNotBlank()) {
                        vm.ajouterAction(titre.trim(), responsable.trim())
                        titre = ""
                        responsable = ""
                    }
                }
            ) {
                Text("Ajouter l'action")
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(actions, key = { it.id }) { action ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(action.titre, style = MaterialTheme.typography.titleMedium)
                            if (action.responsable.isNotBlank()) {
                                Text("Responsable : ${action.responsable}")
                            }
                            Text("Statut : ${action.statut}")
                            Text(
                                "Créée le " + formaterDate(action.creeLe),
                                style = MaterialTheme.typography.labelMedium
                            )
                            if (action.statut == "Ouverte") {
                                OutlinedButton(
                                    onClick = { vm.terminerAction(action) }
                                ) {
                                    Text("Marquer terminée")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
