package ru.pk.testfordrweb.domain.model

data class InstalledAppDetailsModel(
    val appName: String,
    val version: String,
    val packageName: String,
    val apkChecksum: String
)
