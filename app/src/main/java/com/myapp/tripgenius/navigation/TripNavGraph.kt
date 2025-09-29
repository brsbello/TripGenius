package com.myapp.tripgenius.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myapp.tripgenius.presentation.home.HomeScreen
import com.myapp.tripgenius.presentation.trip.CreateTripScreen
import com.myapp.tripgenius.presentation.trip.TripListScreen
import com.myapp.tripgenius.presentation.trip.TripViewModel
import com.myapp.tripgenius.shared.domain.model.Trip
import com.myapp.tripgenius.shared.util.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun TripNavGraph(startDestination: String = "home") {
    val navController = rememberNavController()
    val tripViewModel: TripViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = startDestination) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("tripList") {
            TripListScreen(
                navController = navController,
                tripViewModel = tripViewModel
            )
        }

        composable("createTrip") {
            CreateTripScreen(
                tripToEdit = null,
                onTripSaved = { navController.popBackStack() },
                tripViewModel = tripViewModel
            )
        }

        composable(
            route = "editTrip/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.LongType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getLong("tripId") ?: 0L
            val trip = (tripViewModel.trips.collectAsState().value as? UiState.Success<List<Trip>>)
                ?.data?.find { it.id == tripId }

            if (trip != null) {
                CreateTripScreen(
                    tripToEdit = trip,
                    onTripSaved = { navController.popBackStack() },
                    tripViewModel = tripViewModel
                )
            } else {
                CreateTripScreen(
                    tripToEdit = null,
                    onTripSaved = { navController.popBackStack() },
                    tripViewModel = tripViewModel
                )
            }
        }
    }
}
