package com.samuel.alimento.reposity

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samuel.alimento.dataSouce.DataSource
import kotlinx.coroutines.tasks.await

class alimentosReposity{

    private val dataSource = DataSource()
    private val db = FirebaseFirestore.getInstance()

    suspend fun salvarAlimento(Alimento: String, Calorias: Int){
        dataSource.salvarAlimento(Alimento, Calorias)
    }

    suspend fun excluirAlimento(nomeAlimento: String) {
        try {
            // Assumindo que o nome do alimento é único, senão use o ID do documento
            val querySnapshot = db.collection("alimentos")
                .whereEqualTo("nome", nomeAlimento) // Substitua por campo apropriado para buscar o alimento

            val result = querySnapshot.get().await()
            if (!result.isEmpty) {
                for (document in result) {
                    // Excluir o documento do Firestore
                    db.collection("alimentos").document(document.id).delete().await()
                }
            }
        } catch (e: Exception) {
            Log.e("Exclusão", "Erro ao excluir alimento", e)
        }
    }
}
