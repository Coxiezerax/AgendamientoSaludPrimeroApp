package com.elcoxie.agendamientosaludprimeroapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity si queremos agregar una nueva caract en la BD aca
@Entity(tableName = "pacientes")
data class Paciente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val appaterno: String,
    val apmaterno: String,
    val rut: String,
    val edad: Int,
    val telefono: String,
    val email: String
)


