# 🌤️ WeatherFlow - Dinamik Hava Durumu Uygulaması
**Not:** Bu proje, API entegrasyonu, modern Android mimarileri ve kullanıcı deneyimi (UX) odaklı geliştirme temellerini pekiştirmek amacıyla hazırlanmıştır.

WeatherFlow, modern Android teknolojileri (Jetpack Compose, Retrofit) kullanılarak geliştirilmiş, gerçek zamanlı hava durumu verilerini estetik ve dinamik bir arayüzle sunan bir portfolyo projesidir.

## Öne Çıkan Özellikler
- **Dinamik Arka Plan:** Hava durumuna göre (Güneşli, Yağmurlu, Bulutlu) otomatik değişen renk geçişleri (Gradient Background).
- **Gerçek Zamanlı Veri:** OpenWeather API entegrasyonu ile Türkiye'nin büyük şehirlerine ait anlık sıcaklık ve durum bilgileri.
- **Modern UI/UX:** Material Design 3 standartlarına uygun, kullanıcı dostu kart tasarımları.
- **Asenkron Yapı:** Coroutines ile ana thread'i yormadan, performanslı veri çekme süreçleri.

## Kullanılan Teknolojiler & Kütüphaneler
- **Dil:** Kotlin
- **Arayüz:** Jetpack Compose
- **Ağ (Networking):** Retrofit2 & OkHttp
- **Veri Dönüştürme:** Gson (JSON Serialization)
- **Görsel Yükleme:** Coil (Asenkron ikon yönetimi)

## 📂 Kurulum
1. Repoyu clone'layın: `git clone https://github.com/KULLANICI_ADIN/WeatherFlow.git`
2. `MainActivity.kt` içerisindeki `myApiKey` değişkenine [OpenWeather](https://openweathermap.org/) üzerinden aldığınız API anahtarını ekleyin.
3. Projeyi Android Studio ile derleyip çalıştırın.

---
