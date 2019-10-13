package br.fiap.trabalhomobile.model

import com.google.gson.annotations.SerializedName

data class ProdutoResponse(
    @SerializedName("produtos") val produtos: ArrayList<Produto>
)