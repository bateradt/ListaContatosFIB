package com.br.estudo.marcelo.listacontatosfib

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ContextMenu
import kotlinx.android.synthetic.main.activity_lista_contatos.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.br.estudo.marcelo.bean.Contato
import com.br.estudo.marcelo.repositorio.ContatoRepository

class ListaContatosActivity : AppCompatActivity() {

    private var contatos:ArrayList<Contato>? = null
    private var contatoSelecionado: Contato? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_contatos);

        val myToolbar = toolbar
        myToolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(myToolbar)

        //val contatos = arrayOf("Maria", "José", "Carlos")
        //val adapter
        //        = ArrayAdapter(this, android.R.layout.simple_list_item_1, contatos)

        //var listaContatos = lista
        //listaContatos.setAdapter(adapter);

        //contatos = ContatoRepository(this).findAll()
        //val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, contatos)
        //lista?.adapter = adapter
        //adapter.notifyDataSetChanged()

        carregaLista();

        /*//lista.
        contato_list_recyclerview.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(this@ListaContatosActivity, ContatoActivity::class.java)
            intent.putExtra("contato", contatos?.get(position))
            startActivity(intent)
        }

        contato_list_recyclerview.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapter, view, posicao, id ->
            contatoSelecionado = contatos?.get(posicao)
            false
        }*/
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        menuInflater.inflate(R.menu.menu_contato_contexto, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.excluir -> {
                AlertDialog.Builder(this@ListaContatosActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar ?")
                        .setPositiveButton("Quero",
                                DialogInterface.OnClickListener { dialog, which ->
                                    ContatoRepository(this).delete(this.contatoSelecionado!!.id)
                                    carregaLista()
                                }).setNegativeButton("Nao", null).show()
                return false
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun carregaLista() {
        contatos = ContatoRepository(this).findAll()
        /*
        val recyclerView = contato_list_recyclerview
        recyclerView.adapter = ListaRecyclerAdapter(contatos!!,
                { contato : Contato -> contatoClicked(contato) },
                { contato : Contato -> contatoLongClicked(contato) },
                this)
        //val layoutManager = LinearLayoutManager(this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager*/

        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, contatos)
        lista?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.novo -> {
                //Toast.makeText(this, "Novo", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ContatoActivity::class.java)
                startActivity(intent)
                return false
            }

            R.id.sincronizar -> {
                Toast.makeText(this, "Enviar", Toast.LENGTH_LONG).show()
                return false
            }

            R.id.receber -> {
                Toast.makeText(this, "Receber", Toast.LENGTH_LONG).show()
                return false
            }

            R.id.mapa -> {
                Toast.makeText(this, "Mapa", Toast.LENGTH_LONG).show()
                return false
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        //carregaLista()
        registerForContextMenu(lista)
        contatos = ContatoRepository(this).findAll()
        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, contatos)
        lista?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun contatoClicked(contato : Contato) {
        val intent = Intent(this@ListaContatosActivity, ContatoActivity::class.java)
        intent.putExtra("contato", contato)
        startActivity(intent)
    }

    private fun contatoLongClicked(contato: Contato): Boolean {
        /*contatoSelecionado = contato
        //Toast.makeText(this, "CLick longo. Nome: " + contato?.nome, Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this@ListaContatosActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deletar")
                .setMessage("Deseja mesmo deletar ?")
                .setPositiveButton("Quero",
                        DialogInterface.OnClickListener { dialog, which ->
                            ContatoRepository(this).delete(this.contatoSelecionado!!.id)
                            carregaLista()
                        }).setNegativeButton("Nao", null).show()
        return true}*/

        return true;
    }


}

