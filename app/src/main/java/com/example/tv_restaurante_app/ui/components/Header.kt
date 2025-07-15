package com.example.tv_restaurante_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tv_restaurante_app.model.Orden
import com.example.tv_restaurante_app.model.EstadoOrden
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.Icon

private fun getHoraActual(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date())
}

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
            .shadow(4.dp)
            .padding(horizontal = 0.dp, vertical = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Cocina - Sabor Abierto",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    "Panel de Control de Órdenes",
                    fontSize = 15.sp,
                    color = Color(0xFF757575),
                    fontWeight = FontWeight.Normal
                )
            }
            Column(
                modifier = Modifier.weight(0.7f),
                horizontalAlignment = Alignment.End
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = "Hora", tint = Color(0xFF222222), modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(horaActual.value, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ListAlt, contentDescription = "Órdenes", tint = Color(0xFF757575), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$ordenesActivas órdenes activas", fontSize = 15.sp, color = Color(0xFF757575))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

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