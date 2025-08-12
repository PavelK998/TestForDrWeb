package ru.pk.testfordrweb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.pk.testfordrweb.presentation.screens.installed_app_details.InstalledAppDetailsScreen
import ru.pk.testfordrweb.presentation.screens.installed_app_details.InstalledAppDetailsUiAction
import ru.pk.testfordrweb.presentation.screens.installed_app_details.InstalledAppDetailsViewModel
import ru.pk.testfordrweb.presentation.screens.installed_apps_list.InstalledAppsListScreen
import ru.pk.testfordrweb.presentation.screens.installed_apps_list.InstalledAppsListUiAction
import ru.pk.testfordrweb.presentation.screens.installed_apps_list.InstalledAppsListViewModel
import ru.pk.testfordrweb.utils.ObserveAsActions

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.InstalledAppsListScreen
    ) {
        composable<Destination.InstalledAppsListScreen> {
            val viewModel = hiltViewModel<InstalledAppsListViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            ObserveAsActions(viewModel.uiAction) { action ->
                when(action) {
                    is InstalledAppsListUiAction.NavigateToDetailsScreen -> {
                        navController.navigate(
                            Destination.InstalledAppsDetailsScreen(action.packageName)
                        )
                    }
                }
            }
            InstalledAppsListScreen(
                uiState = uiState,
                handleIntent = viewModel::handleIntent
            )
        }

        composable<Destination.InstalledAppsDetailsScreen> {
            val viewModel = hiltViewModel<InstalledAppDetailsViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            ObserveAsActions(viewModel.uiAction) { action ->
                when(action) {
                    InstalledAppDetailsUiAction.NavigateUp -> {
                        navController.navigateUp()
                    }
                }
            }
            InstalledAppDetailsScreen(
                uiState = uiState,
                handleIntent = viewModel::handleIntent
            )
        }
    }
}