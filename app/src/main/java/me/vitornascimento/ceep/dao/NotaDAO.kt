package me.vitornascimento.ceep.dao

import me.vitornascimento.ceep.model.Nota
import java.util.*

class NotaDAO {
    companion object {

        private val notas: ArrayList<Nota> = ArrayList()

        fun todos(): List<Nota> {
            return notas.clone() as List<Nota>
        }

        fun insere(vararg notas: Nota) {
            Companion.notas.addAll(listOf(*notas))
        }

        fun altera(posicao: Int, nota: Nota) {
            notas[posicao] = nota
        }

        fun remove(posicao: Int) {
            notas.removeAt(posicao)
        }

        fun troca(posicaoInicio: Int, posicaoFim: Int) {
            Collections.swap(notas, posicaoInicio, posicaoFim)
        }

        fun removeTodos() {
            notas.clear()
        }
    }
}