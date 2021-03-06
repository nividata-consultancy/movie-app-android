package com.nividata.movie_time.view.common

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.Coil
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.nividata.movie_time.R
import com.nividata.movie_time.domain.data.Constant
import com.nividata.movie_time.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private suspend fun calculateSwatchesInImage(
    context: Context,
    imageUrl: String?
): Int {
    val request = ImageRequest.Builder(context)
        .data(Constant.IMAGE_BASE_URL.plus(imageUrl))
        .allowHardware(false)
        .build()

    val bitmap = when (val result = Coil.execute(request)) {
        is SuccessResult -> result.drawable.toBitmap()
        else -> null
    }

    return bitmap?.let {
        withContext(Dispatchers.Default) {
            val palette = Palette.Builder(bitmap).generate()
            palette.getLightVibrantColor(ContextCompat.getColor(context, R.color.red))
        }
    } ?: ContextCompat.getColor(context, R.color.red)
}

@Composable
fun RoundCardView(movie: Movie, index: Int, onItemClicked: (Int, String) -> Unit) {

    var state by remember { mutableStateOf(R.color.red) }
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        state = calculateSwatchesInImage(context = context, movie.posterPath)
    }

    Box() {
        Card(
            modifier = Modifier
                .height(95.dp)
                .width(95.dp)
                .clickable {
                    onItemClicked(movie.id, movie.type)
                },
            shape = RoundedCornerShape(47.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color(
                    android.graphics.Color.parseColor(
                        "#${
                            Integer.toHexString(state).substring(2)
                        }"
                    )
                )
            )
        ) {
            Image(
                painter = rememberImagePainter(Constant.IMAGE_BASE_URL.plus(movie.posterPath),
                    builder = {
                        placeholder(R.drawable.place_holder)
                        error(R.drawable.place_holder)
                    }),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = (index + 1).toString(),
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp)
                .align(alignment = Alignment.TopStart)
                .width(20.dp)
                .height(20.dp)
                .background(
                    color = Color(
                        android.graphics.Color.parseColor(
                            "#${
                                Integer
                                    .toHexString(state)
                                    .substring(2)
                            }"
                        )
                    ), shape = CircleShape
                ),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
}