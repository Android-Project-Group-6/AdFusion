package com.example.testandroidpro.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File

class PdfLoadViewModel(): ViewModel() {

    var pdfFile = mutableStateOf<File?>(null)
    var pdfLoading = mutableStateOf(true)

    init {
        pdfLoading.value = true
//        loadPdfFile(filePath)
    }

    @Composable
    fun loadPdfFile(filePath:String){
        Log.d("PdfLoadViewModel",filePath)
//        val localFile = File.createTempFile("tempPdf", "pdf")
//        val storageReference = FirebaseStorage.getInstance().reference
//        val pathReference = storageReference.child(filePath)
//        pdfLoading.value = true
//        pathReference
//            .getFile(localFile)
//            .addOnSuccessListener {
//                pdfFile.value = localFile
//                pdfLoading.value = false
//                Log.d("Read suppliers Pdf Data", "success $filePath")
//                Log.d("Read suppliers Pdf Data", "success ${pdfLoading.value}")
////                val fileContent = localFile.readText()
////                Log.d("FileContent", fileContent)
//            }
//            .addOnFailureListener { e ->
//                Log.e("Read suppliers Pdf Data", "Error loading PDF", e)
//            }
        val storageReference = FirebaseStorage.getInstance().reference
        val pathReference = storageReference.child(filePath)
        val localFile = File.createTempFile("tempPdf", "pdf")

        LaunchedEffect(Unit) {
            try {
                pathReference.getFile(localFile).await() // 等待文件下载完成
                pdfFile.value = localFile
                pdfLoading.value = false
                Log.d("TestPdf","success")
            } catch (e: Exception) {
                Log.e("TestPdf", "Error loading PDF", e)
                // 处理加载PDF文件时的错误
            }
        }
    }
}