package com.gandhasiri.app.presentation.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gandhasiri.app.domain.repository.AiRepository
import com.gandhasiri.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiAssistantState(
    val isLoading: Boolean = false,
    val response: String? = null,
    val error: String? = null
)

@HiltViewModel
class AiAssistantViewModel @Inject constructor(
    private val aiRepository: AiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AiAssistantState())
    val state: StateFlow<AiAssistantState> = _state

    fun askQuestion(prompt: String) {
        viewModelScope.launch {
            aiRepository.getFarmingAdvice(prompt).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true, error = null)
                    is Resource.Success -> _state.value = _state.value.copy(isLoading = false, response = resource.data)
                    is Resource.Error -> _state.value = _state.value.copy(isLoading = false, error = resource.message)
                }
            }
        }
    }
}
