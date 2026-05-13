package com.gandhasiri.app.di

import com.gandhasiri.app.data.repository.AiRepositoryImpl
import com.gandhasiri.app.data.repository.AuthRepositoryImpl
import com.gandhasiri.app.data.repository.TreeRepositoryImpl
import com.gandhasiri.app.domain.repository.AiRepository
import com.gandhasiri.app.domain.repository.AuthRepository
import com.gandhasiri.app.domain.repository.TreeRepository
import com.gandhasiri.app.data.repository.SettingsRepositoryImpl
import com.gandhasiri.app.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTreeRepository(
        treeRepositoryImpl: TreeRepositoryImpl
    ): TreeRepository

    @Binds
    @Singleton
    abstract fun bindAiRepository(
        aiRepositoryImpl: AiRepositoryImpl
    ): AiRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}
