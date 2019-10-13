package br.fiap.trabalhomobile.repository

import br.fiap.trabalhomobile.model.Produto

interface ProdutoRepository {

    fun getProdutos(
        onComplete: (List<Produto>?) -> Unit,
        onError: (Throwable?) -> Unit
    )

    fun createProduto(
        produto: Produto,
        onComplete: (Produto?) -> Unit,
        onError: (Throwable?) -> Unit
    )

    fun updateProduto(
        produto: Produto,
        onComplete: (Produto?) -> Unit,
        onError: (Throwable?) -> Unit
    )

    fun deleteProduto(
        produto: Produto,
        onComplete: (Produto?) -> Unit,
        onError: (Throwable?) -> Unit
    )

}