package ru.pk.testfordrweb.data.manager

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.pk.testfordrweb.domain.manager.ResourceManager
import javax.inject.Inject

class ResourceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ResourceManager {
    override fun getString(resourceId: Int): String {
        return context.getString(resourceId)
    }
}