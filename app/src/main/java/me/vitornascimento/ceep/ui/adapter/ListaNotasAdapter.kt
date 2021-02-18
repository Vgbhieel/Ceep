package me.vitornascimento.ceep.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.vitornascimento.ceep.R
import me.vitornascimento.ceep.model.Nota

class ListaNotasAdapter(private val context: Context, private val notas: List<Nota>) :
    RecyclerView.Adapter<ListaNotasAdapter.ListaNotasViewHolder>() {

    class ListaNotasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.item_nota_tv_titulo)
        val tvDescricao: TextView = view.findViewById(R.id.item_nota_tv_descricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaNotasViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        return ListaNotasViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ListaNotasViewHolder, position: Int) {
        val nota = notas[position]

        holder.tvTitulo.text = nota.titulo
        holder.tvDescricao.text = nota.descricao
    }

    override fun getItemCount() = notas.size
}