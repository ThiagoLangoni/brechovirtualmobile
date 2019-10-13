package br.fiap.trabalhomobile.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.trabalhomobile.R
import br.fiap.trabalhomobile.model.Produto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.produtos_list_item.view.*

class ListProdutosAdapter(
    val produtos: List<Produto>,
    val picasso: Picasso,
    val clickListener: (Produto) -> Unit,
    val clickListenerExcluir: (Produto) -> Unit
) : RecyclerView.Adapter<ListProdutosAdapter.ProdutoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.produtos_list_item, parent, false)
        return ProdutoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.bindView(produto, picasso, clickListener, clickListenerExcluir)
    }

    class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(
            produto: Produto,
            picasso: Picasso,
            clickListener: (Produto) -> Unit, clickListenerExcluir: (Produto) -> Unit
        ) = with(itemView) {
            tvProdutoNome.text = produto.nome
            tvProdutoPreco.text = produto.preco.toString()

            ivProduto.setOnClickListener { clickListener(produto) }
            btExcluirForm.setOnClickListener { clickListenerExcluir(produto) }
        }
    }
}