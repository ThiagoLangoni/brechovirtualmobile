package br.fiap.trabalhomobile.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.fiap.trabalhomobile.model.Produto
import br.fiap.trabalhomobile.repository.ProdutoRepository


class ListProdutosViewModel (val produtoRepository: ProdutoRepository) : ViewModel() {

    val messageError: MutableLiveData<String> = MutableLiveData()
    val produtos: MutableLiveData<List<Produto>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getProdutos() {
        isLoading.value = true
        produtoRepository.getProdutos(
            {
                produtos.value = it
                messageError.value = ""
                isLoading.value = false
            },
            {
                produtos.value = emptyList()
                messageError.value = it?.message
                isLoading.value = false
            }
        )
    }

    fun deleteProduto(produto : Produto) {
        produtoRepository.deleteProduto(produto, {
            messageError.value = "Dados excluidos com sucesso"
            getProdutos()
        }, {
            messageError.value = it?.message
            isLoading.value = true
        })
    }
}