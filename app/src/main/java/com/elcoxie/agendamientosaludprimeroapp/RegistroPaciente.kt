package com.elcoxie.agendamientosaludprimeroapp

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Esmeralda
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Mora2
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.WhiteText
import com.elcoxie.agendamientosaludprimeroapp.viewmodel.PacienteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RegistroPacientesApp(
    navController: NavController,
    vm: PacienteViewModel = viewModel()
) {
    // declaramos variables
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var appaterno by remember { mutableStateOf(TextFieldValue("")) }
    var apmaterno by remember { mutableStateOf(TextFieldValue("")) }
    var rut by remember { mutableStateOf(TextFieldValue("")) }
    var edad by remember { mutableStateOf(TextFieldValue("")) }
    var telefono by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }

    // estados
    var rutError by remember { mutableStateOf(false) }
    var nombreError by remember { mutableStateOf(false) }


    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // animacion boton
    val buttonColor by animateColorAsState(
        targetValue = if (nombre.text.isNotBlank() && rut.text.isNotBlank()) Mora2 else Color.Gray,
        animationSpec = tween(500)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Paciente", color = WhiteText) },
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nombreError = nombre.text.isBlank()
                    rutError = rut.text.isBlank()

                    if (!nombreError && !rutError) {
                        vm.registrarPaciente(
                            nombre.text,
                            appaterno.text,
                            apmaterno.text,
                            rut.text,
                            edad.text.toIntOrNull() ?: 0,
                            telefono.text,
                            email.text
                        )

                        //
                        nombre = TextFieldValue("")
                        appaterno = TextFieldValue("")
                        apmaterno = TextFieldValue("")
                        rut = TextFieldValue("")
                        edad = TextFieldValue("")
                        telefono = TextFieldValue("")
                        email = TextFieldValue("")

                        scope.launch {
                            snackbarHostState.showSnackbar("Paciente registrado correctamente")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Completa los campos faltantes")
                        }
                    }
                },
                containerColor = buttonColor,
                contentColor = WhiteText
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = "Agregar paciente")
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
            //
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800)) + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                Text(
                    text = "Registro de Pacientes ",
                    fontSize = 22.sp,
                    color = Mora2,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
            }

            // campos visuales
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = it.text.isBlank()
                },
                label = { Text("Nombre") },
                isError = nombreError,
                supportingText = {
                    if (nombreError) Text("Campo requerido", color = MaterialTheme.colorScheme.error)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = appaterno,
                onValueChange = { appaterno = it },
                label = { Text("Apellido Paterno") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = apmaterno,
                onValueChange = { apmaterno = it },
                label = { Text("Apellido Materno") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rut,
                onValueChange = {
                    rut = it
                    rutError = it.text.isBlank()
                },
                label = { Text("RUT") },
                isError = rutError,
                supportingText = {
                    if (rutError) Text("Campo requerido", color = MaterialTheme.colorScheme.error)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
