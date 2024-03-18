package com.example.testandroidpro.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.navigation.NavController
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class AdViewModel: ViewModel()  {
    var email: String by  mutableStateOf("")
    var passWord: String by  mutableStateOf("")
    var userAddress: String by  mutableStateOf("")
    var userPhoneNum: String by  mutableStateOf("")
    var userName: String by  mutableStateOf("")
    var userState: String by  mutableStateOf("")

    val fAuth = Firebase.auth
    val currentUser = fAuth.currentUser

    val db = Firebase.firestore
    val dataState = mutableStateOf<List<DocumentSnapshot>>(emptyList())

    val localFilesIcon = mutableListOf<File>()

    init {
        readSuppliersData()
    }
    fun readSuppliersData(){
        db.collection("suppliers")
            .get()
            .addOnSuccessListener { result ->
                dataState.value = result.documents
                Log.d("Read suppliers Data", "success")
                for (document in dataState.value) {
                    Log.d("Read suppliers Data", "${document.id} => ${document.data}")
                }
                readSuppliersResIcon()
            }
            .addOnFailureListener { e ->
                Log.w("Read suppliers Data", "Error adding document", e)
            }
    }

    fun readSuppliersResIcon(){
        val storageReference = FirebaseStorage.getInstance().reference
        var pathReference = storageReference.child("Koko-Suomen-tarjoukset-to-14-3-ke-20-3-05.pdf")

        val pdfFile =   mutableStateOf<File?>(null)
        val loading =   mutableStateOf(true)

        for (document in dataState.value) {
            Log.d("Read suppliers Icon Data", "${document.id} => ${document.data}")
            val icon = document.getString("icon")
            val name = document.id

            if (icon != "") {
                val localFile = File.createTempFile(name, "png")
                localFilesIcon.add(localFile)
                pathReference = storageReference.child(icon ?: "")
                pathReference
                    .getFile(localFile)
                    .addOnSuccessListener {
                        pdfFile.value = localFile
                        loading.value = false
                        Log.d("Read suppliers Icon Data", "success $name")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Read suppliers Icon Data", "Error loading PDF", e)
                    }
            }
            else{
                Log.d("Read suppliers Icon Data", "fail")
            }
        }
        for (file in localFilesIcon) {
            Log.d("Read suppliers Icon Data", "File: ${file.name}")
        }
    }
    fun getLocalFile(iconName: String): File? {
        val file = localFilesIcon.find { it.name.contains(iconName) }
        if (file == null) {
            Log.d("getLocalFile", "No local file found for $iconName")
        }
        else{
            Log.d("getLocalFile", "success found for $iconName")
            val fileContent = file.readText()
            Log.d("FileContent", fileContent)
        }
        return file
    }
    fun userSignup(navController: NavController) {
        if (email.isNotEmpty() && passWord.isNotEmpty()) {
            fAuth.createUserWithEmailAndPassword(email, passWord)
                .addOnSuccessListener {
                    if (currentUser != null) {

                        db.collection("users")
                            .document(currentUser.uid)
                            .collection("inf")
                            .document("subscribe")
                            .set(Myusub(false,false,false,false))
                            .addOnSuccessListener {
                                Log.d("Signup Init Database", "DocumentSnapshot added with ID:")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Signup Init Database", "Error adding document", e)
                            }

                        db.collection("users")
                            .document(currentUser.uid)
                            .collection("inf")
                            .document("details")
                            .set(Myuser(userName,userAddress,userPhoneNum))
                            .addOnSuccessListener {
                                Log.d("Signup Init Database", "DocumentSnapshot added with ID:")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Signup Init Database", "Error adding document", e)
                            }

                        Log.d("signup", currentUser.uid)
                        userState = "Signup success"
                        navController.popBackStack("signup", inclusive = true)
                        navController.popBackStack("login", inclusive = true)
                        navController.navigate("home")
                    }
                }
                .addOnFailureListener {
                    Log.d("Signup", it.message.toString())
                    userState = it.message.toString()
                }
        }
        else
        {
            userState = "Email or Password is empty"
            Log.d("Signup", "Email or Password is empty")
        }
    }

    fun userLogin(navController: NavController) {
        if (email.isNotEmpty() && passWord.isNotEmpty()) {
            fAuth.signInWithEmailAndPassword(email, passWord)
                .addOnSuccessListener {
                    if (currentUser != null) {
                        Log.d("Login", currentUser.uid)
                        userState = "Login success"
                        navController.popBackStack("login", inclusive = true)
                        navController.navigate("home")
                    }
                }
                .addOnFailureListener {
                    Log.d("Login", it.message.toString())
                    userState = "Email or Password is wrong"
                }
        }
        else
        {
            userState = "Email or Password is empty"
            Log.d("Login", "Email or Password is empty")
        }
    }


}