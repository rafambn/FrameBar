package com.rafambn.framebarcomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.rafambn.framebar.FrameBar
import com.rafambn.framebar.Marker
import com.rafambn.framebar.enums.CoercePointer
import com.rafambn.framebar.enums.PointerSelection
import com.rafambn.framebarcomposeapp.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Surface(modifier = Modifier.fillMaxSize()) {

        val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

        val markers = remember(imageBitmap.value) {
            mutableStateListOf(
                Marker(width = 10.dp, bitmap = imageBitmap.value),
                Marker(width = 10.dp, topOffset = 50.dp),
                Marker(width = 10.dp, bitmap = imageBitmap.value),
                Marker(width = 10.dp, topOffset = 20.dp),
                Marker(width = 10.dp),
                Marker(width = 10.dp, topOffset = 20.dp),
                Marker(width = 10.dp, bitmap = imageBitmap.value)
            )
        }

        val pointer = remember(imageBitmap.value) {
            mutableStateOf(
                Marker(
                    width = 10.dp,
                    height = 40.dp,
                    topOffset = 5.dp,
                    color = Color.Yellow,
                    bitmap = imageBitmap.value
                )
            )
        }
        val valor = remember { mutableStateOf(0F) }
        Box {
            FrameBar(
                modifier = Modifier.align(Alignment.Center),
                pointerSelection = PointerSelection.LEFT,
                coercedPointer = CoercePointer.NOT_COERCED,
                pointer = pointer.value,
                markers = markers,
                value = valor.value,
                onValueChange = {
                    valor.value = it
                    println(it)
                },
                enabled = true,
            )
        }
    }
}