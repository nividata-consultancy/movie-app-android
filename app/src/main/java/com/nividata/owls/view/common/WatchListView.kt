package com.nividata.owls.view.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nividata.owls.domain.data.Constant
import com.nividata.owls.domain.model.WatchProviders
import kotlinx.coroutines.CoroutineScope

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun WatchListView(
    link: String,
    flatrateList: List<WatchProviders.Results.IN.Flatrate>,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    content: @Composable() () -> Unit,
) {
//    val localUriHandler = LocalUriHandler.current
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            LazyVerticalGrid(
                cells = GridCells.Fixed(4),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(flatrateList) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
//                                localUriHandler.openUri(link)
//                                localUriHandler.openUri("https://www.hotstar.com/")
//                                localUriHandler.openUri("https://www.netflix.com")
                            }
                    ) {
                        Surface(shape = RoundedCornerShape(4.dp)) {
                            Image(
                                painter = rememberImagePainter(Constant.IMAGE_BASE_URL_500.plus(it.logo_path)),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Fit,
                            )
                        }
                        Text(
                            text = it.provider_name,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        },
    ) {
        content()
    }
}