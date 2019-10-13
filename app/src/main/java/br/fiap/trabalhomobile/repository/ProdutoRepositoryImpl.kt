package br.fiap.trabalhomobile.repository

import br.fiap.trabalhomobile.api.ProdutoService
import br.fiap.trabalhomobile.model.Produto
import br.fiap.trabalhomobile.model.ProdutoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdutoRepositoryImpl(val produtoService: ProdutoService) :
    ProdutoRepository {

    override fun getProdutos(
        onComplete: (List<Produto>?) -> Unit,
        onError: (Throwable?) -> Unit
    ) {

        produtoService.getProdutos()
            .enqueue(object : Callback<ProdutoResponse> {
                override fun onFailure(call: Call<ProdutoResponse>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<ProdutoResponse>, response: Response<ProdutoResponse>) {
                    if (response.isSuccessful) {
                        onComplete(response.body()?.produtos)
                    } else {
                        onError(Throwable("Não foi possível carregar os produtos"))
                    }
                }
            })
    }

    override fun createProduto(produto: Produto, onComplete: (Produto?) -> Unit, onError: (Throwable?) -> Unit) {

        produtoService.createProduto(produto)
            .enqueue(object : Callback<Produto> {
                override fun onFailure(call: Call<Produto>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable("Erro na criação do produto :("))
                    }
                }
            })
    }

    override fun updateProduto(produto: Produto, onComplete: (Produto?) -> Unit, onError: (Throwable?) -> Unit) {

        produtoService.updateProduto(produto.id, produto)
            .enqueue(object : Callback<Produto> {
                override fun onFailure(call: Call<Produto>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable("Erro na atualização do produto:("))
                    }
                }
            })

    }

    override fun deleteProduto(produto: Produto, onComplete: (Produto?) -> Unit, onError: (Throwable?) -> Unit) {

        produtoService.deleteProduto(produto.id)
            .enqueue(object : Callback<Produto> {
                override fun onFailure(call: Call<Produto>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable("Erro na exclusão do produto :("))
                    }
                }
            })

    }

}