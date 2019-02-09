package com.br.estudo.marcelo.listacontatosfib

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.br.estudo.marcelo.bean.Contato
import kotlinx.android.synthetic.main.linha.view.*
import android.view.*

class ListaRecyclerAdapter(private val contatos: List<Contato>, val clickListener: (Contato) -> Unit,
                           val longClickListener: (Contato) -> Boolean,
                           private val context: Context) : RecyclerView.Adapter<ListaRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contato = contatos[position]
        holder?.let {
            it.bindView(contato, clickListener, longClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.linha, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contatos.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(contato : Contato, clickListener: (Contato) -> Unit, longClickListener: (Contato) -> Boolean) {
            val nome = itemView.txtNomeRecycler
            nome.text = contato.nome
            itemView.setOnClickListener{clickListener(contato)}
            itemView.setOnLongClickListener { longClickListener(contato) }
        }
    }
}

