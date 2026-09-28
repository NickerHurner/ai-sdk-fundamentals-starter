package com.example.aisdk.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aisdk.data.model.SupportTicket
import com.example.aisdk.data.remote.GeminiClient
import com.example.aisdk.data.repository.SampleData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Lesson2ClassificationScreen(
    onNavigateBack: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val coroutineScope = rememberCoroutineScope()
    val tickets = remember { mutableStateListOf<SupportTicket>().apply { addAll(SampleData.INITIAL_SUPPORT_REQUESTS) } }
    var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }
    var customTicketInput by remember { mutableStateOf("") }
    var isClassifying by remember { mutableStateOf(false) }

    val categories = listOf(
        "all",
        "billing",
        "product_issues",
        "enterprise_sales",
        "account_issues",
        "product_feedback"
    )

    val displayedTickets = if (selectedCategoryFilter == null || selectedCategoryFilter == "all") {
        tickets
    } else {
        tickets.filter { it.category == selectedCategoryFilter }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lesson 2: Classification", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Support Request Categorization",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Classify incoming customer support tickets into defined Zod/JSON schema categories with priority triage and suggested resolution text.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Test custom input card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Classify Custom Ticket",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = customTicketInput,
                        onValueChange = { customTicketInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ticket_input_field"),
                        placeholder = { Text("e.g., We need SAML SSO configuration for our 500 employee team.") },
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (customTicketInput.isNotBlank()) {
                                coroutineScope.launch {
                                    isClassifying = true
                                    val newTicket = GeminiClient.classifySupportTicket(customTicketInput)
                                    tickets.add(0, newTicket)
                                    customTicketInput = ""
                                    isClassifying = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_ticket_button"),
                        enabled = customTicketInput.isNotBlank() && !isClassifying,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isClassifying) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Classifying Ticket...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Classify with AI")
                        }
                    }
                }
            }

            // Category filter chips
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Filter by Category",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = (selectedCategoryFilter == cat) || (selectedCategoryFilter == null && cat == "all")
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedCategoryFilter = if (cat == "all") null else cat
                            },
                            label = {
                                Text(
                                    text = formatCategoryName(cat),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            }

            // Ticket list
            Text(
                text = "Tickets (${displayedTickets.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            displayedTickets.forEach { ticket ->
                TicketItemCard(ticket = ticket)
            }
        }
    }
}

@Composable
fun TicketItemCard(ticket: SupportTicket) {
    val categoryColor = when (ticket.category) {
        "billing" -> Color(0xFFEF4444)
        "product_issues" -> Color(0xFFF59E0B)
        "enterprise_sales" -> Color(0xFF8B5CF6)
        "account_issues" -> Color(0xFF3B82F6)
        "product_feedback" -> Color(0xFF10B981)
        else -> MaterialTheme.colorScheme.primary
    }

    val priorityColor = when (ticket.priority.lowercase()) {
        "urgent" -> Color(0xFFDC2626)
        "high" -> Color(0xFFEA580C)
        "medium" -> Color(0xFFD97706)
        else -> Color(0xFF16A34A)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Tag
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = categoryColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = formatCategoryName(ticket.category),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = categoryColor
                    )
                }

                // Priority Tag
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = priorityColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = ticket.priority.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = priorityColor
                    )
                }
            }

            Text(
                text = ticket.text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (ticket.suggestedReply.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "AI Suggested Reply:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = ticket.suggestedReply,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

private fun formatCategoryName(cat: String): String {
    return when (cat) {
        "all" -> "All Categories"
        "billing" -> "Billing"
        "product_issues" -> "Product Issues"
        "enterprise_sales" -> "Enterprise Sales"
        "account_issues" -> "Account Issues"
        "product_feedback" -> "Product Feedback"
        else -> cat.replace("_", " ").replaceFirstChar { it.uppercase() }
    }
}
