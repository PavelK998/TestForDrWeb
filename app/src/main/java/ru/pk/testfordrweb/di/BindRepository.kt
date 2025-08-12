package ru.pk.testfordrweb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.pk.testfordrweb.data.manager.DeviceManagerImpl
import ru.pk.testfordrweb.domain.manager.DeviceManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepository {
    @Binds
    @Singleton
    abstract fun bindDeviceManager(
        deviceManagerImpl: DeviceManagerImpl
    ): DeviceManager
}