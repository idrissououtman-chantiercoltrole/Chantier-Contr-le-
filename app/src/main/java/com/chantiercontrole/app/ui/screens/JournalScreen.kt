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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formaterDate(instant: Long): String =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE).format(Date(instant))

@Composable
fun JournalScreen(vm: MainViewModel) {
    val chantier by vm.chantierActif.collectAsState()
    val journal by vm.journal.collectAsState()

    var texte by remember { mutableStateOf("") }
    var auteur by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Journal de chantier",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        if (chantier == null) {
            Text("Créez d'abord un chantier dans l'onglet Accueil.")
        } else {
            OutlinedTextField(
                value = texte,
                onValueChange = { texte = it },
                label = { Text("Que s'est-il passé ?") },
                minLines = 3,
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
                    if (texte.isNotBlank()) {
                        vm.ajouterJournal(texte.trim(), auteur.trim())
                        texte = ""
                    }
                }
            ) {
                Text("Ajouter au journal")
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(journal, key = { it.id }) { entree ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                formaterDate(entree.date),
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(entree.texte)
                            if (entree.auteur.isNotBlank()) {
                                Text(
                                    "Par ${entree.auteur}",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
