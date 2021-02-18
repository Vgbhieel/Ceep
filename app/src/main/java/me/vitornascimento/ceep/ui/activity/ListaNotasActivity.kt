package me.vitornascimento.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import me.vitornascimento.ceep.dao.NotaDAO
import me.vitornascimento.ceep.databinding.ActivityListaNotasBinding
import me.vitornascimento.ceep.model.Nota
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_REQUISICAO_INSERE_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_RESULTADO_NOTA_CRIADA
import me.vitornascimento.ceep.ui.adapter.ListaNotasAdapter

class ListaNotasActivity : AppCompatActivity() {

    lateinit var binding: ActivityListaNotasBinding
    lateinit var adapter: ListaNotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaNotasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.listaNotasRv

        val todasNotas = NotaDAO.todos()

        configuraBtnInsereNota()

        configuraRecyclerView(todasNotas, recyclerView)
    }

    private fun configuraBtnInsereNota() {
        val btnInsereNota = binding.listaNotasTvInserirNota
        btnInsereNota.setOnClickListener {
            val vaiParaFormularioNotasActivity = Intent(this, FormularioNotaActivity::class.java)
            startActivityForResult(vaiParaFormularioNotasActivity, CODIGO_REQUISICAO_INSERE_NOTA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CODIGO_REQUISICAO_INSERE_NOTA && resultCode == CODIGO_RESULTADO_NOTA_CRIADA && data!!.hasExtra(
                CHAVE_NOTA
            )
        ) {
            val notaRecebida: Nota = data.getParcelableExtra(CHAVE_NOTA)!!
            NotaDAO.insere(notaRecebida)
            adapter.adiciona(notaRecebida)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun configuraRecyclerView(todasNotas: ArrayList<Nota>, recyclerView: RecyclerView) {
        adapter = ListaNotasAdapter(this, todasNotas)
        recyclerView.adapter = adapter
    }
}