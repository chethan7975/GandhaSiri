package com.gandhasiri.app.presentation.tree_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gandhasiri.app.domain.model.Tree
import com.gandhasiri.app.domain.repository.AuthRepository
import com.gandhasiri.app.domain.repository.TreeRepository
import com.gandhasiri.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class TreeRegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class TreeRegisterViewModel @Inject constructor(
    private val treeRepository: TreeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TreeRegisterState())
    val state: StateFlow<TreeRegisterState> = _state

    fun registerTree(
        name: String, 
        girth: Double, 
        height: Double, 
        age: Int,
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        photoUrl: String = ""
    ) {
        val farmerId = authRepository.currentUser?.uid
        if (farmerId == null) {
            _state.value = _state.value.copy(error = "User not logged in")
            return
        }

        val tree = Tree(
            id = UUID.randomUUID().toString(),
            farmerId = farmerId,
            name = name,
            girth = girth,
            height = height,
            age = age,
            latitude = latitude,
            longitude = longitude,
            photoUrl = photoUrl,
            healthScore = 100 // default
        )

        viewModelScope.launch {
            treeRepository.registerTree(tree).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true, error = null)
                    is Resource.Success -> _state.value = _state.value.copy(isLoading = false, isSuccess = true)
                    is Resource.Error -> _state.value = _state.value.copy(isLoading = false, error = resource.message)
                }
            }
        }
    }
}
