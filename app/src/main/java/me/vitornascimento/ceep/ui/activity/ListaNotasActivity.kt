package me.vitornascimento.ceep.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        val listaNotas = binding.listaNotasLv


        val dao = NotaDAO
        for (i in 1..10000) {
            dao.insere(Nota("Nota $i", "Descrição $i"))
        }
        val todasNotas = dao.todos()

        listaNotas.adapter = ListaNotasAdapter(this, todasNotas)

    }
}