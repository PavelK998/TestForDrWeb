package ru.pk.testfordrweb.presentation.screens.installed_app_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import ru.pk.testfordrweb.R
import ru.pk.testfordrweb.domain.manager.DeviceManager
import ru.pk.testfordrweb.domain.manager.ResourceManager
import ru.pk.testfordrweb.presentation.navigation.Destination
import ru.pk.testfordrweb.presentation.screens.installed_app_details.model.InstalledAppDetailsUiModel
import ru.pk.testfordrweb.utils.extensions.execute
import javax.inject.Inject

@HiltViewModel
class InstalledAppDetailsViewModel @Inject constructor(
    private val deviceManager: DeviceManager,
    private val resourceManager: ResourceManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(InstalledAppDetailsState())
    val uiState = _uiState
        .onStart {
            val packageName =
                savedStateHandle.toRoute<Destination.InstalledAppsDetailsScreen>().packageName
            if (packageName.isNotBlank()) {
                getAppInfo(packageName)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = InstalledAppDetailsState()
        )

    private val _uiAction = Channel<InstalledAppDetailsUiAction>()
    val uiAction = _uiAction.receiveAsFlow()

    fun handleIntent(intent: InstalledAppDetailsIntent) {
        when (intent) {
            InstalledAppDetailsIntent.OnNavigationIconClick -> {
                _uiAction.trySend(InstalledAppDetailsUiAction.NavigateUp)
            }

            InstalledAppDetailsIntent.OnLaunchBtnClick -> {
                _uiAction.trySend(
                    InstalledAppDetailsUiAction.LaunchApp(
                        _uiState.value.installedAppDetailsModel.packageName
                    )
                )
            }
        }
    }

    private fun getAppInfo(packageName: String) = viewModelScope.execute(
        source = {
            deviceManager.getInstalledAppDetailsInfo(packageName)
        },
        onSuccess = { model ->
            _uiState.update {
                it.copy(
                    installedAppDetailsModel = InstalledAppDetailsUiModel(
                        appName = model.appName,
                        version = "v${model.version}",
                        packageName = model.packageName,
                        apkChecksum = model.apkChecksum
                    )
                )
            }
        },
        onLoading = { isLoading ->
            _uiState.update {
                it.copy(
                    isLoading = isLoading
                )
            }
        },
        onError = { throwable ->
            _uiAction.trySend(
                InstalledAppDetailsUiAction.ShowError(
                    throwable.localizedMessage ?: resourceManager.getString(R.string.error)
                )
            )
        }
    )
}