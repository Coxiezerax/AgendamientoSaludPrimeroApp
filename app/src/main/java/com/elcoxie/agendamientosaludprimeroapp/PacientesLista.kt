package com.elcoxie.agendamientosaludprimeroapp

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elcoxie.agendamientosaludprimeroapp.viewmodel.PacienteViewModel
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Esmeralda
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Mora2
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.WhiteText
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PacientesListScreen(
    navController: NavController,
    vm: PacienteViewModel = viewModel()
) {
    val context = LocalContext.current

    //
    val pacientes = vm.listaPacientes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pacientes Registrados", color = WhiteText) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                .padding(16.dp)
        ) {
            //
            if (pacientes.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay pacientes registrados.", color = Mora2)
                }
            } else {
                //
                AnimatedVisibility(
                    visible = pacientes.value.isNotEmpty(),
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 }),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 3 })
                ) {
                    LazyColumn {
                        items(pacientes.value) { paciente ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                colors = CardDefaults.cardColors(containerColor = WhiteText),
                                elevation = CardDefaults.cardElevation(3.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "${paciente.nombre} ${paciente.appaterno} ${paciente.apmaterno}",
                                        color = Mora2,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text("RUT: ${paciente.rut}")
                                    Text("Edad: ${paciente.edad} años")
                                    Text("Teléfono: ${paciente.telefono}")
                                    Text("Email: ${paciente.email}")

                                    //
                                    if (paciente.telefono.isNotBlank()) {
                                        Button(
                                            onClick = {
                                                val intent = Intent(Intent.ACTION_DIAL)
                                                intent.data = Uri.parse("tel:${paciente.telefono}")
                                                context.startActivity(intent)
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 6.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Mora2,
                                                contentColor = WhiteText
                                            )
                                        ) {
                                            Icon(Icons.Default.Call, contentDescription = "Llamar")
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Llamar al paciente")
                                        }
                                    }
                                    //
                                    Button(
                                        onClick = { vm.eliminarPaciente(paciente) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 4.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFD32F2F),
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text("Eliminar paciente")
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

