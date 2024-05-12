package com.trivigenai.di

import com.trivigenai.repository.FakeGenAIRepositoryImpl
import com.trivigenai.repository.GenAIRepository
import com.trivigenai.repository.GenAIRepositoryImpl
import com.trivigenai.service.GenAIService
import org.koin.dsl.module

val appModule = module {
    if (System.getenv("PROJECT_ID").isNullOrEmpty()) {
        single<GenAIRepository> { FakeGenAIRepositoryImpl() }
    } else {
        single<GenAIRepository> { GenAIRepositoryImpl() }
    }
    single { GenAIService(get()) }
}