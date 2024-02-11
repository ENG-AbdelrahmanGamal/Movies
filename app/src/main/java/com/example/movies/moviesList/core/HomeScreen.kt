package com.example.movies.moviesList.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.R
import com.example.movies.moviesList.presentation.MovieListUIEvent
import com.example.movies.moviesList.presentation.MovieListViewModel
import com.example.movies.moviesList.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<MovieListViewModel>()

    val movieState = viewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController, onEvent = viewModel::onEvent)

        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieState.isCurrentPopularScreen) {
                            stringResource(R.string.popular_movies)
                        } else
                            stringResource(R.string.upcoming_movies),
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,

                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    //COLOR FOR TOP BAR
                    MaterialTheme.colorScheme.primaryContainer

                )
            )

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovieList.route
            ) {
                composable(Screen.PopularMovieList.route) {
                    popularMovieScreen(
                        movieListState = movieState,
                        navHostController = navController,
                        onEvent = viewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMoveList.route) {
                    UpcomingMovieScreen(
                        movieListState = movieState,
                        navHostController = navController,
                        onEvent = viewModel::onEvent
                    )
                }

            }
        }
    }


}

@Composable
fun BottomNavigationBar(
    bottomNavHostController: NavHostController,
    onEvent: (MovieListUIEvent) -> Unit
) {
    val item = listOf(
        BottomItem(
            titile = stringResource(R.string.popular),
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            titile = stringResource(R.string.upcoming),
            icon = Icons.Rounded.Upcoming
        )
    )
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
            item.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected.intValue == index, {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                onEvent(MovieListUIEvent.Navigate)
                                bottomNavHostController.popBackStack()
                                bottomNavHostController.navigate(Screen.PopularMovieList.route)

                            }

                            1 -> {
                                onEvent(MovieListUIEvent.Navigate)
                                bottomNavHostController.popBackStack()
                                bottomNavHostController.navigate(Screen.UpcomingMoveList.route)
                            }
                        }

                    },
                    {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.titile,
                            tint = MaterialTheme.colorScheme.surfaceTint

                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.titile,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )


                    }

                )

            }
        }
    }

}


data class BottomItem(
    val titile: String,
    val icon: ImageVector
)