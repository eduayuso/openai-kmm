package dev.eduayuso.openaikmm.domain.interactors.type

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

abstract class UseCase {

    operator fun invoke(): Flow<Resource<Unit>> = flow {
        emit(
            try {
                Resource.Success(block())
            } catch (ex: Exception) {
                Resource.Error(exception = ex)
            }
        )
    }

    protected abstract val block: suspend () -> Unit
}