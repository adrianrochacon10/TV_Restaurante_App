package com.example.tv_restaurante_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tv_restaurante_app.model.EstadoOrden
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.Icon

@Composable
fun EstadoCard(estado: EstadoOrden, count: Int) {
    val (color, icon, label) = when (estado) {
        EstadoOrden.RECIBIDO -> Triple(Color(0xFFCCE0FF), Icons.Filled.Inbox, "Recibido")
        EstadoOrden.PREPARANDO -> Triple(Color(0xFFFFF1B0), Icons.Filled.Timer, "Preparando")
        EstadoOrden.COCINANDO -> Triple(Color(0xFFFFCCCC), Icons.Filled.LocalDining, "Cocinando")
        EstadoOrden.LISTO -> Triple(Color(0xFFB9F6CA), Icons.Filled.CheckCircle, "Listo")
        else -> Triple(Color.LightGray, Icons.Filled.Inbox, "Otro")
    }

    Column(
        modifier = Modifier
            .width(80.dp)
            .background(color, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = Color.DarkGray, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(count.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
        Text(label, fontSize = 13.sp, color = Color.DarkGray)
    }
}