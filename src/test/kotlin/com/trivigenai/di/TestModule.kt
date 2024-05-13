package com.trivigenai.di

import com.trivigenai.repository.FakeGenAIRepositoryImpl
import com.trivigenai.repository.GenAIRepository
import com.trivigenai.service.GenAIService
import org.koin.dsl.module

val testModule = module {
    single<GenAIRepository> { FakeGenAIRepositoryImpl() }
    single { GenAIService(get()) }
}