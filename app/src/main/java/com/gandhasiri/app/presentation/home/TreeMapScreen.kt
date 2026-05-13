package com.gandhasiri.app.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gandhasiri.app.domain.model.Tree
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreeMapScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    // Default to India if no trees
    val defaultLocation = LatLng(20.5937, 78.9629)
    val firstTreeLocation = state.trees.firstOrNull()?.let { LatLng(it.latitude, it.longitude) } ?: defaultLocation

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(firstTreeLocation, if (state.trees.isEmpty()) 4f else 10f)
    }

    LaunchedEffect(state.trees) {
        if (state.trees.isNotEmpty()) {
            val location = LatLng(state.trees.first().latitude, state.trees.first().longitude)
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(location, 10f)
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tree Map") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                state.trees.forEach { tree ->
                    val position = LatLng(tree.latitude, tree.longitude)
                    Marker(
                        state = MarkerState(position = position),
                        title = tree.name.ifEmpty { "Sandalwood Tree" },
                        snippet = "Girth: ${tree.girth}cm | Age: ${tree.age} | Health: ${tree.healthScore}"
                    )
                }
            }
        }
    }
}
