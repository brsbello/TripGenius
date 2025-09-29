package com.myapp.tripgenius.presentation.trip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.myapp.tripgenius.R
import com.myapp.tripgenius.shared.domain.model.Trip
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(
    tripToEdit: Trip? = null,
    onTripSaved: () -> Unit,
    tripViewModel: TripViewModel = koinViewModel()
) {
    var title by rememberSaveable { mutableStateOf(tripToEdit?.title ?: "") }
    var destination by rememberSaveable { mutableStateOf(tripToEdit?.destination ?: "") }
    var description by rememberSaveable { mutableStateOf(tripToEdit?.description ?: "") }
    var budget by rememberSaveable { mutableStateOf(tripToEdit?.budget?.toString() ?: "") }

    val isSaving by tripViewModel.isSaving.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (tripToEdit == null) "Nova Trip" else "Editar Trip") },
                navigationIcon = {
                    IconButton(onClick = { onTripSaved() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back_24),
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = budget,
                onValueChange = { budget = it },
                label = { Text("Orçamento") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val trip = Trip(
                        id = tripToEdit?.id ?: 0L,
                        title = title,
                        destination = destination,
                        description = description,
                        budget = budget.toDoubleOrNull() ?: 0.0
                    )

                    if (tripToEdit == null) {
                        tripViewModel.addTrip(trip)
                    } else {
                        tripViewModel.updateTrip(trip)
                    }

                    onTripSaved()
                },
                enabled = !isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (tripToEdit == null) "Criar Trip" else "Salvar Alterações")
            }

            if (isSaving) {
                Spacer(Modifier.height(16.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
