package com.samuel.alimento.dataSouce

import android.util.Log
import com.samuel.alimento.ui.model.model
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import kotlinx.coroutines.tasks.await

class DataSource {
    private val db = FirebaseFirestore.getInstance()
    val alimentoCollection = db.collection("alimento")

    // Obter ID máximo
    suspend fun getId(): Int {
        val dados = alimentoCollection.get().await()
        val maxId = dados.documents.mapNotNull {
            it.getLong("Id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    // Salvar alimento
    suspend fun salvarAlimento(Alimento: String, Calorias: Int) {
        val alimentosSalvar = model(
            Id = getId(),
            alimento = Alimento,
            calorias = Calorias,
        )
        try {
            // Aguardar operação de salvamento com `await()`
            alimentoCollection.document().set(alimentosSalvar).await()
            Log.d("Firebase", "Alimento salvo com sucesso.")
        } catch (e: Exception) {
            Log.e("Firebase", "Erro ao salvar alimento: ")
        }
    }
}

