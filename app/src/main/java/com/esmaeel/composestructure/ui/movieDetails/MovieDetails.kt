package com.esmaeel.composestructure.ui.movieDetails

import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import coil.compose.rememberImagePainter
import com.esmaeel.composestructure.base.produceUiState
import com.esmaeel.composestructure.data.MovieDetailResponse
import com.esmaeel.composestructure.ui.movies.MovieItem

@Composable
fun MovieDetails(
    viewModel: MoviesDetailsViewModel = viewModel(),
    navController: NavHostController,
    id: String
) {
    val (result, refresh, clearError) = produceUiState(producer = viewModel) {
        getMoviesListPager(id)
    }

    val isLoading = result.value.loading

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movie Details") }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }, actions = {
                IconButton(onClick = { refresh() }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                }
            })
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                ScreenDetails(result.value.data)
            }
            if (isLoading)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun ScreenDetails(data: MovieDetailResponse?) {

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Box(Modifier.fillMaxSize()) {

                Image(
                    painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w500/${data?.poster_path}",
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
                                text = data?.title ?: "",
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
                                    text = data?.vote_average.toString() ?: "",
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
                            text = data?.release_date ?: "",
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = data?.overview ?: "",
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 19.sp,
                                color = Color.White
                            )
                        )
                    }

                }
            }

        }
    }
}
