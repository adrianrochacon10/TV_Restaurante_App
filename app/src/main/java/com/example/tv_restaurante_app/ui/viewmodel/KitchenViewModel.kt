package com.example.tv_restaurante_app.ui.viewmodel


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tv_restaurante_app.model.EstadoOrden
import com.example.tv_restaurante_app.model.Orden

class KitchenViewModel : ViewModel() {
    private val _ordenes = mutableStateListOf(
        Orden("ORD-2024-001", "María García", 5, listOf("Salmón x1", "Pasta x2", "Volcán x1"), "18 min", "$80.50", EstadoOrden.PREPARANDO),
        Orden("ORD-2024-002", "Carlos Ruiz", 12, listOf("Pizza x1", "Ensalada x1"), "22 min", "$45.00", EstadoOrden.RECIBIDO),

    )

    val ordenes: List<Orden> get() = _ordenes

    fun cambiarEstado(orden: Orden) {
        val index = _ordenes.indexOfFirst { it.id == orden.id }
        if (index != -1) {
            _ordenes[index] = orden.copy(
                estado = when (orden.estado) {
                    EstadoOrden.RECIBIDO -> EstadoOrden.PREPARANDO
                    EstadoOrden.PREPARANDO -> EstadoOrden.COCINANDO
                    EstadoOrden.COCINANDO -> EstadoOrden.LISTO
                    EstadoOrden.LISTO -> EstadoOrden.ENTREGADO
                    else -> orden.estado
                }
            )
        }
    }

    fun regresarEstado(orden: Orden) {
        val index = _ordenes.indexOfFirst { it.id == orden.id }
        if (index != -1) {
            _ordenes[index] = orden.copy(
                estado = when (orden.estado) {
                    EstadoOrden.PREPARANDO -> EstadoOrden.RECIBIDO
                    EstadoOrden.COCINANDO -> EstadoOrden.PREPARANDO
                    EstadoOrden.LISTO -> EstadoOrden.COCINANDO
                    EstadoOrden.ENTREGADO -> EstadoOrden.LISTO
                    else -> orden.estado
                }
            )
        }
    }
}