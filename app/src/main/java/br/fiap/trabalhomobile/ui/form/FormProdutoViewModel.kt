package br.fiap.trabalhomobile.ui.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.fiap.trabalhomobile.model.Produto
import br.fiap.trabalhomobile.repository.ProdutoRepository

class FormProdutoViewModel (
    val produtoRepository: ProdutoRepository
) : ViewModel() {

    val messageResponse = MutableLiveData<String>()
    val sucessoProcessamento : MutableLiveData<Boolean> = MutableLiveData()

    fun createProduto(produto : Produto) {
        produtoRepository.createProduto(produto, {
            messageResponse.value = "Dados gravado com sucesso"
            sucessoProcessamento.value = true
        }, {
            messageResponse.value = it?.message
            sucessoProcessamento.value = false
        })
    }

    fun updateProduto(produto : Produto) {
        produtoRepository.updateProduto(produto, {
            messageResponse.value = "Dados gravado com sucesso"
            sucessoProcessamento.value = true
        }, {
            messageResponse.value = it?.message
            sucessoProcessamento.value = false
        })
    }

    fun deleteProduto(produto : Produto) {
        produtoRepository.deleteProduto(produto, {
            messageResponse.value = "Dados excluidos com sucesso"
            sucessoProcessamento.value = true
        }, {
            messageResponse.value = it?.message
            sucessoProcessamento.value = true
        })
    }
}