package ru.pk.testfordrweb.data.manager

import android.content.Context
import android.content.pm.Checksum
import android.os.Build
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
) : DeviceManager {
    override suspend fun getInstalledAppDetailsInfo(packageName: String) =
        withContext(Dispatchers.IO) {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            val apkPath = appInfo.sourceDir
            //Проверяем, если версия SDK выше 31, то используем requestChecksums,
            // иначе метод calculateApkChecksumSHA256
            val apkCheckSum = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                var sha256String = ""
                packageManager.requestChecksums(
                    packageInfo.packageName,
                    //Включает сплит-apk для приложений установленных через bundle
                    true,
                    Checksum.TYPE_WHOLE_MERKLE_ROOT_4K_SHA256,
                    emptyList()
                ) { checksums ->
                    checksums.find { it.type == Checksum.TYPE_WHOLE_MERKLE_ROOT_4K_SHA256 }?.let {
                        sha256String = it.value.joinToString("") { byte -> "%02x".format(byte) }
                    }
                }
                sha256String
            } else {
                calculateApkChecksumSHA256(File(apkPath))
            }
            InstalledAppDetailsModel(
                appName = packageManager.getApplicationLabel(appInfo).toString(),
                version = packageInfo.versionName.toString(),
                packageName = packageInfo.packageName,
                apkChecksum = apkCheckSum
            )
        }

    override suspend fun getInstalledAppsDefaultInfo() = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        //Получаем установленные прилолжения за исключением этого приложения
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
        //Создаем объект MessageDigest, алгоритм SHA-256
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { inputStream ->
            //Создаем буфер объемом 8кб, используем для чтения файла частями
            val buffer = ByteArray(8192)
            var read: Int
            //Читаем по 8кб, проходим до конца файла
            while (inputStream.read(buffer).also { read = it } != -1) {
                //Передаем прочитанные данные в MessageDigest
                digest.update(buffer, 0, read)
            }
        }
        //Влзвращаем ByteArray, каждый байт преобразуем в шестнадцатеричное представление
        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}