package com.nividata.movie_time.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.nividata.movie_time.R
import com.nividata.movie_time.navigation.Screen
import com.nividata.movie_time.view.amazon.AmazonView
import com.nividata.movie_time.view.hotstar.HotstarView
import com.nividata.movie_time.view.netflix.NetflixView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainView(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val tabs = listOf(Screen.Netflix, Screen.Amazon, Screen.Hotstar)
    val pagerState = rememberPagerState(pageCount = tabs.size)

    val onPageChange: (
        index: Int
    ) -> Unit = { index ->
        viewModel.setEvent(MainContract.Event.ChangeTheme(index))
    }

    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }) {
        Column {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(
                tabs = tabs,
                pagerState = pagerState,
                navController = navController,
                onPageChange = onPageChange,
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Tabs(tabs: List<Screen>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                selectedContentColor = tab.selectedContentColor!!
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = tab.icon),
                        contentDescription = "",
                        alpha = if (pagerState.currentPage == index) 1f else 0.5f
                    )
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun TabsContent(
    tabs: List<Screen>,
    pagerState: PagerState,
    navController: NavHostController,
    onPageChange: (Int) -> Unit,
) {
    HorizontalPager(state = pagerState) { page ->
        onPageChange(pagerState.currentPage)
        when (page) {
            0 -> NetflixView(viewModel = hiltViewModel(), navController = navController)
            1 -> AmazonView(viewModel = hiltViewModel(), navController = navController)
            2 -> HotstarView(viewModel = hiltViewModel(), navController = navController)
        }
    }
}