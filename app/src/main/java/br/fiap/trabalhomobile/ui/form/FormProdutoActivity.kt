package br.fiap.trabalhomobile.ui.form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.fiap.trabalhomobile.R
import br.fiap.trabalhomobile.model.Produto
import kotlinx.android.synthetic.main.activity_form_produto.*
import org.koin.android.viewmodel.ext.android.viewModel


class FormProdutoActivity : AppCompatActivity() {

    val formProdutoViewModel: FormProdutoViewModel by viewModel()

    lateinit var produto: Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_produto)
        setValues()

        formProdutoViewModel.messageResponse.observe(this, Observer {
            if (it != "") {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        formProdutoViewModel.sucessoProcessamento.observe(this, Observer {
            if (it == true) {
                refreshListProducts()
            }
        })

        btSalvarForm.setOnClickListener {
            val produtoForm: Produto = retornaProdutoAlterado()

            if (produtoForm.id == 0)
                formProdutoViewModel.createProduto(produtoForm)
            else
                formProdutoViewModel.updateProduto(produtoForm)
        }

        btExcluirForm.setOnClickListener {
            formProdutoViewModel.deleteProduto(produto)
        }
    }

    private fun retornaProdutoAlterado(): Produto = Produto(
        id = produto.id,
        nome = txInputNameForm.text.toString(),
        descricao = txInputDescricaoForm.text.toString(),
        preco = txInputPrecoForm.text.toString().toDouble(),
        imagem = ""
    )

    private fun setValues() {
        produto = intent.getParcelableExtra("PRODUTO")

        if (produto.id == 0)
            btExcluirForm.visibility = View.GONE
        else
            btExcluirForm.visibility = View.VISIBLE

        txInputNameForm.setText(produto.nome)
        txInputDescricaoForm.setText(produto.descricao)
        txInputPrecoForm.setText(produto.preco.toString())
    }

    private fun refreshListProducts() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
