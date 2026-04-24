# Continuum (Kotlin Multiplatform)

Continuum es una plataforma de monitoreo clínico para pacientes con enfermedades crónicas en Colombia. El objetivo es facilitar el registro diario de síntomas (con entrada por voz), el análisis de tendencias y un flujo de emergencia (SOS) que genere un resumen clínico útil para atención oportuna.

Este repositorio está migrando hacia una base **Kotlin Multiplatform (KMP)** con UI en **Compose Multiplatform**, compartiendo la lógica de negocio en `shared/` y apuntando a **Android, iOS y Web**.

## Alcance y módulos

El proyecto está organizado bajo una estructura KMP con módulos para cada plataforma y un módulo compartido:

- `androidApp/`: aplicación Android (Compose).
- `iosApp/`: proyecto iOS (Xcode) consumiendo el framework generado por KMP.
- `webApp/`: app Web con Compose Multiplatform (target JS).
- `shared/`: código compartido (dominio, datos, agentes, navegación, UI común).
- `landing/`: landing pública en HTML/CSS/JS (sin build), con formulario de lista de espera.

## Stack técnico (no negociable)

- **UI**: Kotlin Multiplatform + Compose Multiplatform (Android, iOS, Web)
- **Backend**: Supabase (Auth, Postgres, Realtime, Storage)
- **Agentes/IA**: Anthropic Claude (`claude-sonnet-4-5`) consumido desde Ktor
- **Persistencia local**: SQLDelight (enfoque offline-first)
- **Networking**: Ktor Client (en `commonMain`)
- **Navegación**: Decompose (en `commonMain`)
- **DI**: Koin (en `commonMain`)
- **Preferencias**: Multiplatform Settings
- **Build**: Gradle Version Catalog (`gradle/libs.versions.toml`)

## Estructura esperada del código

La base del proyecto se está implementando con el siguiente layout (resumen):

- `shared/src/commonMain/kotlin/com/continuum/`
  - `config/`: configuración de entorno (`AppConfig.kt`)
  - `di/`: módulo de Koin (`AppModule.kt`)
  - `domain/`: modelos, repositorios y casos de uso
  - `data/`: servicios remotos (Supabase, Claude), repositorios, y capa local (SQLDelight)
  - `agent/`: agentes de extracción clínica, tendencias y contexto SOS
  - `presentation/`: navegación (Decompose), ViewModels (StateFlow) y pantallas Compose
  - `platform/`: `expect/actual` (por ahora, grabación/transcripción de audio)

## Backend (Supabase)

El backend usa Supabase con tablas para usuarios, entradas clínicas, eventos SOS, tendencias de salud y una lista de espera pública.

- Migración inicial: `shared/supabase/migrations/001_initial.sql`
- RLS (Row Level Security): aplicado para `clinical_entries`, `sos_events` y `health_trends`, asegurando que cada usuario solo pueda acceder a sus datos.

## Offline-first con SQLDelight

Los registros clínicos se escriben primero en la base local y se sincronizan con Supabase cuando hay conectividad.

- Esquema SQLDelight: `shared/src/commonMain/sqldelight/com/continuum/db/ContinuumDatabase.sq`

## Agentes de IA

En `shared/src/commonMain/kotlin/com/continuum/agent/` se definen tres piezas principales:

- `ClinicalSieveAgent`: toma una transcripción (español) y devuelve síntomas, severidad (1–10), datos estructurados y un resumen clínico.
- `TrendAnalysisAgent`: analiza el histórico reciente y devuelve patrones de empeoramiento/mejoría, anomalías y una recomendación.
- `SOSContextAgent`: genera un resumen clínico conciso para emergencias (en español), incluyendo urgencia y contexto relevante.

Los llamados se hacen a la API de Anthropic desde Ktor (`ClaudeService`).

## Reglas de implementación

- La lógica de negocio vive en `commonMain`. Lo específico de plataforma se limita a `expect/actual` (por ejemplo, `AudioRecorder`).
- Estado de UI con `StateFlow` (no se usa LiveData).
- Todas las llamadas de red son `suspend`.
- Manejo de errores con `Result<T>` (tipos `Success` y `Error`).
- Serialización con `kotlinx.serialization`.
- No se deben commitear llaves de Anthropic ni credenciales.

## Configuración de entorno

Por seguridad, las llaves no deben quedar en el repositorio.

- Ejemplo de variables: `.env.example`
  - `SUPABASE_URL`
  - `SUPABASE_ANON_KEY`
  - `ANTHROPIC_API_KEY`

Asegúrate de tener en `.gitignore`:

- `local.properties`
- `.env`
- `*.keystore`

> Nota: En producción, la idea es leer claves desde `local.properties` (Android), `Info.plist` (iOS) y variables de entorno (Web).

## Cómo correr el proyecto

### Requisitos

- Android Studio (versión estable)
- JDK 17
- SDK de Android instalado
- Gradle Wrapper (incluido)

### Android

```bash
./gradlew :androidApp:assembleDebug
```

### Shared (compilación de commonMain)

```bash
./gradlew :shared:compileKotlinMetadata
```

## Estado actual

El repositorio está consolidando el scaffold KMP y la implementación base (dominio, datos, agentes, navegación y pantallas). La referencia de versiones se maneja desde `gradle/libs.versions.toml`.

## Licencia

Pendiente de definir.
