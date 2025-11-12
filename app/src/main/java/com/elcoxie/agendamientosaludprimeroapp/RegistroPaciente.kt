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
    // Variables
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var appaterno by remember { mutableStateOf(TextFieldValue("")) }
    var apmaterno by remember { mutableStateOf(TextFieldValue("")) }
    var rut by remember { mutableStateOf(TextFieldValue("")) }
    var edad by remember { mutableStateOf(TextFieldValue("")) }
    var telefono by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }

    // estados de error -> para validaciones que sean obligatorios esos campos
    var nombreError by remember { mutableStateOf(false) }
    var rutError by remember { mutableStateOf(false) }
    var edadError by remember { mutableStateOf(false) }
    var telefonoError by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // animaciones del boton
    val buttonColor by animateColorAsState(
        targetValue = if (
            nombre.text.isNotBlank() &&
            rut.text.isNotBlank() &&
            edad.text.isNotBlank() &&
            telefono.text.isNotBlank()
        ) Mora2 else Color.Gray,
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
        // logica de validaciones
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Validaciones
                    nombreError = nombre.text.isBlank()
                    rutError = rut.text.isBlank()
                    edadError = edad.text.isBlank()
                    telefonoError = telefono.text.isBlank()
                    // para campo obligatorio
                    if (!nombreError && !rutError && !edadError && !telefonoError) {
                        vm.registrarPaciente(
                            nombre.text,
                            appaterno.text,
                            apmaterno.text,
                            rut.text,
                            edad.text.toIntOrNull() ?: 0,
                            telefono.text,
                            email.text
                        )

                        // campos limpios
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
                            snackbarHostState.showSnackbar("Porfavor completa todos los campos que son obligatorios")
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
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800)) + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                Text(
                    text = "Registro de Pacientes",
                    fontSize = 22.sp,
                    color = Mora2,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
            }

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    // esta funcion nos permite que solo se acepten espacios y letras dentro de campo nombre appaterno apmaterno
                    if (it.text.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$"))) {
                        nombre = it
                        nombreError = it.text.isBlank()
                    }
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
                onValueChange = {
                    if (it.text.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$"))) appaterno = it
                },
                label = { Text("Apellido Paterno") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = apmaterno,
                onValueChange = {
                    if (it.text.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$"))) apmaterno = it
                },
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
                onValueChange = {
                    if (it.text.matches(Regex("^[0-9]{0,3}$"))) edad = it
                    edadError = edad.text.isBlank()
                },
                label = { Text("Edad") },
                isError = edadError,
                supportingText = {
                    if (edadError) Text("Campo requerido", color = MaterialTheme.colorScheme.error)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )


            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    if (it.text.matches(Regex("^[0-9]{0,9}$"))) telefono = it
                    telefonoError = telefono.text.isBlank()
                },
                label = { Text("Teléfono") },
                isError = telefonoError,
                supportingText = {
                    if (telefonoError) Text("Campo requerido", color = MaterialTheme.colorScheme.error)
                },
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

