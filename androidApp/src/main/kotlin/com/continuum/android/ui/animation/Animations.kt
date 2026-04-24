package com.continuum.android.ui.animation

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun breatheAnimation(): State<Float> {
    val inf = rememberInfiniteTransition(label = "breathe")
    return inf.animateFloat(
        initialValue = 1f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(tween(4000, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "scale"
    )
}

@Composable
fun pulseAlphaAnimation(): State<Float> {
    val inf = rememberInfiniteTransition(label = "pulse")
    return inf.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "alpha"
    )
}

@Composable
fun ringPulseAnimation(): State<Float> {
    val inf = rememberInfiniteTransition(label = "ring")
    return inf.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(tween(1200, easing = EaseOutQuad), RepeatMode.Restart),
        label = "radius"
    )
}

@Composable
fun AnimatedBlurOrb(
    color: Color,
    size: Dp,
    startX: Float,
    startY: Float,
    dxTarget: Float = 28f,
    dyTarget: Float = -18f,
    durationMs: Int = 9000,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "orb")
    val dx by transition.animateFloat(
        initialValue = 0f,
        targetValue = dxTarget,
        animationSpec = infiniteRepeatable(tween(durationMs, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "dx"
    )
    val dy by transition.animateFloat(
        initialValue = 0f,
        targetValue = dyTarget,
        animationSpec = infiniteRepeatable(tween((durationMs * 0.78).toInt(), easing = EaseInOutSine), RepeatMode.Reverse),
        label = "dy"
    )
    Canvas(modifier = modifier.fillMaxSize()) {
        val radius = size.toPx() / 2f
        val center = Offset(startX + dx, startY + dy)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color, Color.Transparent),
                center = center,
                radius = radius
            ),
            radius = radius,
            center = center
        )
    }
}
