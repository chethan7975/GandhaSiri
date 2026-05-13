package com.gandhasiri.app.data.repository

import com.gandhasiri.app.domain.repository.AiRepository
import com.gandhasiri.app.utils.Resource
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : AiRepository {

    override suspend fun getFarmingAdvice(prompt: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = generativeModel.generateContent(prompt)
            emit(Resource.Success(response.text ?: "No response from AI"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to get AI response"))
        }
    }

    override suspend fun analyzeTreeHealth(healthData: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val prompt = "Analyze this sandalwood tree data and give health suggestions: $healthData"
            val response = generativeModel.generateContent(prompt)
            emit(Resource.Success(response.text ?: "No response from AI"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to analyze health"))
        }
    }
}
