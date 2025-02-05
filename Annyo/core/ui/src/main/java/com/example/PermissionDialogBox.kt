package com.example

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.util.PermissionDialogContent

@Composable
fun PermissionDialogBox(
    modifier: Modifier = Modifier,
    dialogContent: PermissionDialogContent,
    isPermissionPermanentlyDeclined: Boolean,
    onGoToSettingsClick: () -> Unit,
    onAllowAccessClick: () -> Unit,
    onNotNowClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(enabled = false) {}
            .background(
                color = Color(0x23372E3A)
            )
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.6f),
            elevation = CardDefaults
                .elevatedCardElevation(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(0.65f)
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .weight(5f),
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = dialogContent.getIcon()),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 10.dp),
                    textAlign = TextAlign.Center,
                    text = if (isPermissionPermanentlyDeclined)
                        dialogContent.getSettingGuidanceDescription()
                    else dialogContent.getDescription(),
                    style = MaterialTheme.typography.bodyMedium
                        .copy(color = MaterialTheme.colorScheme.secondary)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                        .clip(RoundedCornerShape(35.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = {
                        if (isPermissionPermanentlyDeclined)
                            onGoToSettingsClick()
                        else onAllowAccessClick()
                    },
                    enabled = true
                ) {
                    Text(
                        modifier = Modifier,
                        text = if (isPermissionPermanentlyDeclined)
                            "Go to Settings"
                        else "Allow Access",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    modifier = Modifier
                        .clickable { onNotNowClick() }
                        .padding(vertical = 10.dp, horizontal = 10.dp),
                    text = "Not Now",
                    style = MaterialTheme.typography.titleMedium
                        .copy(color = Color.Gray)
                )
            }
        }
    }
    BackHandler{onBackClick()}
}

@Preview(showBackground = true)
@Composable
fun Pr20() {
//    PermissionDialogBox(
//        onNotNowClick = {},
//        onAllowAccessClick = {},
//        onGoToSettingsClick = {},
//        dialogContent = LocationDialogContent()
//    )
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=portrait"
)

@Composable
fun Pr21() {
//    PermissionDialogBox(
//        onNotNowClick = {},
//        onAllowAccessClick = {},
//        onGoToSettingsClick = {},
//        dialogContent = LocationDialogContent()
//    )
}


