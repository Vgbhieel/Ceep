package me.vitornascimento.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.vitornascimento.ceep.R
import me.vitornascimento.ceep.databinding.ActivityFormularioNotaBinding
import me.vitornascimento.ceep.model.Nota
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_NOTA
import me.vitornascimento.ceep.ui.activity.NotaActivityConstantes.Companion.CHAVE_POSICAO
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

        verificaExtrasParaEdicao()

    }

    private fun verificaExtrasParaEdicao() {

        if (intent.hasExtra(CHAVE_NOTA)) {
            configuraEdicao()
            preencheCampos()
        }

    }

    private fun preencheCampos() {

        val notaRecebida = intent.getParcelableExtra<Nota>(CHAVE_NOTA) as Nota
        posicaoRecebida = intent.getIntExtra(CHAVE_POSICAO, -1)
        binding.formularioNotaEtTitulo.setText(notaRecebida.titulo)
        binding.formularioNotaEtDescricao.setText(notaRecebida.descricao)

    }

    private fun configuraEdicao() {

        ehEdicao = true

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_formulario_nota, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_formulario_nota_ic_salva) {
            if (camposPreenchidos()) {

                if (ehEdicao) {

                    val notaAlterada = criaNovaNota()
                    retornaNotaAlterada(notaAlterada)
                    finish()

                } else {

                    val notaCriada = criaNovaNota()
                    retornaNovaNota(notaCriada)
                    finish()

                }

            } else {

                Toast.makeText(
                    this,
                    "É obrigatório preencher um título e uma descrição.",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }

        }

        return super.onOptionsItemSelected(item)

    }

    private fun camposPreenchidos() =
        binding.formularioNotaEtDescricao.text.isNotBlank() && binding.formularioNotaEtTitulo.text.isNotBlank()

    private fun retornaNovaNota(notaCriada: Nota) {
        val resultadoInsercao = Intent()
        resultadoInsercao.putExtra(CHAVE_NOTA, notaCriada)
        setResult(RESULT_OK, resultadoInsercao)
    }

    private fun retornaNotaAlterada(notaAlterada: Nota) {
        val resultadoAlteracao = Intent()
        resultadoAlteracao.putExtra(CHAVE_NOTA, notaAlterada)
        resultadoAlteracao.putExtra(CHAVE_POSICAO, posicaoRecebida)
        setResult(RESULT_OK, resultadoAlteracao)
    }

    private fun criaNovaNota(): Nota {
        val etTitulo = binding.formularioNotaEtTitulo.text.toString()
        val etDescricao = binding.formularioNotaEtDescricao.text.toString()
        val notaCriada = Nota(etTitulo, etDescricao)
        return notaCriada
    }

}