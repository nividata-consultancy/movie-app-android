package com.nividata.owls.domain.repository

import android.util.Log
import com.nividata.owls.domain.core.repository.ResponseResult
import com.nividata.owls.domain.core.repository.TmdbRepository
import com.nividata.owls.domain.data.api.TmdbService
import com.nividata.owls.domain.data.model.response.MovieListResponse
import com.nividata.owls.domain.data.model.response.State
import com.nividata.owls.domain.data.util.getResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single source of data of noty user.
 */
@ExperimentalCoroutinesApi
@Singleton
class TmdbRepositoryImp @Inject internal constructor(
    private val tmdbService: TmdbService
) : TmdbRepository {


    override fun getUpcomingMovies(): Flow<ResponseResult<MovieListResponse>> = flow {
        val notesResponse = tmdbService.getUpcomingMovies().getResponse()
        val state = when (notesResponse.status) {
            State.SUCCESS -> ResponseResult.success(notesResponse)
            else -> ResponseResult.error(notesResponse.message)
        }
        emit(state)
    }.catch {
        it.stackTrace
        emit(ResponseResult.error(it.stackTraceToString()))
    }
}