package com.nividata.movie_time.domain.data.model.response

import com.nividata.movie_time.domain.model.Movie
import com.squareup.moshi.Json

class MovieList(
    val success: Boolean,
    val data: Data,
    val message: String,
    val page: Int,
    @Json(name = "total_pages") val totalPages: Int,
)

data class Data(
    val categoryType: String,
    val id: Int,
    val title: String,
    val network: Int,
    val movies: List<Movies>
)

data class Movies(
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int,
    val original_language: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int,

    val name: String?,
    val original_name: String?,
    val origin_country: List<String>?,
    val first_air_date: String?,

    val title: String?,
    val original_title: String?,
    val adult: Boolean?,
    val release_date: String?,
    val video: Boolean?,

    val type: String,
) {
    fun toMovie(): Movie = Movie(
        id = id,
        title = if (type == "movie") title!! else name!!,
        type = type,
        backdropPath = backdrop_path,
        posterPath = poster_path
    )
}