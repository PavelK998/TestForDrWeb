package ru.pk.testfordrweb.presentation.screens.installed_apps_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pk.testfordrweb.R
import ru.pk.testfordrweb.domain.model.InstalledAppDefaultInfoModel
import ru.pk.testfordrweb.presentation.screens.installed_apps_list.components.AppCard
import ru.pk.testfordrweb.ui.theme.TestForDrWebTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstalledAppsListScreen(
    uiState: InstalledAppsListState,
    handleIntent: (InstalledAppsListIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.list_screen_top_bar_name)
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = uiState.installedAppsList,
                key = { item ->
                    item.packageName
                }
            ) { appModel ->
                AppCard(
                    modifier = Modifier.fillMaxWidth(),
                    model = appModel,
                    onClick = {
                        handleIntent(InstalledAppsListIntent.OnCardClick(appModel))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TestForDrWebTheme {
        InstalledAppsListScreen(
            uiState = InstalledAppsListState(
                installedAppsList = (1..20).map {
                    InstalledAppDefaultInfoModel(
                        appName = "Test app 1",
                        packageName = "com.test.app$it",
                    )
                }
            ),
            handleIntent = {}
        )
    }
}