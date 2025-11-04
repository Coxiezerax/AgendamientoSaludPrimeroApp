package com.elcoxie.agendamientosaludprimeroapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.elcoxie.agendamientosaludprimeroapp.data.Agendamiento

class AgendamientoViewModel : ViewModel() {
    private var nextId = 1
    var listaAgendamientos = mutableStateListOf<Agendamiento>()
        private set

    fun registrarAgendamiento(
        idPaciente: Int,
        medico: String,
        fecha: String,
        hora: String,
        motivo: String
    ) {
        val nuevo = Agendamiento(
            id = nextId++,
            idPaciente = idPaciente,
            medico = medico,
            fecha = fecha,
            hora = hora,
            motivo = motivo
        )
        listaAgendamientos.add(nuevo)
    }

    fun eliminarAgendamiento(id: Int) {
        listaAgendamientos.removeIf { it.id == id }
    }
}

