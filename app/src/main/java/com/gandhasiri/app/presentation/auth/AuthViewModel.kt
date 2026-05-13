package com.gandhasiri.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gandhasiri.app.domain.repository.AuthRepository
import com.gandhasiri.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    class Success(val userUid: String) : AuthState()
    class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    init {
        authRepository.currentUser?.let {
            _authState.value = AuthState.Success(it.uid)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _authState.value = AuthState.Loading
                    is Resource.Success -> _authState.value = AuthState.Success(resource.data?.uid.orEmpty())
                    is Resource.Error -> _authState.value = AuthState.Error(resource.message ?: "Unknown Error")
                }
            }
        }
    }

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            authRepository.register(email, password, name).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _authState.value = AuthState.Loading
                    is Resource.Success -> _authState.value = AuthState.Success(resource.data?.uid.orEmpty())
                    is Resource.Error -> _authState.value = AuthState.Error(resource.message ?: "Unknown Error")
                }
            }
        }
    }
}
