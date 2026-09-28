package com.example.aisdk.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aisdk.data.model.AppointmentDetails
import com.example.aisdk.data.remote.GeminiClient
import com.example.aisdk.ui.components.CalendarAppointmentCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Lesson4AppointmentExtractionScreen(
    onNavigateBack: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val coroutineScope = rememberCoroutineScope()
    var inputAppointmentText by remember {
        mutableStateOf("Coffee with John next Tuesday at 2pm at Starbucks on Market St, discuss Q4 roadmap")
    }
    var extractedAppointment by remember { mutableStateOf<AppointmentDetails?>(null) }
    var isExtracting by remember { mutableStateOf(false) }

    val presets = listOf(
        "Coffee with John next Tuesday at 2pm at Starbucks on Market St, discuss Q4 roadmap",
        "Design review with Emma & Liam on Friday from 10am to 11am in Conference Room B",
        "Budget approval sync with Sophia tomorrow at 3pm on Google Meet"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lesson 4: Extraction", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Natural Language to Calendar Event",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Extract structured appointment data (event title, date, start & end time, location, and attendee list) from conversational sentences.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Extract Appointment",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = inputAppointmentText,
                        onValueChange = { inputAppointmentText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("appointment_input_field"),
                        placeholder = { Text("Enter appointment details...") },
                        minLines = 2,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Presets
                    Text(
                        text = "Try sample inputs:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        presets.forEachIndexed { idx, preset ->
                            SuggestionChip(
                                onClick = { inputAppointmentText = preset },
                                label = { Text("Sample #${idx + 1}") }
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (inputAppointmentText.isNotBlank()) {
                                coroutineScope.launch {
                                    isExtracting = true
                                    extractedAppointment = GeminiClient.extractAppointment(inputAppointmentText)
                                    isExtracting = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("extract_appointment_button"),
                        enabled = inputAppointmentText.isNotBlank() && !isExtracting,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isExtracting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Extracting Appointment...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Extract Appointment")
                        }
                    }
                }
            }

            // Calendar Appointment Output Card
            CalendarAppointmentCard(
                appointment = extractedAppointment,
                modifier = Modifier.testTag("appointment_details_card")
            )
        }
    }
}
