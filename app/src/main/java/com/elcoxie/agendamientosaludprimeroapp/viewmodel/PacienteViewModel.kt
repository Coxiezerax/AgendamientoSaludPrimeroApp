package com.elcoxie.agendamientosaludprimeroapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.elcoxie.agendamientosaludprimeroapp.data.AppDatabase
import com.elcoxie.agendamientosaludprimeroapp.data.Paciente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PacienteViewModel(application: Application) : AndroidViewModel(application) {

    private val pacienteDao = AppDatabase.getDatabase(application).pacienteDao()

    private val _listaPacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val listaPacientes: StateFlow<List<Paciente>> = _listaPacientes

    init {
        cargarPacientes()
    }


    private fun cargarPacientes() {
        viewModelScope.launch {
            _listaPacientes.value = pacienteDao.obtenerPacientes()
        }
    }


    fun registrarPaciente(nombre: String, appaterno: String, apmaterno: String, rut: String, edad: Int, telefono: String, email: String) {
        viewModelScope.launch {
            pacienteDao.insertarPaciente(
                Paciente(
                    nombre = nombre,
                    appaterno = appaterno,
                    apmaterno = apmaterno,
                    rut = rut,
                    edad = edad,
                    telefono = telefono,
                    email = email
                )
            )
            cargarPacientes() // recarga lista
        }
    }


    fun eliminarPaciente(paciente: Paciente) {
        viewModelScope.launch {
            pacienteDao.eliminarPaciente(paciente)
            cargarPacientes()
        }
    }
}


