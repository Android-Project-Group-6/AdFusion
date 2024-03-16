package com.example.testandroidpro.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.navOptions
import kotlinx.coroutines.launch

class adViewModel: ViewModel()  {
    var email: String by  mutableStateOf("")
    var passWord: String by  mutableStateOf("")
    var userAddress: String by  mutableStateOf("")
    var userPhoneNum: String by  mutableStateOf("")
    var userName: String by  mutableStateOf("")
    var userState: String by  mutableStateOf("")
    val db = Firebase.firestore
    val fAuth = Firebase.auth
    val currentUser = fAuth.currentUser

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