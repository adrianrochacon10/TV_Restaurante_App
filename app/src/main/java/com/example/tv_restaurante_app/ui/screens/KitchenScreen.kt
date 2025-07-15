package com.example.tv_restaurante_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tv_restaurante_app.model.Orden
import com.example.tv_restaurante_app.ui.components.Header
import com.example.tv_restaurante_app.ui.components.OrdenCard
import com.example.tv_restaurante_app.ui.viewmodel.KitchenViewModel
import com.example.tv_restaurante_app.model.EstadoOrden

@Composable
fun KitchenScreen(viewModel: KitchenViewModel = viewModel()) {
    val ordenes = viewModel.ordenes

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF0F0F0)) {
        Column {
            Header(ordenes)

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(ordenes.filter { it.estado != EstadoOrden.ENTREGADO }) { orden ->
                    OrdenCard(
                        orden = orden,
                        onButtonClick = { viewModel.cambiarEstado(orden) },
                        onSecondaryButtonClick = { viewModel.regresarEstado(orden) }
                    )
                }
            }
        }
    }
}