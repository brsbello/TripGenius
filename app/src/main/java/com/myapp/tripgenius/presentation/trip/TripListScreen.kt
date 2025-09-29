package com.myapp.tripgenius.presentation.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.myapp.tripgenius.R
import com.myapp.tripgenius.shared.domain.model.Trip
import com.myapp.tripgenius.shared.util.UiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(
    navController: NavController,
    tripViewModel: TripViewModel = koinViewModel()
) {
    val uiState by tripViewModel.trips.collectAsState()

    var expandedTrip by remember { mutableStateOf<Trip?>(null) }
    var showDeleteSheet by remember { mutableStateOf<Trip?>(null) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("Minhas Trips") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back_24),
                        contentDescription = "Voltar"
                    )
                }
            }
        )
    }) { padding ->
        when (uiState) {
            is UiState.Loading -> Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            is UiState.Error -> Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { Text((uiState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error) }

            is UiState.Success -> {
                val trips = (uiState as UiState.Success<List<Trip>>).data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(trips) { trip ->
                        Box {
                            TripItem(
                                trip = trip,
                                onLongPress = { expandedTrip = trip }
                            )
                            DropdownMenu(
                                expanded = expandedTrip?.id == trip.id,
                                onDismissRequest = { expandedTrip = null },
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Editar", color = MaterialTheme.colorScheme.onSurface) },
                                    onClick = {
                                        expandedTrip = null
                                        navController.navigate("editTrip/${trip.id}")
                                    },
                                    leadingIcon = {
                                        Icon(painter = painterResource(id = R.drawable.ic_edit_24),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Deletar", color = MaterialTheme.colorScheme.error) },
                                    onClick = {
                                        expandedTrip = null
                                        showDeleteSheet = trip
                                    },
                                    leadingIcon = {
                                        Icon(painter = painterResource(id = R.drawable.ic_delete_24),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.error)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Cancelar") },
                                    onClick = { expandedTrip = null },
                                    leadingIcon = {
                                        Icon(painter = painterResource(id = R.drawable.ic_arrow_back_24),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.error)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showDeleteSheet != null) {
            AlertDialog(
                onDismissRequest = { showDeleteSheet = null },
                title = { Text("Confirmar exclusão") },
                text = { Text("Deseja realmente deletar esta Trip?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteSheet?.let { tripViewModel.deleteTrip(it) }
                            showDeleteSheet = null
                        }
                    ) { Text("Deletar", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteSheet = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
