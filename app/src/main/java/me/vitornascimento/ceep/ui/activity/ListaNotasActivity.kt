package me.vitornascimento.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import me.vitornascimento.ceep.dao.NotaDAO
import me.vitornascimento.ceep.databinding.ActivityListaNotasBinding
import me.vitornascimento.ceep.model.Nota
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_REQUISICAO_ALTERA_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_REQUISICAO_INSERE_NOTA
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
        if (data != null) {
            if (requestCode == CODIGO_REQUISICAO_ALTERA_NOTA && data.hasExtra(CHAVE_NOTA)
            ) {
                if (resultCode == RESULT_OK) {
                    alteraNota(data)
                }
            }
        }

    }

    private fun alteraNota(data: Intent) {
        if ((data.getIntExtra("posicao", -1)) >= 0) {
            val notaRecebida: Nota = data.getParcelableExtra(CHAVE_NOTA)!!
            val posicaoRecebida = data.getIntExtra("posicao", -1)
            NotaDAO.altera(posicaoRecebida, notaRecebida)
            adapter.altera(posicaoRecebida, notaRecebida)
        } else {
            Toast.makeText(this, "Ocorreu um erro ao editar a nota.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verificaSeTemNotaCriada(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        if (data != null) {
            if (requestCode == CODIGO_REQUISICAO_INSERE_NOTA && data.hasExtra(CHAVE_NOTA)) {
                if (resultCode == RESULT_OK) {
                    insereNovaNota(data)
                }
            }
        }

    }

    private fun insereNovaNota(data: Intent) {

        val notaRecebida: Nota = data.getParcelableExtra(CHAVE_NOTA)!!
        NotaDAO.insere(notaRecebida)
        adapter.adiciona(notaRecebida)
        Toast.makeText(this, "Nota inserida com sucesso.", Toast.LENGTH_SHORT).show()

    }

    private fun configuraRecyclerView(todasNotas: ArrayList<Nota>, recyclerView: RecyclerView) {

        adapter = ListaNotasAdapter(this, todasNotas, this)
        recyclerView.adapter = adapter

        configuraItemTouchHelper(recyclerView)

    }

    private fun configuraItemTouchHelper(recyclerView: RecyclerView) {

        val itemTouchCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val marcacoesDeDeslize = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                return makeMovementFlags(0, marcacoesDeDeslize)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posicaoElemento = viewHolder.adapterPosition
                NotaDAO.remove(posicaoElemento)
                adapter.remove(posicaoElemento)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
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