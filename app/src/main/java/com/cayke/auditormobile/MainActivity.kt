package com.cayke.auditormobile

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cayke.auditormobile.viewmodel.AuditoriaViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import com.cayke.auditormobile.network.RetrofitClient
import com.cayke.auditormobile.model.Auditoria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AuditorMobileApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditorMobileApp() {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Auditor Mobile",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Início") },
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null
                        )
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Monitoramento") },
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            Icons.Default.Assessment,
                            contentDescription = null
                        )
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Auditorias") },
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            Icons.Default.List,
                            contentDescription = null
                        )
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Relatórios") },
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            Icons.Default.BarChart,
                            contentDescription = null
                        )
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Configurações") },
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                )
            }
        }

    ) {

        Scaffold(

            containerColor = Color(0xFFF5F5F5),

            topBar = {

                TopAppBar(

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0D1440),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),

                    title = {
                        Text("Auditor Mobile")
                    },

                    navigationIcon = {

                        IconButton(

                            onClick = {

                                scope.launch {
                                    drawerState.open()
                                }
                            }

                        ) {

                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }

        ) { innerPadding ->

            TelaPrincipal(
                Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier) {

    val viewModel: AuditoriaViewModel = viewModel()

    val auditorias = viewModel.auditorias

    val totalAuditorias = auditorias.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {

        if (auditorias.isEmpty()) {

            item {

                Text(
                    text = "Carregando auditorias...",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        item {

            Column {

                Text(
                    text = "Últimas Auditorias",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Total: ${auditorias.size} auditorias",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(auditorias) { auditoria ->

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF1EBF5)
                )

            ) {

                Column(

                    modifier = Modifier.padding(all = 16.dp)

                ) {

                    Text(
                        text = "Produto",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = auditoria.aud_produto
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Preço"
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 6.dp)
                    ) {

                        Text(
                            text = "R$ %.2f".format(
                                auditoria.aud_precoAntigo.toDouble()
                            ).replace(".", ","),
                            color = Color.Red,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(38.dp)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = "R$ %.2f".format(
                                auditoria.aud_precoNovo.toDouble()
                            ).replace(".", ","),
                            color = Color(0xFF0F8A00),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Id Loja: ${auditoria.cad_idLoja}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Id Login: ${auditoria.aud_usuario}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Usuário: ${auditoria.aud_nomeUsuario}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Data/Hora"
                    )

                    Text(
                        text = auditoria.aud_dataHora
                            .replace("T", " ")
                            .replace("Z", "")
                            .substring(0, 19)
                    )
                }
            }
        }
    }
}