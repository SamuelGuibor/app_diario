package com.samuel.alimento.ui.screens.projetos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuel.alimento.ui.screens.util.PlannerTopBar

// Simulando uma estrutura de Personal Trainer
data class PersonalTrainer(val nome: String, val telefone: String)

@Composable
fun MainScreen(drawerState: DrawerState) {
    // Lista de contatos de exemplo
    val contatos = listOf(
        PersonalTrainer("Carlos Silva", "(11) 98765-4321"),
        PersonalTrainer("Ana Costa", "(21) 91234-5678"),
        PersonalTrainer("João Oliveira", "(31) 99876-5432"),
        PersonalTrainer("Maria Santos", "(41) 99123-4567"),
        PersonalTrainer("Paulo Souza", "(51) 98765-4321"),
        PersonalTrainer("Fernanda Lima", "(61) 91234-5678")
    )

    TelaProjeto(drawerState = drawerState, contatos = contatos)
}

@Composable
fun TelaProjeto(drawerState: DrawerState, contatos: List<PersonalTrainer>) {
    Scaffold(
        topBar = {
            PlannerTopBar(drawerState)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                   ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Contatos de Personal Trainers",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(contatos) { contato ->
                        ContatoCard(contato)
                    }
                }
            }
        }
    )
}

@Composable
fun ContatoCard(contato: PersonalTrainer) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = contato.nome, fontSize = 18.sp, color = Color.Black)
            Text(text = "Tel: ${contato.telefone}", fontSize = 14.sp, color = Color.Gray)
        }
    }
}
