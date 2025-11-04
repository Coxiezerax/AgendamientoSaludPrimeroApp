package com.elcoxie.agendamientosaludprimeroapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elcoxie.agendamientosaludprimeroapp.data.MedicosPredeterminados
import com.elcoxie.agendamientosaludprimeroapp.viewmodel.AgendamientoViewModel
import com.elcoxie.agendamientosaludprimeroapp.viewmodel.PacienteViewModel
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Esmeralda
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Mora2
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.WhiteText
import java.util.*
import androidx.compose.animation.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AgendamientoScreen(
    pacienteVM: PacienteViewModel = viewModel(),
    agendamientoVM: AgendamientoViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    var pacienteSeleccionado by remember { mutableStateOf<Int?>(null) }
    var medicoSeleccionado by remember { mutableStateOf(MedicosPredeterminados.lista1.first()) }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar Cita Médica", color = WhiteText) },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    pacienteSeleccionado?.let {
                        agendamientoVM.registrarAgendamiento(
                            it,
                            medicoSeleccionado,
                            fecha,
                            hora,
                            motivo
                        )
                        fecha = ""
                        hora = ""
                        motivo = ""
                    }
                },
                containerColor = Mora2,
                contentColor = WhiteText
            ) {
                Icon(Icons.Default.Event, contentDescription = "Agendar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Esmeralda)
                .padding(16.dp)
        ) {
            Text(
                "Agendar cita médica 🩺",
                style = MaterialTheme.typography.headlineSmall,
                color = Mora2
            )

            Spacer(Modifier.height(16.dp))

            // Selección de paciente
            Text("Selecciona un paciente:")
            val pacientes = pacienteVM.listaPacientes.collectAsState()

            DropdownMenuPaciente(pacientes.value) { id -> pacienteSeleccionado = id }


            Spacer(Modifier.height(16.dp))

            // Selección de médico
            Text("Selecciona un médico:")
            DropdownMenuMedico(MedicosPredeterminados.lista1) { medicoSeleccionado = it }

            Spacer(Modifier.height(16.dp))

            // a
            OutlinedTextField(
                value = fecha,
                onValueChange = {},
                label = { Text("Fecha") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                fecha = "$day/${month + 1}/$year"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }) {
                        Icon(Icons.Default.Event, contentDescription = "Seleccionar fecha")
                    }
                }
            )

            //
            OutlinedTextField(
                value = hora,
                onValueChange = {},
                label = { Text("Hora") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                hora = String.format("%02d:%02d", hour, minute)
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    }) {
                        Icon(Icons.Default.Event, contentDescription = "Seleccionar hora")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            // Motivo
            OutlinedTextField(
                value = motivo,
                onValueChange = { motivo = it },
                label = { Text("Motivo de la cita") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Text("Citas agendadas:", style = MaterialTheme.typography.titleMedium)

            //
            AnimatedVisibility(
                visible = agendamientoVM.listaAgendamientos.isNotEmpty(),
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 4 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 4 })
            ) {
                LazyColumn {
                    items(agendamientoVM.listaAgendamientos) { cita ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = WhiteText)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Paciente ID: ${cita.idPaciente}")
                                Text("Médico: ${cita.medico}")
                                Text("Fecha: ${cita.fecha} - Hora: ${cita.hora}")
                                Text("Motivo: ${cita.motivo}")
                                IconButton(onClick = { agendamientoVM.eliminarAgendamiento(cita.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuPaciente(
    pacientes: List<com.elcoxie.agendamientosaludprimeroapp.data.Paciente>,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Seleccionar paciente") }

    Box {
        Button(onClick = { expanded = true }) { Text(selectedText) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            pacientes.forEach { p ->
                DropdownMenuItem(
                    text = { Text("${p.nombre} ${p.appaterno}") },
                    onClick = {
                        onSelect(p.id)
                        selectedText = "${p.nombre} ${p.appaterno}"
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DropdownMenuMedico(medicos: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(medicos.first()) }

    Box {
        Button(onClick = { expanded = true }) { Text(selectedText) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            medicos.forEach { m ->
                DropdownMenuItem(
                    text = { Text(m) },
                    onClick = {
                        onSelect(m)
                        selectedText = m
                        expanded = false
                    }
                )
            }
        }
    }
}



