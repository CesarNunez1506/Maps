package com.example.lab12

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen() {
    val ArequipaLocation = LatLng(-16.4040102, -71.559611) // Arequipa, Perú
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(ArequipaLocation, 12f)
    }

    val context = LocalContext.current

    // Cargar el ícono personalizado desde drawable
    val icon: BitmapDescriptor? = try {
        val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.monta_a)
        if (drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap
            BitmapDescriptorFactory.fromBitmap(bitmap)
        } else {
            Log.e("MapScreen", "El recurso no es un BitmapDrawable.")
            null
        }
    } catch (e: Exception) {
        Log.e("MapScreen", "Error al cargar el ícono desde drawable: ${e.message}")
        null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Añadir marcador en Arequipa con ícono personalizado
            Marker(
                state = rememberMarkerState(position = ArequipaLocation),
                icon = icon ?: BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED), // Usar ícono personalizado o predeterminado
                title = "Arequipa, Perú"
            )

            // Añadir otros marcadores
            val locations = listOf(
                LatLng(-16.433415, -71.5442652), // JLByR
                LatLng(-16.4205151, -71.4945209), // Paucarpata
                LatLng(-16.3524187, -71.5675994) // Zamacola
            )

            locations.forEach { location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    title = "Ubicación",
                    snippet = "Punto de interés"
                )
            }

        }
    }

    // Mover la cámara a Yura después de cargar el mapa
    LaunchedEffect(Unit) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(LatLng(-16.2520984, -71.6836503), 12f),
            durationMs = 3000
        )
    }
}
