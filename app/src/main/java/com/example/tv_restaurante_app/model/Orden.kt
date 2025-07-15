package com.example.tv_restaurante_app.model

enum class EstadoOrden { RECIBIDO, PREPARANDO, COCINANDO, LISTO, ENTREGADO }

data class Orden(
    val id: String,
    val nombreCliente: String,
    val mesa: Int,
    val items: List<String>,
    val tiempo: String,
    val total: String,
    var estado: EstadoOrden
)