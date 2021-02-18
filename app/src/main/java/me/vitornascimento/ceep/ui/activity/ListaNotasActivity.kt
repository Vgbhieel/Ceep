package me.vitornascimento.ceep.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.vitornascimento.ceep.databinding.ActivityListaNotasBinding

class ListaNotasActivity : AppCompatActivity() {

    lateinit var binding: ActivityListaNotasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaNotasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}