package com.chantiercontrole.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chantiercontrole.app.ui.navigation.AppNavigation
import com.chantiercontrole.app.ui.theme.ChantierControleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChantierControleTheme {
                AppNavigation()
            }
        }
    }
}
