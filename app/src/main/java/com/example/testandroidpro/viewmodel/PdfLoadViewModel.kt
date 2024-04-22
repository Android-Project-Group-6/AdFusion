package com.example.testandroidpro.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File

class PdfLoadViewModel: ViewModel() {

    var pdfFile = mutableStateOf<File?>(null)
    var pdfLoading = mutableStateOf(true)

    init {
        pdfLoading.value = true
    }

    @Composable
    fun LoadPdfFile(filePath:String){
        Log.d("PdfLoadViewModel",filePath)
        val storageReference = FirebaseStorage.getInstance().reference
        val pathReference = storageReference.child(filePath)
        val localFile = File.createTempFile("tempPdf", "pdf")

        LaunchedEffect(Unit) {
            try {
                pathReference.getFile(localFile).await()
                pdfFile.value = localFile
                pdfLoading.value = false
                Log.d("TestPdf","success")
            } catch (e: Exception) {
                Log.e("TestPdf", "Error loading PDF", e)
            }
        }
    }
}