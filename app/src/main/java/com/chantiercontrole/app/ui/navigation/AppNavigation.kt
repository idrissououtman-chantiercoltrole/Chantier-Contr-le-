package com.chantiercontrole.app.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.chantiercontrole.app.ui.MainViewModel
import com.chantiercontrole.app.ui.screens.ActionsScreen
import com.chantiercontrole.app.ui.screens.AccueilScreen
import com.chantiercontrole.app.ui.screens.ControlesScreen
import com.chantiercontrole.app.ui.screens.JournalScreen
import com.chantiercontrole.app.ui.screens.RapportScreen

private val onglets = listOf("Accueil", "Journal", "Contrôles", "Actions", "Rapport")

@Composable
fun AppNavigation() {
    val activite = LocalContext.current as ComponentActivity
    val vm = remember { ViewModelProvider(activite)[MainViewModel::class.java] }
    var choisi by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                onglets.forEachIndexed { index, nom ->
                    NavigationBarItem(
                        selected = choisi == index,
                        onClick = { choisi = index },
                        icon = {},
                        label = { Text(nom) },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { marges ->
        Box(modifier = Modifier.fillMaxSize().padding(marges)) {
            when (choisi) {
                0 -> AccueilScreen(vm)
                1 -> JournalScreen(vm)
                2 -> ControlesScreen(vm)
                3 -> ActionsScreen(vm)
                else -> RapportScreen(vm)
            }
        }
    }
}
