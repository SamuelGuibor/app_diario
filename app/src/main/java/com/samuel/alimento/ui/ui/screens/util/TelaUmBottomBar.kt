package com.samuel.alimento.ui.screens.util

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

object TelaUm {
    const val TELA_AFAZERES_ROUTE = "t1a"
}

@Composable
fun TelaUmBottomBar(navController: NavController) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()


    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(containerColor = Color(0xFF020E86)) {

        NavigationBarItem(
            selected = currentRoute == TelaUm.TELA_AFAZERES_ROUTE,
            onClick = {
                navController.navigate(TelaUm.TELA_AFAZERES_ROUTE) {
                    popUpTo(TelaUm.TELA_AFAZERES_ROUTE) { inclusive = true }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(40.dp)
                )
            },
            label = { Text(text = "Home", color = Color.White, fontWeight = FontWeight.Bold) }
        )


    }
}
