package me.vitornascimento.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import me.vitornascimento.ceep.R
import me.vitornascimento.ceep.databinding.ActivityFormularioNotaBinding
import me.vitornascimento.ceep.model.Nota
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_RESULTADO_NOTA_CRIADA

class FormularioNotaActivity : AppCompatActivity() {

    lateinit var binding: ActivityFormularioNotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioNotaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_nota, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_formulario_nota_ic_salva) {
            val notaCriada = criaNovaNota()
            retornaNovaNota(notaCriada)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun retornaNovaNota(notaCriada: Nota) {
        val resultadoInsercao = Intent().putExtra(CHAVE_NOTA, notaCriada)
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao)
    }

    private fun criaNovaNota(): Nota {
        val etTitulo = binding.formularioNotaEtTitulo.text.toString()
        val etDescricao = binding.formularioNotaEtDescricao.text.toString()
        val notaCriada = Nota(etTitulo, etDescricao)
        return notaCriada
    }
}