package com.elcoxie.agendamientosaludprimeroapp.data

data class Agendamiento(
    val id: Int = 0,
    val idPaciente: Int = 0,
    val medico: String = "",
    val fecha: String = "",
    val hora: String = "",
    val motivo: String = "",
    val estado: String = "Agendado"
)

