package ru.pk.testfordrweb.presentation.screens.installed_app_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import ru.pk.testfordrweb.domain.manager.DeviceManager
import javax.inject.Inject

@HiltViewModel
class InstalledAppDetailsViewModel @Inject constructor(
    private val deviceManager: DeviceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(InstalledAppDetailsState())
    val uiState = _uiState
        .onStart {

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

            }
        }
    }
}