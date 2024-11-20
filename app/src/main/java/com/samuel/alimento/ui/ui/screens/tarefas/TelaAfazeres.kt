package com.samuel.alimento.ui.screens.tarefas

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuel.alimento.reposity.alimentosReposity
import com.samuel.alimento.ui.screens.util.PlannerTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun TelaAfazeres(
    navController: NavController,
    alimentos: SnapshotStateList<Pair<String, String>>, // Recebendo a lista de alimentos
    drawerState: DrawerState
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val alimentosReposity = alimentosReposity()

    var nomeAlimento by remember { mutableStateOf("") }
    var calorias by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White)
    ) {
        PlannerTopBar(drawerState = drawerState)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nomeAlimento,
            onValueChange = { nomeAlimento = it },
            label = { Text("Nome do Alimento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = calorias.toString(),
            onValueChange = { calorias = it.toIntOrNull() ?: 0 },
            label = { Text("Quantidade de Calorias") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    if (nomeAlimento.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Nome do alimento é obrigatório", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        alimentosReposity.salvarAlimento(nomeAlimento, calorias)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Sucesso ao salvar a tarefa", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                if (nomeAlimento.isNotBlank()) {
                    alimentos.add(Pair(nomeAlimento, calorias.toString())) // Adiciona na lista local
                    nomeAlimento = ""
                    calorias = 0
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Alimento")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(alimentos) { alimento ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${alimento.first} - ${alimento.second} calorias", fontSize = 16.sp)
                    Row {
                        Button(
                            onClick = {
                                val alimentoIndex = alimentos.indexOf(alimento)
                                if (alimentoIndex != -1) {
                                    navController.navigate("editar_alimento/$alimentoIndex")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Editar", color = Color.White)
                        }

                        Button(
                            onClick = {
                                // Remove da lista local
                                alimentos.remove(alimento)

                                // Remove do banco de dados (Firestore)
                                scope.launch(Dispatchers.IO) {
                                    alimentosReposity.excluirAlimento(alimento.first) // Assume que 'alimento.first' é o nome ou ID
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Alimento excluído com sucesso", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Excluir", color = Color.White)
                        }

                    }
                }
            }
        }
    }
}




@Composable
fun EditarAlimentoTela(
    alimentoIndex: Int,
    alimentosState: SnapshotStateList<Pair<String, String>>, // Passando a lista de alimentos
    navController: NavController // Recebendo o NavController
) {
    var nomeAlimento by remember { mutableStateOf(alimentosState[alimentoIndex].first) }
    var caloriasAlimento by remember { mutableStateOf(alimentosState[alimentoIndex].second) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Editar Alimento", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nomeAlimento,
            onValueChange = { nomeAlimento = it },
            label = { Text("Nome do Alimento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = caloriasAlimento,
            onValueChange = { caloriasAlimento = it },
            label = { Text("Calorias") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                alimentosState[alimentoIndex] = Pair(nomeAlimento, caloriasAlimento)


                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Salvar")
        }
    }
}



