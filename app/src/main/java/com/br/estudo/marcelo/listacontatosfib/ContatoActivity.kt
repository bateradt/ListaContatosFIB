package com.br.estudo.marcelo.listacontatosfib

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import com.br.estudo.marcelo.bancodadosutil.constantes.dateFormatter
import com.br.estudo.marcelo.bean.Contato
import com.br.estudo.marcelo.repositorio.ContatoRepository
import kotlinx.android.synthetic.main.activity_contato.*
import java.text.SimpleDateFormat
import java.util.*

class ContatoActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    private var vBtnDatanascimento: Button? = null
    private var vImageContato: ImageView? = null
    private var vEdtNome: EditText? = null
    private var vEdtEndereco: EditText? = null
    private var vEdtTelefone: EditText? = null
    private var vEdtSite: EditText? = null
    private var vBtnCadastro: Button? = null
    private var vEdtEmail: EditText? = null
    private var contato: Contato? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        val myChildToolbar = toolbar_child
        setSupportActionBar(myChildToolbar)

        // Get a support ActionBar corresponding to this toolbar
        val ab = supportActionBar

        // Enable the Up button
        ab!!.setDisplayHomeAsUpEnabled(true)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        vBtnDatanascimento = txtDatanascimento
        vBtnDatanascimento!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@ContatoActivity,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        vImageContato = imgContato
        vEdtNome = txtNome
        vEdtEndereco = txtEndereco
        vEdtTelefone = txtTelefone
        vEdtSite = txtSite
        vEdtEmail = txtEmail
        vBtnCadastro = btnCadastro

        vBtnCadastro?.setOnClickListener {
            contato?.nome = txtNome?.text.toString()
            contato?.endereco = txtEndereco?.text.toString()
            contato?.telefone = txtTelefone?.text.toString().toLong()
            val date = dateFormatter.parse(vBtnDatanascimento?.text.toString())
            contato?.dataNascimento = date.time
            contato?.email = txtEmail?.text.toString()
            contato?.site = txtSite?.text.toString()

            if(contato?.id == 0L) {
                ContatoRepository(this).create(contato!!)
            }else{
                ContatoRepository(this).update(contato!!)
            }
            finish()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        vBtnDatanascimento!!.text = sdf.format(cal.time)
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        if(intent != null){
            if(intent.getSerializableExtra("contato") != null){
                contato = intent.getSerializableExtra("contato") as Contato

                txtNome?.setText(contato?.nome)
                txtEndereco?.setText(contato?.endereco)
                txtTelefone.setText(contato?.telefone.toString())

                if (contato?.dataNascimento != null) {
                    vBtnDatanascimento?.text = dateFormatter?.format(Date(contato?.dataNascimento!!))
                }else{
                    vBtnDatanascimento?.text = dateFormatter?.format(Date())
                }
                txtEmail.setText(contato?.email)
                txtSite?.setText(contato?.site)

            }else{
                contato = Contato()
            }
        }

    }


}
