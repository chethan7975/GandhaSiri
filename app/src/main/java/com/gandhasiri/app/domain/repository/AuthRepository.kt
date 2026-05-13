package com.gandhasiri.app.domain.repository

import com.gandhasiri.app.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun register(email: String, password: String, name: String): Flow<Resource<FirebaseUser>>
    fun logout()
}
