package com.cayke.auditormobile.ui.produtos

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cayke.auditormobile.viewmodel.AuditoriaViewModel
import java.util.Calendar

@Composable
fun ProdutosScreen(
    modifier: Modifier = Modifier
) {

    var idLoja by remember {
        mutableStateOf("")
    }

    var dataInicial by remember {
        mutableStateOf("")
    }

    var dataFinal by remember {
        mutableStateOf("")
    }

    val viewModel: AuditoriaViewModel = viewModel()

    val calendar = Calendar.getInstance()

    val datePickerInicial = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->

            dataInicial =
                "%04d-%02d-%02d".format(
                    year,
                    month + 1,
                    dayOfMonth
                )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val datePickerFinal = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->

            dataFinal =
                "%04d-%02d-%02d".format(
                    year,
                    month + 1,
                    dayOfMonth
                )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Auditoria de Produtos - TESTE",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = idLoja,
            onValueChange = {
                idLoja = it
            },
            label = {
                Text("ID da Loja")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = dataInicial,
                onValueChange = {},
                label = {
                    Text("Data Inicial")
                },
                placeholder = {
                    Text("Selecione a data")
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {
                        datePickerInicial.show()
                    }
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = dataFinal,
                onValueChange = {},
                label = {
                    Text("Data Final")
                },
                placeholder = {
                    Text("Selecione a data")
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {
                        datePickerFinal.show()
                    }
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                if (
                    idLoja.isNotBlank() &&
                    dataInicial.isNotBlank() &&
                    dataFinal.isNotBlank()
                ) {

                    viewModel.buscarAuditoriasFiltradas(
                        idLoja = idLoja.toInt(),
                        dataInicial = dataInicial,
                        dataFinal = dataFinal
                    )
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("FILTRAR")
        }
    }
}