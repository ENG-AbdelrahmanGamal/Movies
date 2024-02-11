package com.example.movies.moviesList.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movies.moviesList.details.presentation.DetailsSCreen
import com.example.movies.moviesList.presentation.MovieListViewModel
import com.example.movies.moviesList.util.Screen
import com.example.movies.ui.theme.MoviesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import okio.blackholeSink

//abaca52e53f445dd59c758dae5fdfbea

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {

                setBarCalar(color = MaterialTheme.colorScheme.primary)
                Surface (modifier = Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.secondaryContainer){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route) {
                  composable(Screen.Home.route){
                      HomeScreen(navController)
                  }
                        composable(
                            Screen.Details.route+"/{movieId}",
                            arguments = listOf(
                            navArgument("movieId"){type = NavType.IntType}
                        )){
                            backStackEntry ->
                        DetailsSCreen()
                        }

                    }
                }
            }
        }

    }

    @Composable
    private fun setBarCalar(color:Color) {
        val systemUiController= rememberSystemUiController()
        LaunchedEffect(key1 =color)
        {
            systemUiController.setSystemBarsColor(color)
        }

    }
}


