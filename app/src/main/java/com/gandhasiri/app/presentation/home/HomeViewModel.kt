package com.gandhasiri.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gandhasiri.app.domain.model.Tree
import com.gandhasiri.app.domain.repository.AuthRepository
import com.gandhasiri.app.domain.repository.TreeRepository
import com.gandhasiri.app.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = false,
    val trees: List<Tree> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val treeRepository: TreeRepository,
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        loadTrees()
    }

    private fun loadTrees() {
        val farmerId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            treeRepository.getTrees(farmerId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            trees = resource.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    fun deleteTree(treeId: String) {
        viewModelScope.launch {
            treeRepository.deleteTree(treeId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    fun sendPanicAlert() {
        val farmerId = authRepository.currentUser?.uid ?: "unknown"
        val alertData = hashMapOf(
            "farmerId" to farmerId,
            "timestamp" to System.currentTimeMillis(),
            "status" to "active"
        )
        firestore.collection("panic_alerts").add(alertData)
    }
}
