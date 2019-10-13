package br.fiap.trabalhomobile.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Produto(
    val id: Int,
    val nome: String,
    val descricao: String,
    val imagem: String,
    val preco: Double
) : Parcelable