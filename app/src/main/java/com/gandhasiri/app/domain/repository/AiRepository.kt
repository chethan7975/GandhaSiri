package com.gandhasiri.app.domain.repository

import com.gandhasiri.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AiRepository {
    suspend fun getFarmingAdvice(prompt: String): Flow<Resource<String>>
    suspend fun analyzeTreeHealth(healthData: String): Flow<Resource<String>>
}
