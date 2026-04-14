package com.zafer.havadurumu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

// Tasarım İçin Renk Paleti (Hava Durumuna Göre)
val SunnyStart = Color(0xFFFFD54F) // Sarı
val SunnyEnd = Color(0xFFFF8A65)   // Turuncu
val CloudyStart = Color(0xFF90A4AE) // Gri-Mavi
val CloudyEnd = Color(0xFF78909C)   // Koyu Gri
val RainyStart = Color(0xFF4FC3F7)  // Açık Mavi
val RainyEnd = Color(0xFF0277BD)    // Koyu Mavi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1976D2),
            background = Color(0xFFF5F5F5)
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen() {
    val cities = listOf("Istanbul", "Ankara", "Izmir", "Eskisehir", "Aydin", "Gaziantep", "Antalya", "Bursa", "Adana", "Konya")
    val weatherList = remember { mutableStateListOf<WeatherModel>() }
    val myApiKey = "d8253055974e9a3d4a4f1a71f5585de7"

    LaunchedEffect(Unit) {
        cities.forEach { city ->
            try {
                val result = RetrofitClient.api.getCurrentWeather(city, myApiKey)
                weatherList.add(result)
            } catch (e: Exception) {
                android.util.Log.e("HAVA_DURUMU", "Hata oluştu dayı: ${e.message}")
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Türkiye Hava Durumu", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (weatherList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFFEEEEEE)),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Kartlar arası boşluk
            ) {
                items(weatherList) { weather ->
                    WeatherCard(weather)
                }
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherModel) {

    val mainCondition = weather.weather[0].main.lowercase()
    val backgroundBrush = when {
        mainCondition.contains("cloud") -> Brush.linearGradient(listOf(CloudyStart, CloudyEnd))
        mainCondition.contains("rain") || mainCondition.contains("drizzle") -> Brush.linearGradient(listOf(RainyStart, RainyEnd))
        else -> Brush.linearGradient(listOf(SunnyStart, SunnyEnd))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(backgroundBrush)
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // İkon
                    val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = weather.weather[0].description.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Sıcaklık (Daha büyük ve kalın)
            Text(
                text = "${weather.main.temp.toInt()}°C",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                fontSize = 48.sp // Özel büyük boyut
            )
        }
    }
}