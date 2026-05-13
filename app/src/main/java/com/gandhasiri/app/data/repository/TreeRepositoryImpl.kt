package com.gandhasiri.app.data.repository

import com.gandhasiri.app.domain.model.Tree
import com.gandhasiri.app.domain.repository.TreeRepository
import com.gandhasiri.app.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TreeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TreeRepository {

    private val treesCollection = firestore.collection("Trees")

    override fun getTrees(farmerId: String): Flow<Resource<List<Tree>>> = callbackFlow {
        trySend(Resource.Loading())
        val listener = treesCollection
            .whereEqualTo("farmerId", farmerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Unknown error"))
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val trees = snapshot.toObjects(Tree::class.java)
                    trySend(Resource.Success(trees))
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun registerTree(tree: Tree): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val documentId = if (tree.id.isEmpty()) treesCollection.document().id else tree.id
            val newTree = tree.copy(id = documentId)
            treesCollection.document(documentId).set(newTree).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to register tree"))
        }
    }

    override suspend fun updateTree(tree: Tree): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            treesCollection.document(tree.id).set(tree).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to update tree"))
        }
    }

    override suspend fun deleteTree(treeId: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            treesCollection.document(treeId).delete().await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to delete tree"))
        }
    }
}
