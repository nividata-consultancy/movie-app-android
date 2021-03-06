package com.nividata.movie_time.view.amazon

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nividata.movie_time.domain.core.repository.MovieTimeRepository
import com.nividata.movie_time.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmazonViewModel @Inject constructor(
    private val movieTimeRepository: MovieTimeRepository
) : BaseViewModel<AmazonContract.Event,
        AmazonContract.State,
        AmazonContract.Effect>() {

    init {
        viewModelScope.launch {
            getAmazonData()
        }
    }

    override fun setInitialState(): AmazonContract.State = AmazonContract.State.Loading

    override fun handleEvents(event: AmazonContract.Event) {
        when (event) {
            is AmazonContract.Event.AmazonItemSelection -> {
                if (event.type == "movie") {
                    setEffect {
                        AmazonContract.Effect.Navigation.ToMovieDetails(event.id)
                    }
                } else {
                    setEffect {
                        AmazonContract.Effect.Navigation.ToTvDetails(event.id)
                    }
                }
            }
            is AmazonContract.Event.MovieListSelection -> {
                setEffect {
                    AmazonContract.Effect.Navigation.ToMovieList(
                        categoryName = event.categoryName,
                        categoryType = event.categoryType
                    )
                }
            }
        }
    }

    private suspend fun getAmazonData() {
        try {
            val homeMovieList = movieTimeRepository.getAmazonData()
            setState { AmazonContract.State.Success(homeMovieList) }
        } catch (e: Exception) {
            Log.e("ok", e.toString())
        }
    }
}