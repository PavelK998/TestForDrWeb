package ru.pk.testfordrweb.presentation.screens.installed_apps_list.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pk.testfordrweb.R
import ru.pk.testfordrweb.domain.model.InstalledAppDefaultInfoModel
import ru.pk.testfordrweb.ui.theme.TestForDrWebTheme

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    model: InstalledAppDefaultInfoModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Min),
        onClick = onClick,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight().padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .padding(end = 8.dp),
                painter = painterResource(R.drawable.android),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = model.appName
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppCardPreview() {
    TestForDrWebTheme {
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            model =
                InstalledAppDefaultInfoModel(
                    appName = "Test app 1",
                    packageName = "com.test.app",
                ),
            onClick = {}
        )
    }
}