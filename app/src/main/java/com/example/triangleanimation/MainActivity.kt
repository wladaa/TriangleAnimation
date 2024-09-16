package com.example.triangleanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RotatingTriangle()
        }
    }
}

@Composable
fun RotatingTriangle() {
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    coroutineScope.launch {
                        rotation.animateTo(
                            targetValue = rotation.value + 360f,
                            animationSpec = tween(durationMillis = 1000)
                        )
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2


            rotate(rotation.value, Offset(centerX, centerY)) {
                drawPath(
                    path = trianglePath(centerX, centerY),
                    color = Color.Black
                )
            }
        }
    }
}


fun trianglePath(centerX: Float, centerY: Float): androidx.compose.ui.graphics.Path {
    val path = androidx.compose.ui.graphics.Path()
    path.moveTo(centerX, centerY - 400f)
    path.lineTo(centerX - 400f, centerY + 400f)
    path.lineTo(centerX + 400f, centerY + 400f)
    path.close()
    return path
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RotatingTriangle()
}

