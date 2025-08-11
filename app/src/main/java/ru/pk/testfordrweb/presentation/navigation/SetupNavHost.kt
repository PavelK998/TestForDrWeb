package ru.pk.testfordrweb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.InstalledAppsListScreen
    ) {
        composable<Destination.InstalledAppsListScreen> {

        }
    }
}