package ru.pk.testfordrweb.presentation.screens.installed_app_details

sealed interface InstalledAppDetailsUiAction{
    data object NavigateUp: InstalledAppDetailsUiAction
}
