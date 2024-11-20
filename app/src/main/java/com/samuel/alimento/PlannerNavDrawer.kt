    package com.samuel.alimento

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.tooling.preview.Devices
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.compose.rememberNavController
    import com.samuel.alimento.ui.screens.financas.TelaFinancas
    import com.samuel.alimento.ui.screens.projetos.PersonalTrainer
    import com.samuel.alimento.ui.screens.projetos.TelaProjeto
    import com.samuel.alimento.ui.screens.tarefas.EditarAlimentoTela
    import com.samuel.alimento.ui.screens.tarefas.TelaAfazeres
    import com.samuel.alimento.R
    import kotlinx.coroutines.launch

    object PlannerRotas {
        const val TELA_TAREFAS_ROTA = "tela_um"
        const val TELA_PROJETOS_ROTA = "tela_dois"
        const val TELA_FINANCAS_ROTA = "tela_tres"
    }

    @Preview(
        device = Devices.PIXEL
    )
    @Composable
    fun PlannerNavDrawer() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val navCtrlDrawer = rememberNavController() // NavController para o Drawer
            val alimentosState = remember { mutableStateListOf<Pair<String, String>>() } // Lista de alimentos

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(navCtrlDrawer, drawerState)
            },
            content = {
                NavHost(
                    navController = navCtrlDrawer,
                    startDestination = PlannerRotas.TELA_TAREFAS_ROTA
                ) {
                    composable(PlannerRotas.TELA_TAREFAS_ROTA) {
                        TelaAfazeres(
                            navController = navCtrlDrawer,
                            alimentos = alimentosState, // Passando a lista para a tela
                            drawerState = drawerState
                        )
                    }
                    composable("editar_alimento/{alimentoIndex}") { backStackEntry ->
                        val alimentoIndex = backStackEntry.arguments?.getString("alimentoIndex")?.toIntOrNull()
                        alimentoIndex?.let {
                            EditarAlimentoTela(
                                alimentoIndex = it,
                                alimentosState = alimentosState,
                                navController = navCtrlDrawer // Passando o navController para a tela de edição
                            )
                        }
                    }


                    composable(PlannerRotas.TELA_PROJETOS_ROTA) {
                        val contatos = listOf(
                            PersonalTrainer("Carlos Silva", "(11) 98765-4321"),
                            PersonalTrainer("Ana Costa", "(21) 91234-5678"),
                            PersonalTrainer("João Oliveira", "(31) 99876-5432"),
                            PersonalTrainer("Maria Santos", "(41) 99123-4567"),
                            PersonalTrainer("Paulo Souza", "(51) 98765-4321"),
                            PersonalTrainer("Fernanda Lima", "(61) 91234-5678")
                        )
                        TelaProjeto(
                            drawerState = drawerState,
                            contatos = contatos
                        )
                    }

                    composable(PlannerRotas.TELA_FINANCAS_ROTA) {
                        TelaFinancas(
                            drawerState = drawerState,
                            navController = navCtrlDrawer
                        )
                    }
                }
            }
        )
    }


    @Composable
    private fun DrawerContent(
        navController: NavController,
        drawerState: DrawerState
    ) {
        val coroutineScope = rememberCoroutineScope()

        val currentBack by navController.currentBackStackEntryAsState()
        val rotaAtual = currentBack?.destination?.route ?: PlannerRotas.TELA_TAREFAS_ROTA

        val ehRotaUm = rotaAtual == PlannerRotas.TELA_TAREFAS_ROTA
        val ehRotaDois = rotaAtual == PlannerRotas.TELA_PROJETOS_ROTA
        val ehRotaTres = rotaAtual == PlannerRotas.TELA_FINANCAS_ROTA

        Column(
            modifier = Modifier
                .width(300.dp)
                .background(Color.White)
                .padding(30.dp)
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            // Botão para navegar para a Tela de Tarefas
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = getColorMenu(ehRotaUm)
                ),
                onClick = {
                    navController.navigate(PlannerRotas.TELA_TAREFAS_ROTA)
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.food), // Ícone para Tarefas
                    contentDescription = "Home",
                    modifier = Modifier.size(40.dp),
                    tint = getColorTexto(ehRotaUm)
                )
                Text(text = "Home", fontSize = 30.sp, color = getColorTexto(ehRotaUm))
            }

            // Botão para navegar para a Tela de Projetos
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = getColorMenu(ehRotaDois)
                ),
                onClick = {
                    navController.navigate(PlannerRotas.TELA_PROJETOS_ROTA)
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.food), // Ícone para Projetos
                    contentDescription = "Personal",
                    modifier = Modifier.size(40.dp),
                    tint = getColorTexto(ehRotaDois)
                )
                Text(text = "Personal", fontSize = 30.sp, color = getColorTexto(ehRotaDois))
            }

            // Botão para navegar para a Tela de Finanças
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = getColorMenu(ehRotaTres)
                ),
                onClick = {
                    navController.navigate(PlannerRotas.TELA_FINANCAS_ROTA)
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.food), // Ícone para Finanças
                    contentDescription = "Meta Agua",
                    modifier = Modifier.size(40.dp),
                    tint = getColorTexto(ehRotaTres)
                )
                Text(text = "Meta Agua", fontSize = 30.sp, color = getColorTexto(ehRotaTres))
            }
        }
    }

    fun getColorMenu(estaSelecionada: Boolean): Color {
        return if (estaSelecionada) Color.Yellow else Color.Transparent
    }

    fun getColorTexto(estaSelecionada: Boolean): Color {
        return if (estaSelecionada) Color.Black else Color.DarkGray
    }
