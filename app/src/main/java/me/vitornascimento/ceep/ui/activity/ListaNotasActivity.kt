package me.vitornascimento.ceep.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import me.vitornascimento.ceep.dao.NotaDAO
import me.vitornascimento.ceep.databinding.ActivityListaNotasBinding
import me.vitornascimento.ceep.model.Nota
import me.vitornascimento.ceep.ui.adapter.ListaNotasAdapter

class ListaNotasActivity : AppCompatActivity() {

    lateinit var binding: ActivityListaNotasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaNotasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.listaNotasRv


        val dao = NotaDAO

        dao.insere(Nota("Nota 1", "Descrição 1"))
        dao.insere(Nota("Nota 2", "Descrição 2"))

        val todasNotas = dao.todos()

        configuraRecyclerView(todasNotas, recyclerView)

    }

    private fun configuraRecyclerView(todasNotas: List<Nota>, recyclerView: RecyclerView) {
        val adapter = ListaNotasAdapter(this, todasNotas)
        recyclerView.adapter = adapter
    }
}