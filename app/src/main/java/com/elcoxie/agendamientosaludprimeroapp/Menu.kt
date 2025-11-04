package com.elcoxie.agendamientosaludprimeroapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Esmeralda
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.Mora2
import com.elcoxie.agendamientosaludprimeroapp.ui.theme.WhiteText
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navController: NavController) {
    var visible by remember { mutableStateOf(false) }

    // 🔹 Animación de aparición (fade-in)
    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Clínica Salud Primero",
                        color = WhiteText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Mora2)
            )
        },
        containerColor = Esmeralda
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Esmeralda)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            //
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(800)),
                exit = fadeOut()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_salud_primero),
                        contentDescription = "Logo de la clínica",
                        modifier = Modifier
                            .size(140.dp)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Registro de Citas Médicas",
                        color = Mora2,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
            }

            //
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(1000))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ButtonMenu("Registrar Paciente") {
                        navController.navigate("registro_pacientes")
                    }
                    ButtonMenu("️Médicos Registrados") {
                        navController.navigate("medicos_list")
                    }
                    ButtonMenu("Pacientes Registrados") {
                        navController.navigate("pacientes_list")
                    }
                    ButtonMenu("Agendar Cita Médica") {
                        navController.navigate("agendamiento")
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            //
            Text(
                text = "Versión 1.0 - App Salud Primero",
                color = WhiteText.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}

//
@Composable
fun ButtonMenu(text: String, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }

    //  Animación de color suave al presionar
    val buttonColor by animateColorAsState(
        targetValue = if (pressed) Color(0xFFD05CE3) else Mora2,
        animationSpec = tween(durationMillis = 300),
        label = "buttonColor"
    )

    //
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(200)
            pressed = false
        }
    }

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = WhiteText
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(text, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}
