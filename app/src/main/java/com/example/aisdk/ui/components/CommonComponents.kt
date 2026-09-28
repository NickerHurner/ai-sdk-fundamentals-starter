package com.example.aisdk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aisdk.data.model.AppointmentDetails
import com.example.aisdk.data.model.SummaryCardData
import com.example.aisdk.data.model.WeatherData

@Composable
fun SummaryCard(
    summary: SummaryCardData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = summary.headline,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                BulletPointItem(
                    label = "Context",
                    text = summary.context
                )
                BulletPointItem(
                    label = "Discussion",
                    text = summary.discussionPoints
                )
                BulletPointItem(
                    label = "Takeaways",
                    text = summary.takeaways
                )
            }
        }
    }
}

@Composable
private fun BulletPointItem(label: String, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "•",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarAppointmentCard(
    appointment: AppointmentDetails?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Appointment Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Appointment Name",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = appointment?.title ?: "No appointment set",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Date & Time
                AppointmentDetailRow(
                    icon = Icons.Default.AccessTime,
                    label = "Date & Time",
                    value = appointment?.let {
                        if (it.startTime != null && it.endTime != null) {
                            "${it.date}, ${it.startTime} - ${it.endTime}"
                        } else if (it.startTime != null) {
                            "${it.date} at ${it.startTime}"
                        } else {
                            it.date
                        }
                    },
                    placeholder = "No date or time set"
                )

                // Location
                AppointmentDetailRow(
                    icon = Icons.Default.LocationOn,
                    label = "Location",
                    value = appointment?.location,
                    placeholder = "No location set"
                )

                // Attendees
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "Attendees",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Attendees",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (!appointment?.attendees.isNullOrEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            appointment?.attendees?.forEach { attendee ->
                                AttendeeChip(name = attendee)
                            }
                        }
                    } else {
                        Text(
                            text = "No attendees set",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppointmentDetailRow(
    icon: ImageVector,
    label: String,
    value: String?,
    placeholder: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(20.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value ?: placeholder,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (value != null) FontWeight.Medium else FontWeight.Normal,
                color = if (value != null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun AttendeeChip(name: String) {
    val initials = name.split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .uppercase()

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${name.lowercase().replace(" ", "_")}@company.com",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun WeatherCard(
    weather: WeatherData,
    modifier: Modifier = Modifier
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3B82F6), // Blue 500
            Color(0xFF1D4ED8)  // Blue 700
        )
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(gradientBrush)
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = weather.city,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${weather.temperature}°C",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                        Text(
                            text = weather.condition ?: getWeatherConditionName(weather.weatherCode),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    WeatherIconWidget(weatherCode = weather.weatherCode)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = "Humidity",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Humidity: ${weather.humidity}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherIconWidget(weatherCode: Int) {
    when {
        weatherCode == 0 -> {
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Clear sky",
                tint = Color(0xFFFDE047),
                modifier = Modifier.size(56.dp)
            )
        }
        weatherCode <= 3 -> {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = Color(0xFFFDE047),
                    modifier = Modifier.size(48.dp)
                )
                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = "Partly cloudy",
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier
                        .size(38.dp)
                        .padding(start = 12.dp, top = 8.dp)
                )
            }
        }
        weatherCode <= 49 -> {
            Icon(
                imageVector = Icons.Default.Cloud,
                contentDescription = "Cloudy",
                tint = Color(0xFFE2E8F0),
                modifier = Modifier.size(56.dp)
            )
        }
        weatherCode <= 84 -> {
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = "Rainy",
                tint = Color(0xFF93C5FD),
                modifier = Modifier.size(56.dp)
            )
        }
        else -> {
            Icon(
                imageVector = Icons.Default.Thunderstorm,
                contentDescription = "Thunderstorm",
                tint = Color(0xFFFBBF24),
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

private fun getWeatherConditionName(code: Int): String {
    return when {
        code == 0 -> "Clear sky"
        code <= 3 -> "Partly cloudy"
        code <= 49 -> "Cloudy"
        code <= 69 -> "Rainy"
        code <= 79 -> "Snowy"
        code <= 84 -> "Rain showers"
        else -> "Thunderstorm"
    }
}
