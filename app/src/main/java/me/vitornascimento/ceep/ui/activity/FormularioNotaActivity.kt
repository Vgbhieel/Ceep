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
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_POSICAO
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_RESULTADO_NOTA_ALTERADA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CODIGO_RESULTADO_NOTA_CRIADA
import kotlin.properties.Delegates

class FormularioNotaActivity : AppCompatActivity() {

    lateinit var binding: ActivityFormularioNotaBinding
    var posicaoRecebida by Delegates.notNull<Int>()
    var ehEdicao = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioNotaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (intent.hasExtra(CHAVE_NOTA) && intent.hasExtra(CHAVE_POSICAO)) {
            ehEdicao = true
            val notaRecebida = intent.getParcelableExtra<Nota>(CHAVE_NOTA) as Nota
            posicaoRecebida = intent.getIntExtra(CHAVE_POSICAO, -1)
            binding.formularioNotaEtTitulo.setText(notaRecebida.titulo)
            binding.formularioNotaEtDescricao.setText(notaRecebida.descricao)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_nota, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_formulario_nota_ic_salva) {
            if (!ehEdicao) {
                val notaCriada = criaNovaNota()
                retornaNovaNota(notaCriada)
                finish()
            } else {
                val notaAlterada = criaNovaNota()
                retornaNotaAlterada(notaAlterada)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun retornaNovaNota(notaCriada: Nota) {
        val resultadoInsercao = Intent()
        resultadoInsercao.putExtra(CHAVE_NOTA, notaCriada)
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao)
    }

    private fun retornaNotaAlterada(notaAlterada: Nota) {
        val resultadoAlteracao = Intent()
        resultadoAlteracao.putExtra(CHAVE_NOTA, notaAlterada)
        resultadoAlteracao.putExtra(CHAVE_POSICAO, posicaoRecebida)
        setResult(CODIGO_RESULTADO_NOTA_ALTERADA, resultadoAlteracao)
    }

    private fun criaNovaNota(): Nota {
        val etTitulo = binding.formularioNotaEtTitulo.text.toString()
        val etDescricao = binding.formularioNotaEtDescricao.text.toString()
        val notaCriada = Nota(etTitulo, etDescricao)
        return notaCriada
    }
}