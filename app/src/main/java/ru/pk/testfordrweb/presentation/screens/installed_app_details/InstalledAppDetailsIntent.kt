package ru.pk.testfordrweb.presentation.screens.installed_app_details

sealed interface InstalledAppDetailsIntent {
    data object OnNavigationIconClick: InstalledAppDetailsIntent

    data object OnLaunchBtnClick: InstalledAppDetailsIntent
}