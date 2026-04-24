package com.continuum.di

import com.continuum.agent.ClinicalSieveAgent
import com.continuum.agent.SOSContextAgent
import com.continuum.agent.TrendAnalysisAgent
import com.continuum.data.remote.ClaudeService
import com.continuum.data.remote.SupabaseService
import com.continuum.data.repository.ClinicalRepositoryImpl
import com.continuum.data.repository.SOSRepositoryImpl
import com.continuum.data.repository.UserRepositoryImpl
import com.continuum.domain.repository.ClinicalRepository
import com.continuum.domain.repository.SOSRepository
import com.continuum.domain.repository.UserRepository
import com.continuum.domain.usecase.GetHealthTrendsUseCase
import com.continuum.domain.usecase.SubmitVoiceEntryUseCase
import com.continuum.domain.usecase.TriggerSOSUseCase
import com.continuum.platform.AudioRecorder
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {

    // ── HTTP client ────────────────────────────────────────────────────────
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true; isLenient = true })
            }
            install(Logging) { level = LogLevel.HEADERS }
        }
    }

    // ── Remote services ────────────────────────────────────────────────────
    single { SupabaseService() }
    single { ClaudeService(get()) }

    // ── AI agents ──────────────────────────────────────────────────────────
    single { ClinicalSieveAgent(get()) }
    single { TrendAnalysisAgent(get()) }
    single { SOSContextAgent(get()) }

    // ── Repositories ───────────────────────────────────────────────────────
    single<ClinicalRepository> { ClinicalRepositoryImpl(get()) }
    single<SOSRepository>      { SOSRepositoryImpl(get()) }
    single<UserRepository>     { UserRepositoryImpl(get()) }

    // ── Platform ───────────────────────────────────────────────────────────
    factory { AudioRecorder() }

    // ── Use cases ──────────────────────────────────────────────────────────────
    factory { SubmitVoiceEntryUseCase(get(), get()) }
    factory { TriggerSOSUseCase(get(), get(), get(), get()) }
    factory { GetHealthTrendsUseCase(get(), get()) }

    // ── ViewModels — pass userId at call site via Koin parameters ───────────────
    factory { (userId: String) ->
        com.continuum.presentation.home.HomeViewModel(get(), get(), userId)
    }
    factory { (userId: String) ->
        com.continuum.presentation.voiceentry.VoiceEntryViewModel(get(), get(), userId)
    }
    factory { (userId: String) ->
        com.continuum.presentation.dashboard.DashboardViewModel(get(), userId)
    }
    factory { (userId: String) ->
        com.continuum.presentation.sos.SOSViewModel(get(), userId)
    }
}
