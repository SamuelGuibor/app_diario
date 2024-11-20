package com.samuel.alimento.ui.screens.financas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun TelaFinancas(drawerState: DrawerState, navController: NavController) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var aguaRecomendada by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center) // Centraliza todos os itens dentro da Box
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = altura,
                onValueChange = { altura = it },
                label = { Text("Altura (cm)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (peso.isNotBlank() && altura.isNotBlank()) {
                        val pesoFloat = peso.toFloat()
                        aguaRecomendada = pesoFloat * 35 / 1000
                    }
                }
            ) {
                Text("Calcular Água")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Água recomendada: ${aguaRecomendada}L", color = Color.Blue, fontWeight = FontWeight.Bold)
        }


        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_revert), // Ícone de voltar (padrão)
                contentDescription = "Voltar"
            )
        }
    }
}


