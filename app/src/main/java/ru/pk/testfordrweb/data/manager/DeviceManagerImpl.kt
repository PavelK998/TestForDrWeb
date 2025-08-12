package ru.pk.testfordrweb.data.manager

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.pk.testfordrweb.domain.manager.DeviceManager
import ru.pk.testfordrweb.domain.model.InstalledAppDefaultInfoModel
import ru.pk.testfordrweb.domain.model.InstalledAppDetailsModel
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import javax.inject.Inject

class DeviceManagerImpl @Inject constructor(
   @ApplicationContext private val context: Context
): DeviceManager {
    override suspend fun getInstalledAppDetailsInfo(packageName: String) = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        val apkPath = appInfo.sourceDir
        InstalledAppDetailsModel(
            appName = packageManager.getApplicationLabel(appInfo).toString(),
            version = packageInfo.versionName.toString(),
            packageName = packageInfo.packageName,
            apkChecksum = calculateApkChecksumSHA256(File(apkPath))

        )
    }

    override suspend fun getInstalledAppsDefaultInfo() = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val apps = packageManager.getInstalledApplications(0)
            .filter { it.packageName != context.packageName }
            .sortedBy {
                packageManager.getApplicationLabel(it).toString()
            }
        apps.map {
            InstalledAppDefaultInfoModel(
                appName = packageManager.getApplicationLabel(it).toString(),
                packageName = it.packageName
            )
        }
    }

    private fun calculateApkChecksumSHA256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { inputStream ->
           val buffer = ByteArray(8192)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}