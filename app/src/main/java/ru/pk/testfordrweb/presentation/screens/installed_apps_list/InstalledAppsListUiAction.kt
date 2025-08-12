package ru.pk.testfordrweb.presentation.screens.installed_apps_list

sealed interface InstalledAppsListUiAction {
    data class NavigateToDetailsScreen(val packageName: String) : InstalledAppsListUiAction
}