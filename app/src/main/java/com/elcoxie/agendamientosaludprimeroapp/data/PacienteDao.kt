package com.elcoxie.agendamientosaludprimeroapp.data

import androidx.room.*

@Dao
interface PacienteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPaciente(paciente: Paciente)

    @Query("SELECT * FROM pacientes ORDER BY id DESC")
    suspend fun obtenerPacientes(): List<Paciente>


    @Delete
    suspend fun eliminarPaciente(paciente: Paciente)
}
