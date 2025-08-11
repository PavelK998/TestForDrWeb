package ru.pk.testfordrweb.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object InstalledAppsListScreen : Destination

    @Serializable
    data class InstalledAppsDetailsScreen(val packageName: String) : Destination
}