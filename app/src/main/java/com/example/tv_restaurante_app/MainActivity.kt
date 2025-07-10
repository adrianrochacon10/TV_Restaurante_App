package com.example.tv_restaurante_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

// ------------------ MODELO ------------------

/**
 * Enum que representa los distintos estados por los que pasa una orden
 */
enum class EstadoOrden { RECIBIDO, PREPARANDO, COCINANDO, LISTO, ENTREGADO }

/**
 * Modelo de datos que representa una orden en el sistema
 */
data class Orden(
    val id: String,
    val nombreCliente: String,
    val mesa: Int,
    val items: List<String>,
    val tiempo: String,
    val total: String,
    var estado: EstadoOrden
)

// ------------------ ACTIVIDAD PRINCIPAL ------------------

/**
 * Actividad principal que lanza la UI compuesta por KitchenOrderApp
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KitchenOrderApp()
        }
    }
}

// ------------------ UI PRINCIPAL ------------------

/**
 * Componente principal de la interfaz. Gestiona la lista de órdenes y su visualización.
 */
@Composable
fun KitchenOrderApp() {
    // Lista de órdenes simuladas con estado mutable
    val ordenes = remember {
        mutableStateListOf(
            Orden("ORD-2024-001", "María García", 5, listOf("Salmón x1", "Pasta x2", "Volcán x1"), "18 min", "$80.50", EstadoOrden.PREPARANDO),
            Orden("ORD-2024-002", "Carlos Ruiz", 12, listOf("Pizza x1", "Ensalada x1"), "22 min", "$45.00", EstadoOrden.RECIBIDO),
            Orden("ORD-2024-003", "Ana López", 8, listOf("Hamburguesa x2", "Pollo x1"), "8 min", "$67.50", EstadoOrden.COCINANDO),
            Orden("ORD-2024-004", "Roberto Silva", 3, listOf("Tiramisú x2", "Ensalada x1"), "Listo", "$32.00", EstadoOrden.LISTO),
            Orden("ORD-2024-005", "Laura Pérez", 6, listOf("Tacos x3", "Agua x1"), "15 min", "$50.00", EstadoOrden.RECIBIDO),
            Orden("ORD-2024-006", "Luis Torres", 10, listOf("Sopa x1", "Ensalada x1"), "10 min", "$42.00", EstadoOrden.PREPARANDO)
        )
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF0F0F0)) {
        Column {
            Header(ordenes) // Muestra la cabecera con hora y resumen

            // Muestra las órdenes activas en una cuadrícula de 3 columnas
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(ordenes.filter { it.estado != EstadoOrden.ENTREGADO }) { orden ->
                    OrdenCard(orden = orden) {
                        // Cambio de estado al presionar el botón
                        orden.estado = when (orden.estado) {
                            EstadoOrden.RECIBIDO -> EstadoOrden.PREPARANDO
                            EstadoOrden.PREPARANDO -> EstadoOrden.COCINANDO
                            EstadoOrden.COCINANDO -> EstadoOrden.LISTO
                            EstadoOrden.LISTO -> EstadoOrden.ENTREGADO
                            else -> orden.estado
                        }
                    }
                }
            }
        }
    }
}

// ------------------ CABECERA ------------------

/**
 * Devuelve la hora actual en formato "HH:mm"
 */
fun getHoraActual(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date())
}

/**
 * Cabecera superior con título, hora actual y resumen de estados de órdenes
 */
@Composable
fun Header(ordenes: List<Orden>) {
    val horaActual = remember { mutableStateOf(getHoraActual()) }

    // Efecto que actualiza la hora cada minuto
    LaunchedEffect(Unit) {
        while (true) {
            horaActual.value = getHoraActual()
            delay(60_000)
        }
    }

    val ordenesActivas = ordenes.count { it.estado != EstadoOrden.ENTREGADO }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Cocina - Sabor Abierto", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Panel de Control de Órdenes", fontSize = 14.sp, color = Color.DarkGray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(horaActual.value, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("$ordenesActivas órdenes activas", fontSize = 14.sp, color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjetas de resumen por estado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            EstadoOrden.values().take(4).forEach { estado ->
                EstadoCard(estado, ordenes.count { it.estado == estado })
            }
        }
    }
}

/**
 * Componente que representa el conteo de órdenes por estado
 */
@Composable
fun EstadoCard(estado: EstadoOrden, count: Int) {
    val color = when (estado) {
        EstadoOrden.RECIBIDO -> Color(0xFFCCE0FF)
        EstadoOrden.PREPARANDO -> Color(0xFFFFF1B0)
        EstadoOrden.COCINANDO -> Color(0xFFFFCCCC)
        EstadoOrden.LISTO -> Color(0xFFB9F6CA)
        else -> Color.LightGray
    }

    Column(
        modifier = Modifier
            .width(80.dp)
            .background(color, shape = RoundedCornerShape(10.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(count.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(estado.name.lowercase().replaceFirstChar { it.uppercase() }, fontSize = 12.sp)
    }
}

// ------------------ TARJETA DE ORDEN ------------------

/**
 * Muestra los datos de una orden con su color y botón correspondiente según estado
 */
@Composable
fun OrdenCard(orden: Orden, onButtonClick: () -> Unit) {
    // Color de borde según el estado de la orden
    val borderColor = when (orden.estado) {
        EstadoOrden.RECIBIDO -> Color(0xFFCCE0FF)
        EstadoOrden.PREPARANDO -> Color(0xFFFFC107)
        EstadoOrden.COCINANDO -> Color(0xFFFF5722)
        EstadoOrden.LISTO -> Color(0xFF4CAF50)
        else -> Color.LightGray
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, borderColor, shape = RoundedCornerShape(12.dp))
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(orden.id, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Cliente: ${orden.nombreCliente} - Mesa ${orden.mesa}", fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            orden.items.forEach { Text(it, fontSize = 13.sp) }
            Spacer(Modifier.height(8.dp))
            Text("Tiempo: ${orden.tiempo} • Total: ${orden.total}", fontSize = 13.sp)
            Spacer(Modifier.height(12.dp))

            val buttonText = when (orden.estado) {
                EstadoOrden.RECIBIDO -> "Iniciar Prep."
                EstadoOrden.PREPARANDO -> "A Cocción"
                EstadoOrden.COCINANDO -> "Marcar Listo"
                EstadoOrden.LISTO -> "Marcar Entregado"
                else -> ""
            }

            Button(
                onClick = onButtonClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(buttonText)
            }
        }
    }
}
