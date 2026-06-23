package com.cayke.auditormobile.network

import com.cayke.auditormobile.model.Auditoria
import retrofit2.http.GET

interface ApiService {

    @GET("auditorias")
    suspend fun buscarAuditorias(): List<Auditoria>
}