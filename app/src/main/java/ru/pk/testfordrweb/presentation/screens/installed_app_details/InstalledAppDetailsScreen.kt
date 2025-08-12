package ru.pk.testfordrweb.presentation.screens.installed_app_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pk.testfordrweb.R
import ru.pk.testfordrweb.presentation.components.DefaultButton
import ru.pk.testfordrweb.presentation.components.Loading
import ru.pk.testfordrweb.presentation.components.TitleText
import ru.pk.testfordrweb.presentation.screens.installed_app_details.model.InstalledAppDetailsUiModel
import ru.pk.testfordrweb.ui.theme.TestForDrWebTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstalledAppDetailsScreen(
    uiState: InstalledAppDetailsState,
    handleIntent: (InstalledAppDetailsIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {
                            handleIntent(InstalledAppDetailsIntent.OnNavigationIconClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AnimatedContent(
            uiState.isLoading,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(800)
                ) togetherWith fadeOut(animationSpec = tween(300))
            },
        ) { isLoading ->
            when (isLoading) {
                true -> {
                    Loading(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
                false -> {
                    ScreenContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp),
                        uiState = uiState,
                        handleIntent = handleIntent
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    uiState: InstalledAppDetailsState,
    handleIntent: (InstalledAppDetailsIntent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = uiState.installedAppDetailsModel.appName
            )
            Text(
                text = uiState.installedAppDetailsModel.version
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            DefaultButton(
                buttonText = stringResource(R.string.detail_screen_button),
                onClick = {
                    handleIntent(InstalledAppDetailsIntent.OnLaunchBtnClick)
                }
            )
        }


        TitleText(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(R.string.detail_screen_package_name)
        )
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = uiState.installedAppDetailsModel.packageName
        )
        TitleText(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.detail_screen_apk_checksum)
        )
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = uiState.installedAppDetailsModel.apkChecksum
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TestForDrWebTheme {
        InstalledAppDetailsScreen(
            uiState = InstalledAppDetailsState(
                installedAppDetailsModel = InstalledAppDetailsUiModel(
                    appName = "Test app",
                    version = "v1",
                    packageName = "com.test.app",
                    apkChecksum = "542652345235423"
                )
            ),
            handleIntent = {}
        )
    }
}