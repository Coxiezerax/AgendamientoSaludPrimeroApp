package com.elcoxie.agendamientosaludprimeroapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elcoxie.agendamientosaludprimeroapp.data.MedicosPredeterminados
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Esmeralda
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Mora2
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.WhiteText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicosListScreen(navController: NavController) {
    Scaffold(
        topBar = {

            TopAppBar(
                title = { Text("Médicos Registrados", color = WhiteText) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = WhiteText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Mora2)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Esmeralda)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Listado de Médicos 👨‍⚕️👩‍⚕️",
                color = Mora2,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (MedicosPredeterminados.lista1.isEmpty()) {
                Text("No hay médicos registrados.", color = Mora2)
            } else {
                LazyColumn {
                    items(MedicosPredeterminados.lista1) { medico ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = medico,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Mora2
                            )
                        }
                    }
                }
            }
        }
    }
}


