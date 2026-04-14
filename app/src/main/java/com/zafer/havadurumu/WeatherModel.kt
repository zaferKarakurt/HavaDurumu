package com.zafer.havadurumu

// API'den gelen ana gövde
data class WeatherModel(
    val main: MainModel,
    val weather: List<WeatherDescription>,
    val name: String
)

// Sıcaklık detayları
data class MainModel(
    val temp: Double
)

// Hava durumu açıklaması
data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)