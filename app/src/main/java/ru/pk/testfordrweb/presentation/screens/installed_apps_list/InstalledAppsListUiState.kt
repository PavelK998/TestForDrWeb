package ru.pk.testfordrweb.presentation.screens.installed_apps_list

import ru.pk.testfordrweb.domain.model.InstalledAppDefaultInfoModel

data class InstalledAppsListState(
    val installedAppsList: List<InstalledAppDefaultInfoModel> = emptyList()
)