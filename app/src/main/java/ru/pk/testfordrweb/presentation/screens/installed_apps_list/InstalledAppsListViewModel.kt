package ru.pk.testfordrweb.presentation.screens.installed_apps_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import ru.pk.testfordrweb.utils.extensions.execute
import javax.inject.Inject

@HiltViewModel
class InstalledAppsListViewModel @Inject constructor(
    private val deviceManager: DeviceManager,
    private val resourceManager: ResourceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(InstalledAppsListState())
    val uiState = _uiState
        .onStart {
            getApplications()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = InstalledAppsListState()
        )

    private val _uiAction = Channel<InstalledAppsListUiAction>()
    val uiAction = _uiAction.receiveAsFlow()

    fun handleIntent(intent: InstalledAppsListIntent) {
        when (intent) {
            is InstalledAppsListIntent.OnCardClick -> {
                _uiAction.trySend(
                    InstalledAppsListUiAction.NavigateToDetailsScreen(intent.model.packageName)
                )
            }
        }
    }

    private fun getApplications() = viewModelScope.execute(
        source = {
            deviceManager.getInstalledAppsDefaultInfo()
        },
        onSuccess = { installedApps ->
            _uiState.update {
                it.copy(
                    installedAppsList = installedApps
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
                InstalledAppsListUiAction.ShowError(
                    throwable.localizedMessage ?: resourceManager.getString(R.string.error)
                )
            )
        }
    )
}