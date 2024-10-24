package com.example.lab12

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment

@Composable
fun MapScreen() {
    val ArequipaLocation = LatLng(-16.4040102, -71.559611) // Arequipa, Perú
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ArequipaLocation, 12f)
    }

    val tiposMapas = listOf("Normal", "Satélite", "Híbrido", "Terreno", "Ninguno")
    var selectedMapType by remember { mutableStateOf(0) }
    var showDropdown by remember { mutableStateOf(false) }
    var showBsAiresMarker by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier
            .padding(16.dp)
            .clickable { showDropdown = true }
        ) {

            Text(text = tiposMapas[selectedMapType])
        }

        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            tiposMapas.forEachIndexed { index, tipo ->
                DropdownMenuItem(
                    onClick = {
                        selectedMapType = index
                        showDropdown = false
                    },
                    text = { Text(text = tipo) }
                )
            }
        }

        GoogleMap(
            modifier = Modifier.weight(1f),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapType = when (selectedMapType) {
                    0 -> MapType.NORMAL
                    1 -> MapType.SATELLITE
                    2 -> MapType.HYBRID
                    3 -> MapType.TERRAIN
                    else -> MapType.NONE
                }
            )
        ) {

            Marker(
                state = rememberMarkerState(position = ArequipaLocation),
                title = "Arequipa, Perú"
            )

            if (showBsAiresMarker) {
                val bsAiresLocation = LatLng(-34.603722, -58.381592)
                Marker(
                    state = rememberMarkerState(position = bsAiresLocation),
                    title = "Buenos Aires, Argentina"
                )
            }
        }

        Button(
            onClick = { showBsAiresMarker = !showBsAiresMarker },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = if (showBsAiresMarker) "Eliminar Marcador en Buenos Aires" else "Agregar Marcador en Buenos Aires")
        }
    }
}
