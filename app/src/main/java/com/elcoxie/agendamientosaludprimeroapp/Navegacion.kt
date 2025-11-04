package com.elcoxie.agendamientosaludprimeroapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elcoxie.agendamientosaludprimeroapp.viewmodel.PacienteViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    val pacienteVM: PacienteViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home" //  p inicio
    ) {
        // menu
        composable(route = "home") {
            Menu(navController)
        }

        //  registro pacientes
        composable(route = "registro_pacientes") {
            RegistroPacientesApp(navController = navController, vm = pacienteVM)
        }

        // pacientes registrados
        composable(route = "pacientes_list") {
            PacientesListScreen(navController = navController, vm = pacienteVM)
        }

        // medicos registrados
        composable(route = "medicos_list") {
            MedicosListScreen(navController = navController)
        }

        // agendar cita
        composable(route = "agendamiento") {
            AgendamientoScreen(
                pacienteVM = pacienteVM,
                navController = navController )//
        }
    }
}



