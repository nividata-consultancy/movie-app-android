package com.nividata.owls.view.movieDetails

import com.nividata.owls.domain.data.model.response.MovieList
import com.nividata.owls.domain.data.model.response.MovieListResponse
import com.nividata.owls.domain.model.CastCrew
import com.nividata.owls.domain.model.HomeMovieList
import com.nividata.owls.domain.model.MovieDetails
import com.nividata.owls.view.base.ViewEvent
import com.nividata.owls.view.base.ViewSideEffect
import com.nividata.owls.view.base.ViewState

sealed class MovieDetailsContract {
    sealed class Event : ViewEvent {
        data class MovieSelection(val movieId: String) : Event()
    }


    sealed class State : ViewState {
        data class Success(
            val movieDetails: MovieDetails,
            val castCrew: CastCrew,
            val recommendations: HomeMovieList
        ) : State()

        object Loading : State()
        data class Failed(val message: String) : State()
    }

//    data class State(val categories: List<MovieResponse> = listOf()) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToMovieDetails(val movieId: String) : Navigation()
        }
    }
}
