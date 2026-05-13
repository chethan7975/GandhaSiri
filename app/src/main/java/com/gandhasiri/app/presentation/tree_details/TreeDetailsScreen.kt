package com.gandhasiri.app.presentation.tree_details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.EnergySavingsLeaf
import androidx.compose.material.icons.rounded.MonitorHeart
import androidx.compose.material.icons.rounded.Scale
import androidx.compose.material.icons.rounded.Straighten
import androidx.compose.material.icons.rounded.Forest
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gandhasiri.app.domain.model.Tree
import com.gandhasiri.app.presentation.home.HomeViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreeDetailsScreen(
    navController: NavController,
    treeId: String,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val tree = state.trees.find { it.id == treeId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tree?.name?.ifEmpty { "Tree Details" } ?: "Tree Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (tree == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Tree not found.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Info Cards
                Text("Properties", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PropertyCard("Age", "${tree.age} yrs", Icons.Rounded.Forest, Modifier.weight(1f))
                    PropertyCard("Girth", "${tree.girth} cm", Icons.Rounded.Scale, Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PropertyCard("Health", "${tree.healthScore}/100", Icons.Rounded.MonitorHeart, Modifier.weight(1f))
                    PropertyCard("Height", "${tree.height} m", Icons.Rounded.Straighten, Modifier.weight(1f))
                }

                // Heartwood Estimate
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.EnergySavingsLeaf, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Estimated Heartwood Yield", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        val estimate = String.format("%.2f", tree.heartwoodEstimateKg)
                        Text(
                            text = if (tree.age < 10) "Too young for significant yield (needs 10+ years)." else "$estimate kg",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Calculation based on girth and age.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                }

                // Growth Tracker Chart
                Text("Growth Tracker (Health)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                GrowthTrackerChart(tree = tree)
            }
        }
    }
}

@Composable
fun PropertyCard(title: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(title, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun GrowthTrackerChart(tree: Tree) {
    // Simulate historical health data over the years
    val history = remember(tree) {
        val points = mutableListOf<Int>()
        var currentHealth = 100
        for (i in 0..tree.age) {
            points.add(currentHealth)
            // random health drop or gain, tending downwards slightly, ending at current health
            if (i == tree.age) {
                points[points.size - 1] = tree.healthScore
            } else {
                currentHealth = (currentHealth - Random.nextInt(0, 5)).coerceIn(50, 100)
            }
        }
        points
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Simulated Health Progression", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stepX = size.width / (history.size - 1).coerceAtLeast(1)
                // Health is 0-100, we map 50-100 to canvas height
                val minHealth = 50f
                val maxHealth = 100f
                val heightRatio = size.height / (maxHealth - minHealth)

                val path = Path()
                
                if (history.size > 1) {
                    history.forEachIndexed { index, health ->
                        val x = index * stepX
                        val y = size.height - ((health - minHealth) * heightRatio).coerceIn(0f, size.height)
                        
                        if (index == 0) {
                            path.moveTo(x, y)
                        } else {
                            path.lineTo(x, y)
                        }
                        
                        drawCircle(
                            color = primaryColor,
                            radius = 4.dp.toPx(),
                            center = Offset(x, y)
                        )
                    }
                    
                    drawPath(
                        path = path,
                        color = primaryColor,
                        style = Stroke(width = 3.dp.toPx())
                    )
                } else {
                    val y = size.height - ((history[0] - minHealth) * heightRatio).coerceIn(0f, size.height)
                    drawCircle(
                        color = primaryColor,
                        radius = 4.dp.toPx(),
                        center = Offset(size.width / 2, y)
                    )
                }
            }
        }
    }
}
