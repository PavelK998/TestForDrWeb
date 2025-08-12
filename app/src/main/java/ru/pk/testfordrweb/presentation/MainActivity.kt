package ru.pk.testfordrweb.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.pk.testfordrweb.presentation.navigation.SetupNavHost
import ru.pk.testfordrweb.ui.theme.TestForDrWebTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TestForDrWebTheme {
                SetupNavHost(navController = navController)
            }
        }
    }
}

