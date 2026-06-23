package com.cayke.auditormobile.model

data class Auditoria(

    val aud_id: Int,
    val aud_idProduto: Int,
    val aud_produto: String,
    val aud_precoAntigo: Double,
    val aud_precoNovo: Double,
    val aud_usuario: String,
    val aud_dataHora: String,
    val aud_nomeUsuario: String,
    val cad_idLoja: Int
)