package com.esmaeel.composestructure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.esmaeel.composestructure.ui.movieDetails.MovieDetails
import com.esmaeel.composestructure.ui.movieDetails.MoviesDetailsViewModel
import com.esmaeel.composestructure.ui.movies.MoviesListScreen
import com.esmaeel.composestructure.ui.movies.MoviesViewModel
import com.esmaeel.composestructure.ui.theme.ComposestructureTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MoviesViewModel by viewModels()
    val detailsViewModel: MoviesDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposestructureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "moviesList") {
                        composable(route = "moviesList") {
                            MoviesListScreen(viewModel, navController)
                        }

                        composable(
                            route = "movieDetails/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) {
                            MovieDetails(
                                detailsViewModel,
                                navController,
                                it.arguments?.getString("id") ?: ""
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposestructureTheme {
        Greeting("Android")
    }
}