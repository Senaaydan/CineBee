# 🎬 CineBee
CineBee, film keşfetme, arama ve favorilere ekleme özellikleri sunan, Kotlin ve Jetpack Compose kullanılarak geliştirilmiş bir Android uygulamasıdır.
Uygulamada film verileri TMDB API üzerinden alınmaktadır. Projeyi geliştirirken ekran yönetimi, yerel veri saklama, API entegrasyonu, bağımlılık yönetimi ve test süreçleri üzerinde çalıştım.
## 📱 Özellikler
- Film listeleme, arama ve detay görüntüleme
- Oyuncu bilgilerini görüntüleme
- Filmleri favorilere ekleme ve favoriler içinde arama
- Son 10 aramayı kaydetme ve arama geçmişini yönetme
- Açık, koyu ve sistem temasını kullanma
- Dil tercihini kaydetme
- Loading, error ve empty state yönetimi
- Bottom Navigation ile ekranlar arasında geçiş

## 🛠 Kullanılan Teknolojiler
- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Retrofit
- OkHttp
- Moshi
- Room Database
- DataStore Preferences
- Hilt
- Kotlin Coroutines
- Flow / StateFlow / SharedFlow
- Coil
- JUnit
- kotlinx-coroutines-test

## 🏗 Mimari Yapı
Projede ekran, veri ve iş mantığı sorumluluklarını birbirinden ayırmaya çalıştım.
Genel veri akışı şu şekildedir:
UI → Intent → ViewModel → Repository → Data Source → State → UI
Ekranların state yönetiminde MVI yaklaşımından yararlandım. Kullanıcı tarafından gerçekleştirilen işlemler Intent olarak ViewModel'e iletilmektedir. ViewModel gerekli işlemleri gerçekleştirdikten sonra ekran state'ini güncellemektedir.
Navigation gibi tek seferlik işlemlerde ise `SharedFlow` kullandım.

Projede Repository Pattern kullanarak ViewModel'lerin Retrofit veya Room gibi veri kaynaklarına doğrudan erişmesini engelledim.

Proje içerisinde temel olarak şu yapılar bulunmaktadır:

- `presentation`: Ekranlar, ViewModel'ler, State ve Intent sınıfları
- `data`: API, Room, DataStore, DTO, Entity ve Repository implementasyonları
- `domain`: Uygulamada kullanılan temel modeller
- `di`: Hilt dependency injection modülleri
- `core/navigation`: Navigation ile ilgili ortak yapılar
- `ui/components`: Tekrar kullanılabilir Compose bileşenleri

## 🌐 API
Film verilerinin alınması için **TMDB API** kullanılmaktadır.
Retrofit üzerinden gerçekleştirilen API isteklerine Authorization bilgisi `AuthInterceptor` aracılığıyla eklenmektedir.
API token doğrudan kaynak kod içerisine yazılmamıştır. Token `local.properties` dosyası üzerinden alınmaktadır.

## 💾 Yerel Veri Saklama
### 1) Room
Room Database aşağıdaki veriler için kullanılmaktadır:
- Favori filmler
- Son 10 arama
  Favori listesi ve arama geçmişi `Flow` ile takip edildiği için veriler değiştiğinde ilgili ekranlar otomatik olarak güncellenmektedir.

### 2) DataStore
DataStore Preferences ile kullanıcı tercihleri saklanmaktadır.
- Tema tercihi
- Dil tercihi
  Bu tercihler uygulama yeniden açıldığında korunmaktadır.

## 🧪 Unit Testler
Projede özellikle aşağıdaki ViewModel sınıfları için unit testler yazılmıştır:
- `MovieSearchViewModel`
- `FavoritesViewModel`

ViewModel'leri API ve veritabanından bağımsız test edebilmek için `FakeMovieRepository` kullanılmıştır.

Testlerde:
- JUnit
- `runTest`
- Test Dispatcher
- `MainDispatcherRule`
- StateFlow assertion
- SharedFlow navigation event testi
  kullanılmıştır.

Test edilen bazı senaryolar:
- Arama sorgusu değiştiğinde state'in güncellenmesi
- Arama sonucunun state'e aktarılması
- Aramanın geçmişe kaydedilmesi
- Arama geçmişinden kayıt silinmesi
- Favorilerin repository'den alınarak state'e aktarılması
- Favori filmin silinmesi
- Favoriler içerisinde arama yapılması
- Film tıklamasında doğru navigation event'inin yayınlanması

## ⚙️ Kurulum
Projeyi bilgisayarınızda çalıştırmak için öncelikle repository'yi klonlayın:


```bash
git clone https://github.com/Senaaydan/CineBee.git
```
Projeyi Android Studio ile açın.

TMDB API kullanabilmek için proje kök dizinindeki local.properties dosyasına kendi TMDB token bilginizi ekleyin:
```properties
TMDB_TOKEN=YOUR_TMDB_TOKEN
```
local.properties dosyası .gitignore içerisinde bulunduğu için API token GitHub repository'sine gönderilmez.

Gradle senkronizasyonu tamamlandıktan sonra uygulama Android cihaz veya emulator üzerinde çalıştırılabilir.
