package me.vitornascimento.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import me.vitornascimento.ceep.dao.NotaDAO
import me.vitornascimento.ceep.databinding.ActivityListaNotasBinding
import me.vitornascimento.ceep.model.Nota
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_POSICAO
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_REQUISICAO_ALTERA_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_REQUISICAO_INSERE_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_RESULTADO_NOTA_ALTERADA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_RESULTADO_NOTA_CRIADA
import me.vitornascimento.ceep.ui.adapter.ListaNotasAdapter

class ListaNotasActivity : AppCompatActivity(), ListaNotasAdapter.OnItemClickListener {

    lateinit var binding: ActivityListaNotasBinding
    lateinit var adapter: ListaNotasAdapter
    lateinit var todasNotas: ArrayList<Nota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaNotasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.listaNotasRv

        for (i in 1..10) {
            NotaDAO.insere(Nota("Título $i", "Descrição $i"))
        }
        todasNotas = NotaDAO.todos()

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

        verificaSeTemNotaCriada(requestCode, resultCode, data)

        verificaSeTemNotaAlterada(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun verificaSeTemNotaAlterada(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == CODIGO_REQUISICAO_ALTERA_NOTA && resultCode == CODIGO_RESULTADO_NOTA_ALTERADA && data!!.hasExtra(
                CHAVE_NOTA
            ) && data.hasExtra(CHAVE_POSICAO)
        ) {
            alteraNota(data)
        }
    }

    private fun alteraNota(data: Intent) {
        val notaRecebida: Nota = data.getParcelableExtra(CHAVE_NOTA)!!
        val posicaoRecebida = data.getIntExtra("posicao", -1)
        NotaDAO.altera(posicaoRecebida, notaRecebida)
        adapter.altera(posicaoRecebida, notaRecebida)
    }

    private fun verificaSeTemNotaCriada(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == CODIGO_REQUISICAO_INSERE_NOTA && resultCode == CODIGO_RESULTADO_NOTA_CRIADA && data!!.hasExtra(
                CHAVE_NOTA
            )
        ) {
            insereNovaNota(data)
        }
    }

    private fun insereNovaNota(data: Intent) {
        val notaRecebida: Nota = data.getParcelableExtra(CHAVE_NOTA)!!
        NotaDAO.insere(notaRecebida)
        adapter.adiciona(notaRecebida)
    }

    private fun configuraRecyclerView(todasNotas: ArrayList<Nota>, recyclerView: RecyclerView) {
        adapter = ListaNotasAdapter(this, todasNotas, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val notaClicada = todasNotas[position]
        val abreFormularioComNota =
            Intent(this, FormularioNotaActivity::class.java)
        abreFormularioComNota.putExtra(CHAVE_NOTA, notaClicada)
        abreFormularioComNota.putExtra("posicao", position)
        startActivityForResult(abreFormularioComNota, 2)
    }
}