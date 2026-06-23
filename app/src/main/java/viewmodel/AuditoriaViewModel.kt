package com.cayke.auditormobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cayke.auditormobile.model.Auditoria
import com.cayke.auditormobile.network.RetrofitClient
import kotlinx.coroutines.launch

class AuditoriaViewModel : ViewModel() {

    var auditorias by mutableStateOf<List<Auditoria>>(emptyList())
        private set

    init {
        carregarAuditorias()
    }

    private fun carregarAuditorias() {

        viewModelScope.launch {

            try {

                auditorias = RetrofitClient.api.buscarAuditorias()

                println("TOTAL AUDITORIAS: ${auditorias.size}")

            } catch (e: Exception) {

                println(e.message)
            }
        }
    }
}