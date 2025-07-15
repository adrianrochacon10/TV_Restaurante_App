package com.example.tv_restaurante_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tv_restaurante_app.model.EstadoOrden
import com.example.tv_restaurante_app.model.Orden

@Composable
fun OrdenCard(orden: Orden, onButtonClick: () -> Unit, onSecondaryButtonClick: () -> Unit) {
    // Color e ícono de borde y estado según el estado de la orden
    val (borderColor, estadoColor, estadoIcon, estadoLabel) = when (orden.estado) {
        EstadoOrden.RECIBIDO -> Quadruple(Color(0xFFCCE0FF), Color(0xFF2196F3), Icons.Filled.Inbox, "Recibido")
        EstadoOrden.PREPARANDO -> Quadruple(Color(0xFFFFF1B0), Color(0xFFFFC107), Icons.Filled.Timer, "Preparando")
        EstadoOrden.COCINANDO -> Quadruple(Color(0xFFFFCCCC), Color(0xFFFF5722), Icons.Filled.LocalDining, "Cocinando")
        EstadoOrden.LISTO -> Quadruple(Color(0xFFB9F6CA), Color(0xFF4CAF50), Icons.Filled.CheckCircle, "Listo")
        else -> Quadruple(Color.LightGray, Color.Gray, Icons.Filled.Inbox, "Otro")
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 220.dp)
            .background(Color.White)
            .border(2.dp, borderColor, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 6.dp, horizontal = 2.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Encabezado: ID, Estado, Nombre, Mesa, Hora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(orden.id, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    Spacer(Modifier.width(6.dp))
                    Icon(estadoIcon, contentDescription = estadoLabel, tint = estadoColor, modifier = Modifier.size(20.dp))
                    Text("  $estadoLabel", color = estadoColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                // Aquí podrías poner la hora si la tienes en el modelo
            }
            Spacer(Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${orden.nombreCliente}", fontSize = 14.sp, color = Color.DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("Mesa ${orden.mesa}", fontSize = 14.sp, color = Color.DarkGray)
            }
            Spacer(Modifier.height(4.dp))
            // Lista de productos
            orden.items.forEach { item ->
                Text(item, fontSize = 13.sp, color = Color.Black)
            }
            Spacer(Modifier.height(8.dp))
            // Notas (si existieran, aquí ejemplo)
            // Box(
            //     modifier = Modifier
            //         .background(Color(0xFFFFF9C4), shape = RoundedCornerShape(6.dp))
            //         .padding(horizontal = 8.dp, vertical = 4.dp)
            // ) {
            //     Text("Notas: ...", fontSize = 13.sp, color = Color(0xFF795548))
            // }
            // Tiempo y total
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${orden.tiempo}", fontSize = 13.sp, color = Color(0xFF757575))
                Text(orden.total, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(Modifier.height(10.dp))
            // Botones de acción
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = onButtonClick,
                    colors = ButtonDefaults.buttonColors(containerColor = estadoColor),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        when (orden.estado) {
                            EstadoOrden.RECIBIDO -> "Iniciar Prep."
                            EstadoOrden.PREPARANDO -> "A Cocción"
                            EstadoOrden.COCINANDO -> "Marcar Listo"
                            EstadoOrden.LISTO -> "Marcar Entregado"
                            else -> ""
                        },
                        color = Color.White
                    )
                }
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = onSecondaryButtonClick, // Acción secundaria
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Text("Regresar", color = Color.DarkGray)
                }
            }
        }
    }
}

// Utilidad para devolver 4 valores
data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)