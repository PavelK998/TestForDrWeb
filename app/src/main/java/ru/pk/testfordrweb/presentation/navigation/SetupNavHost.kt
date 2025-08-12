package ru.pk.testfordrweb.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.pk.testfordrweb.R
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
            val context = LocalContext.current
            ObserveAsActions(viewModel.uiAction) { action ->
                when (action) {
                    is InstalledAppsListUiAction.NavigateToDetailsScreen -> {
                        navController.navigate(
                            Destination.InstalledAppsDetailsScreen(action.packageName)
                        )
                    }

                    is InstalledAppsListUiAction.ShowError -> {
                        Toast.makeText(
                            context,
                            action.message,
                            Toast.LENGTH_SHORT
                        ).show()
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
            val context = LocalContext.current
            ObserveAsActions(viewModel.uiAction) { action ->
                when (action) {
                    InstalledAppDetailsUiAction.NavigateUp -> {
                        navController.navigateUp()
                    }

                    is InstalledAppDetailsUiAction.LaunchApp -> {
                        val packageManager = context.packageManager
                        val launchIntent =
                            packageManager.getLaunchIntentForPackage(action.packageName)
                        if (launchIntent != null) {
                            context.startActivity(launchIntent)
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.start_app_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is InstalledAppDetailsUiAction.ShowError -> {
                        Toast.makeText(
                            context,
                            action.message,
                            Toast.LENGTH_SHORT
                        ).show()
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