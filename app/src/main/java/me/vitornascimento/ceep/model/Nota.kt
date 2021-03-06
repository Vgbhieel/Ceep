package me.vitornascimento.ceep.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nota(val titulo: String, val descricao: String) : Parcelable