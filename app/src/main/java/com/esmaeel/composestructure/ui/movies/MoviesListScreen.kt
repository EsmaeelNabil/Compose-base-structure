package com.esmaeel.composestructure.ui.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.esmaeel.composestructure.base.paging.PagingState
import com.esmaeel.composestructure.data.MovieListResponse


@Composable
fun MoviesListScreen(
    moviesViewModel: MoviesViewModel = viewModel(),
    navController: NavHostController
) {

    val data = moviesViewModel.getMoviesListPager().collectAsLazyPagingItems()

    Scaffold {
        Surface(color = Color.Cyan.copy(alpha = 0.03f)) {

            LazyColumn(state = rememberLazyListState()) {

                items(data.itemSnapshotList.items) {
                    MovieItem(it) {
                        navController.navigate("movieDetails/${it.id}")
                    }
                }

                item {
                    PagingState(
                        lazyPagingItems = data,
                        onInitialLoading = {
                            Column(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator()
                            }
                        },
                        onInitialError = { error ->
                            Column(Modifier.fillMaxSize()) {
                                Button(onClick = { data.retry() }) {
                                    Text(text = "$error Retry")
                                }
                            }
                        },

                        onAppendLoading = {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        },
                        onAppendError = { error ->
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = error.toString())
                                Button(onClick = { data.retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }, onNoData = {
                            Column(
                                Modifier.scrollable(
                                    rememberScrollState(),
                                    Orientation.Vertical
                                ),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                TextButton(onClick = { data.refresh() }) {
                                    Text(text = "Empty List! , click to refresh")
                                }
                            }
                        }
                    )
                }

            }

        }
    }
}

@Composable
fun MovieItem(result: MovieListResponse.Result, openDetails: (MovieListResponse.Result) -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clickable {
                    openDetails(result)
                }
        ) {

            Box(Modifier.fillMaxSize()) {

                Image(
                    painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w500/${result.poster_path}",
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    color = Color.Black.copy(alpha = 0.5f)
                ) {

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {

                            Text(
                                modifier = Modifier.weight(1f),
                                overflow = TextOverflow.Ellipsis,
                                text = result.title,
                                style = TextStyle(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            )


                            Row(
                                modifier = Modifier.weight(0.16f),
                                verticalAlignment = Alignment.Top
                            ) {

                                Text(
                                    textAlign = TextAlign.Justify,
                                    text = result.vote_average.toString(),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 26.sp, color = Color.White
                                    )
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                            }


                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = result.release_date,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            text = result.overview,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        )
                    }

                }
            }

        }
    }
}
