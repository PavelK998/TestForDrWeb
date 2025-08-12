package ru.pk.testfordrweb.presentation.screens.installed_app_details

sealed interface InstalledAppDetailsUiAction{
    data object NavigateUp: InstalledAppDetailsUiAction

    data class LaunchApp(val packageName: String): InstalledAppDetailsUiAction

    data class ShowError(val message: String): InstalledAppDetailsUiAction
}
