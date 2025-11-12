# Agendamiento Salud Primero

Aplicación Android desarrollada en **Kotlin** con **Jetpack Compose** que permite registrar pacientes, listar médicos y agendar citas médicas.

---

#Tecnologías utilizadas
- **Android Studio**
- **Kotlin**
- **Jetpack Compose**
- **Room (SQLite local)**
- **Material Design 3**
- **MVVM Architecture**
- **Git y GitHub**

---

# Funcionalidades principales
- Registro de pacientes con validaciones visuales (campos requeridos, íconos de error, mensajes Snackbar).
- Almacenamiento local con Room (persistencia de datos).
- Listado de pacientes con opción de llamada telefónica.
- Agendamiento de citas con calendario (DatePicker).
- Animaciones funcionales: transiciones y cambio de color en botones.
- Estructura modular (paquetes `data`, `viewmodel`, `ui`).

---

# Arquitectura MVVM
- **Model:** entidades y DAO (`Paciente.kt`, `PacienteDao.kt`, `AppDatabase.kt`)
- **ViewModel:** lógica de negocio y conexión con Room (`PacienteViewModel.kt`)
- **View (UI):** pantallas y componentes con Compose (`RegistroPacientesApp.kt`, `PacientesListScreen.kt`, `AgendamientoScreen.kt`)

---

# Equipo de trabajo
- Tech saluddigital
