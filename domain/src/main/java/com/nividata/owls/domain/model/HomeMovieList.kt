package com.nividata.owls.domain.model

data class HomeMovieList(
    val id: Int,
    val title: String,
    val movieList: List<Movie>
)