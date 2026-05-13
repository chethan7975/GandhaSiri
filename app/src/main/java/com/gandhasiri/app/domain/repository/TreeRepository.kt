package com.gandhasiri.app.domain.repository

import com.gandhasiri.app.domain.model.Tree
import com.gandhasiri.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TreeRepository {
    fun getTrees(farmerId: String): Flow<Resource<List<Tree>>>
    suspend fun registerTree(tree: Tree): Flow<Resource<Boolean>>
    suspend fun updateTree(tree: Tree): Flow<Resource<Boolean>>
    suspend fun deleteTree(treeId: String): Flow<Resource<Boolean>>
}
