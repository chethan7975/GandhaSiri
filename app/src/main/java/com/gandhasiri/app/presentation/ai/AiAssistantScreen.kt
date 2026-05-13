package com.gandhasiri.app.presentation.ai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gandhasiri.app.presentation.components.CustomTextField
import com.gandhasiri.app.presentation.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistantScreen(
    navController: NavController,
    viewModel: AiAssistantViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var prompt by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Farming Assistant") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Text("Welcome to Sandalwood AI. Ask me about disease prevention, legal rules, and growth suggestions.")
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (state.isLoading) {
                    item { CircularProgressIndicator() }
                } else if (state.response != null) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text(
                                text = state.response!!,
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
                if (state.error != null) {
                    item {
                        Text(text = "Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            CustomTextField(
                value = prompt,
                onValueChange = { prompt = it },
                label = "Ask your question..."
            )
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(
                text = "Ask AI",
                onClick = { viewModel.askQuestion(prompt) },
                enabled = prompt.isNotBlank() && !state.isLoading
            )
        }
    }
}
