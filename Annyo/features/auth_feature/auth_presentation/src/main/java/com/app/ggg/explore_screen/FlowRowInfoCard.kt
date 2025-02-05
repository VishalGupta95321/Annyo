package com.app.ggg.explore_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.ggg.item
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun FlowRowInfoCard(
    items: List<item> = listOf(),
    title: String = ""
) {
    if (title.isNotBlank())
        HeadingLabel(text = title)

    FlowRow(
        modifier = Modifier
            .padding(vertical = 10.dp),
//            .background(
//                brush = Brush.linearGradient(
//                    listOf(
//                        MaterialTheme.colorScheme.primaryContainer,
//                        MaterialTheme.colorScheme.secondaryContainer
//                    )
//                ), shape = RoundedCornerShape(15.dp)
//            ),
        mainAxisSize = SizeMode.Expand,
        mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
    ) {
        items.forEach {
            InfoCardItem(
                modifier = Modifier
                    .padding(5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .height(45.dp),
                text = it.name,
                icon = it.icon
            )
        }
    }
}
