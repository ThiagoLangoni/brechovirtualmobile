package br.fiap.trabalhomobile.api

import br.fiap.trabalhomobile.model.Produto
import br.fiap.trabalhomobile.model.ProdutoResponse
import retrofit2.Call
import retrofit2.http.*

interface ProdutoService {

    @GET("/produtos")
    fun getProdutos(): Call<ProdutoResponse>

    @POST("/produtos")
    fun createProduto(@Body produto: Produto) : Call<Produto>

    @PUT("/produtos/{id}")
    fun updateProduto(@Path("id") id : Int, @Body produto: Produto) : Call<Produto>

    @DELETE("/produtos/{id}")
    fun deleteProduto(@Path("id") id : Int) : Call<Produto>
}