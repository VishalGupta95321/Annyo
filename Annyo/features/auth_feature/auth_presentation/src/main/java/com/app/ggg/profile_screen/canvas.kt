package com.app.auth_presentatio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CanvasTry() {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer
    )
    val colors2 = listOf(
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.secondaryContainer
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Canvas(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(10.dp),
        ){
            //instagramLogo(colors)
           // circle(colors)
            //progressIndicator(colors)
            //messengerLogo(colors)
            //googlePhotosLogo()
            weatherLogo(colors,colors2)
        }
    }
}

fun DrawScope.instagramLogo(
    colors:List<Color>
){
    drawRoundRect(
        brush = Brush.linearGradient(colors = colors),
        cornerRadius = CornerRadius(60f, 60f),
        style = Stroke(width = 30f, cap = StrokeCap.Round)
    )
    drawCircle(
        brush = Brush.linearGradient(colors = colors),
        radius = 45f,
        style = Stroke(width = 15f, cap = StrokeCap.Round)
    )
    drawCircle(
        brush = Brush.linearGradient(colors = colors),
        radius = 13f,
        center = Offset(this.size.width * .80f, this.size.height * 0.20f),
    )
}

fun DrawScope.circle(
    colors:List<Color>
){
    drawCircle(
        color = colors.first(),
        radius = 300f,
        style = Stroke(
            width = 50f,
            cap = StrokeCap.Round,
        )
    )
}

fun  DrawScope.progressIndicator(
    colors:Color,
    indicatorWidth:Float = 40f,
    sweepAngle:Float = 240f
){
    drawArc(
        color = colors,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(0f,0f),
    )
}

fun DrawScope.messengerLogo(
    colors:List<Color>,
){
    val trianglePath = Path().let {
        it.moveTo(this.size.width * .30f, this.size.height * .77f)
        it.lineTo(this.size.width * .20f, this.size.height * 0.95f)
        it.lineTo(this.size.width * .37f, this.size.height * 0.86f)
        it.close()
        it
    }

    val electricPath = Path().let {
        it.moveTo(this.size.width * .20f, this.size.height * 0.60f)
        it.lineTo(this.size.width * .45f, this.size.height * 0.35f)
        it.lineTo(this.size.width * 0.56f, this.size.height * 0.46f)
        it.lineTo(this.size.width * 0.78f, this.size.height * 0.35f)
        it.lineTo(this.size.width * 0.54f, this.size.height * 0.60f)
        it.lineTo(this.size.width * 0.43f, this.size.height * 0.45f)
        it.close()
        it
    }

    drawOval(
        Brush.verticalGradient(colors = colors),
        size = Size(this.size.width, this.size.height * 0.95f),
    )

    drawPath(
        path = trianglePath,
        Brush.verticalGradient(colors = colors),
       // style = Stroke(width = 15f, cap = StrokeCap.Round)
    )

    drawPath(path = electricPath, color = Color.White)
}

fun DrawScope.googlePhotosLogo(){
    drawArc(
        color = Color(0xFFf04231),
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = true,
        size = Size(size.width * .50f, size.height * .50f),
        topLeft = Offset(size.width * .25f, 0f)
    )
    drawArc(
        color = Color(0xFF4385f7),
        startAngle = 0f,
        sweepAngle = 190f,
        useCenter = true,
        size = Size(size.width * .50f, size.height * .50f),
        topLeft = Offset(size.width * .50f, size.height * .25f)
    )
    drawArc(
        color = Color(0xFF30a952),
        startAngle = 0f,
        sweepAngle = -180f,
        useCenter = true,
        size = Size(size.width * .50f, size.height * .50f),
        topLeft = Offset(0f, size.height * .25f)
    )

    drawArc(
        color = Color(0xFFffbf00),
        startAngle = 270f,
        sweepAngle = -180f,
        useCenter = true,
        size = Size(size.width * .50f, size.height * .50f),
        topLeft = Offset(size.width * .25f, size.height * .50f)
    )
}

fun DrawScope.weatherLogo(
    colors:List<Color>,
    colors2:List<Color>,
){
    val width = size.width
    val height = size.height
    val path = Path().apply {
        moveTo(width.times(.76f), height.times(.72f))
        cubicTo(
            width.times(.93f),
            height.times(.72f),
            width.times(.98f),
            height.times(.41f),
            width.times(.76f),
            height.times(.40f)
        )
        cubicTo(
            width.times(.75f),
            height.times(.21f),
            width.times(.42f),
            height.times(.21f),
            width.times(.44f),
            height.times(.40f)
        )
        cubicTo(
            width.times(.25f),
            height.times(.40f),
            width.times(.20f),
            height.times(.69f),
            width.times(.41f),
            height.times(.72f)
        )
        close()
    }
    drawRoundRect(
        brush = Brush.verticalGradient(colors),
        cornerRadius = CornerRadius(50f, 50f),

        )
    drawCircle(
        brush = Brush.verticalGradient(colors2),
        radius = width.times(.17f),
        center = Offset(width.times(.35f), height.times(.35f))
    )
    drawPath(path = path, color = Color.White.copy(alpha = .90f))
}


@Preview
@Composable
fun p15() {
    CanvasTry()
}