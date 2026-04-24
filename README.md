# PMC-MVP-Version-Kotlin

## Descripción
Este repositorio contiene una aplicación Android desarrollada en Kotlin bajo un enfoque MVP (Model–View–Presenter). El objetivo del proyecto es servir como base de referencia para una app modular, testeable y fácil de mantener, separando responsabilidades entre la capa de vista, presentación y acceso a datos.

## Tecnologías
- Kotlin
- Android SDK
- Gradle
- (Completar: Retrofit/OkHttp, Room, Coroutines, etc. según lo que use el proyecto)

## Requisitos
- Android Studio (recomendado: versión estable reciente)
- JDK (indicar versión exacta cuando la confirmemos)
- SDK de Android instalado (indicar compileSdk/targetSdk cuando lo confirmemos)
- Gradle (incluido vía Gradle Wrapper)

## Cómo correr el proyecto

### 1) Clonar el repositorio
```bash
git clone https://github.com/PMC-Continuum/PMC-MVP-Version-Kotlin.git
cd PMC-MVP-Version-Kotlin
```

### 2) Abrir en Android Studio
1. Abrir Android Studio
2. Seleccionar **Open** y elegir la carpeta del proyecto
3. Esperar a que finalice la sincronización de Gradle

### 3) Configuración (si aplica)
Dependiendo del entorno, puede ser necesario configurar:
- Variables en `local.properties`
- Archivos `google-services.json` (si hay Firebase)
- `BASE_URL` / endpoints (si consume APIs)
- Llaves o credenciales (no se deben commitear)

> Nota: Cuando me compartas los `build.gradle` y configuración, documentaré exactamente qué se necesita y dónde.

### 4) Ejecutar
- Conectar un dispositivo físico con Depuración USB habilitada, o iniciar un emulador
- Elegir el target y ejecutar con **Run**

## Build desde terminal (opcional)
```bash
./gradlew clean assembleDebug
```

Para correr tests:
```bash
./gradlew test
```

## Estructura del proyecto
(Se completa con la estructura real del repo; ejemplo orientativo)

- `app/`
  - `src/main/java/...`
    - `view/` (Activities/Fragments)
    - `presenter/`
    - `model/` o `data/`
    - `network/` (si aplica)
  - `src/main/res/`
- `gradle/`
- `build.gradle`
- `settings.gradle`

## Arquitectura (MVP)
- **View**: muestra información y delega eventos del usuario.
- **Presenter**: contiene la lógica de presentación, coordina casos de uso y actualiza la View.
- **Model/Data**: repositorios, fuentes de datos, DTOs, persistencia, etc.

## Convenciones y buenas prácticas
- Mantener presenters sin referencias a Android cuando sea posible.
- Evitar lógica de negocio en Activities/Fragments.
- Usar inyección de dependencias si el proyecto la incluye (por confirmar).
- Separar modelos de red de modelos de dominio (si aplica).

## Solución de problemas
- Si falla la sincronización de Gradle, revisar versión de JDK configurada en Android Studio.
- Si hay errores de SDK, confirmar que el `compileSdk` esté instalado.
- Si el proyecto depende de un backend, validar conectividad y configuración de `BASE_URL`.

## Licencia
(Indicar licencia si existe; si no, se puede omitir o agregar una más adelante.)
