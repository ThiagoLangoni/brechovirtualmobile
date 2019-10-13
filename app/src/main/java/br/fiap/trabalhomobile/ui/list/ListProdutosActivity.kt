package br.fiap.trabalhomobile.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.fiap.trabalhomobile.R
import br.fiap.trabalhomobile.model.Produto
import br.fiap.trabalhomobile.ui.form.FormProdutoActivity
import br.fiap.trabalhomobile.ui.login.LoginActivity
import br.fiap.trabalhomobile.ui.signup.SignUpActivity
import br.fiap.trabalhomobile.ui.sobre.SobreActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_produtos.*
import kotlinx.android.synthetic.main.include_loading.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class ListProdutosActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    val listaProdutosViewModel: ListProdutosViewModel by viewModel()

    val picasso: Picasso by inject()

    var RESULT: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_produtos)

        mAuth = FirebaseAuth.getInstance()

        listaProdutosViewModel.getProdutos()

        listaProdutosViewModel.isLoading.observe(this, Observer {
            if (it == true) {
                containerLoading.visibility = View.VISIBLE
            } else {
                containerLoading.visibility = View.GONE
            }
        })
        listaProdutosViewModel.messageError.observe(this, Observer {
            if (it != "") {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        listaProdutosViewModel.produtos.observe(this, Observer {
            rvProdutos.adapter = ListProdutosAdapter(
                it, picasso,
                {
                    val formActivity = Intent(this, FormProdutoActivity::class.java)
                    formActivity.putExtra("PRODUTO", it)
                    startActivityForResult(formActivity, RESULT)
                },
                {
                    listaProdutosViewModel.deleteProduto(it)
                }
            )

            rvProdutos.layoutManager = GridLayoutManager(this, 3)
        })

        btNovoForm.setOnClickListener {
            val formActivity = Intent(this, FormProdutoActivity::class.java)
            formActivity.putExtra(
                "PRODUTO",
                Produto(id = 0, nome = "", descricao = "", preco = 0.0, imagem = "")
            )

            startActivityForResult(formActivity, RESULT)
        }

        btSobreForm.setOnClickListener {
            startActivity(Intent(this, SobreActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                listaProdutosViewModel.getProdutos()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_logout -> {
                logout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        mAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
