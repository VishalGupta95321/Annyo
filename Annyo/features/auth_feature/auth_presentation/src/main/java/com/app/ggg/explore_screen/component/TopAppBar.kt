package com.app.ggg.explore_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.auth_presentation.R
import com.example.ui.AppLogo
import com.example.ui.LogoSize

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxHeight(0.06f)
            .then(modifier)
    ) {
       AppLogo(
           modifier = Modifier
               .align(Alignment.Center),
           logoSize = LogoSize.SMALL
       )
        Icon(
            modifier = Modifier
                .size(45.dp)
                .padding(10.dp)
                .align(Alignment.CenterStart),
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(id = R.drawable.notification),
            contentDescription = null
        )
        Icon(
            modifier = Modifier
                .size(45.dp)
                .padding(10.dp)
                .align(Alignment.CenterEnd),
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(id = R.drawable.prefrences),
            contentDescription = null
        )
    }
}
