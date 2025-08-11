package ru.pk.testfordrweb.domain.manager

import ru.pk.testfordrweb.domain.model.InstalledAppDefaultInfoModel
import ru.pk.testfordrweb.domain.model.InstalledAppDetailsModel

interface DeviceManager {
    suspend fun getInstalledAppDetailsInfo(): InstalledAppDetailsModel

    suspend fun getInstalledAppsDefaultInfo(): List<InstalledAppDefaultInfoModel>

}