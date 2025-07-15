package com.example.tv_restaurante_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tv_restaurante_app.theme.TV_Restaurante_AppTheme
import com.example.tv_restaurante_app.ui.screens.KitchenScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TV_Restaurante_AppTheme {
                KitchenScreen()
            }
        }
    }
}