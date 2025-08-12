package ru.pk.testfordrweb.presentation.screens.installed_apps_list

import ru.pk.testfordrweb.domain.model.InstalledAppDefaultInfoModel

sealed interface InstalledAppsListIntent {
    data class OnCardClick(val model: InstalledAppDefaultInfoModel): InstalledAppsListIntent
}