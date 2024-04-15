package com.example.testandroidpro.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.navigation.NavController
import com.example.testandroidpro.data.Myuser
import com.example.testandroidpro.data.SupplierAd
import com.example.testandroidpro.data.SupportItem
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.File

class AdViewModel: ViewModel()  {
    var emailDisplay: String by  mutableStateOf("")
    var currentEmail: String by  mutableStateOf("")

    var userInfoStore = mutableStateOf(Myuser("", "", ""))
    var userState: String by  mutableStateOf("")

    private val fAuth = Firebase.auth
    private val currentUser = fAuth.currentUser
    var startDestination = "login"
    private val db = Firebase.firestore
    private val dataState = mutableStateOf<List<DocumentSnapshot>>(emptyList())

    private val localFilesIcon = mutableStateListOf<File>()
    private val localFilesEnter = mutableStateListOf<File>()
    var filePath = ""
    var market = ""
    val adList = mutableStateOf(emptyList<SupplierAd>())
    private val tasks = ArrayList<Task<QuerySnapshot>>()

    var listState: LazyListState = LazyListState()

    init {
        viewModelScope.launch {
            Log.d("MVVM", "Init")
//            readSuppliersData()
            checkUserLogin()
        }
    }

    private fun readSuppliersData(){
        viewModelScope.launch {
            Log.d("MVVM", "Init readSuppliersData")
            db.collection("suppliers")
                .get()
                .addOnSuccessListener { result ->
                    dataState.value = result.documents
                    Log.d("Read suppliers Data", "success")
                    for (document in dataState.value) {
                        Log.d("Read suppliers Data", "${document.id} => ${document.data}")
                    }
                    val data = mutableListOf<SupplierAd>()
                    for (document in dataState.value) {
                        Log.d("Read Week Data", "Enter")
                        val task = db.collection("suppliers")
                            .document(document.id)
                            .collection("advertisement")
                            .whereEqualTo("status", true)
                            .get()
                        tasks.add(task)
                    }
                    Tasks.whenAllSuccess<QuerySnapshot>(tasks).addOnSuccessListener { results ->
                        results.forEachIndexed { index, querySnapshot ->
                            val ads = querySnapshot.documents
                            Log.d("Read Week DataList", ads.toString())
                            if (index < result.documents.size) {
                                // It's safe to access the element
                                data.add(SupplierAd(result.documents[index], ads))
                            } else {
                                // Handle the case where the index is out of bounds
                                Log.d("Read Week DataList", "Index out of bounds")
                            }
                        }
                        adList.value = data
                        readSuppliersResEnter()
                    }

                    readSuppliersResIcon()
                }
                .addOnFailureListener { e ->
                    Log.w("Read suppliers Data", "Error adding document", e)
                }
        }
    }

    private fun readSuppliersResEnter(){
        viewModelScope.launch {
            val storageReference = FirebaseStorage.getInstance().reference
//            Log.d("readSuppliersResEnter", "Init readSuppliersResEnter")
            for (supplierAd in adList.value) {
                val name = supplierAd.supplier.id
                val enterPoint = supplierAd.ads[0].getString("enterPoint")
                Log.d("readSuppliersResEnter", enterPoint.toString())
                if (enterPoint != "") {
                    val localFile = File.createTempFile(name, "png")

                    val pathReference = storageReference.child(enterPoint ?: "")
                    pathReference
                        .getFile(localFile)
                        .addOnSuccessListener {
                            localFilesEnter.add(localFile)
                            Log.d("readSuppliersResEnter", "success $name")
//                        val fileContent = localFile.readText()
//                        Log.d("FileContent", fileContent)
                        }
                        .addOnFailureListener { e ->
                            Log.e("readSuppliersResEnter", "Error loading PDF", e)
                        }
                } else {
                    Log.d("readSuppliersResEnter", "fail")
                }
            }
            for (file in localFilesEnter) {
                Log.d("readSuppliersResEnter", "File: ${file.name}")
            }
        }
    }

    private fun readSuppliersResIcon(){
        viewModelScope.launch {
            val storageReference = FirebaseStorage.getInstance().reference

            Log.d("MVVM", "Init readSuppliersResIcon")
            for (document in dataState.value) {
                Log.d("Read suppliers Icon Data", "${document.id} => ${document.data}")
                val icon = document.getString("icon")
                val name = document.id

                if (icon != "") {
                    val localFile = File.createTempFile(name, "png")

                    val pathReference = storageReference.child(icon ?: "")
                    pathReference
                        .getFile(localFile)
                        .addOnSuccessListener {
                            localFilesIcon.add(localFile)
                            Log.d("Read suppliers Icon Data", "success $name")
//                        val fileContent = localFile.readText()
//                        Log.d("FileContent", fileContent)
                        }
                        .addOnFailureListener { e ->
                            Log.e("Read suppliers Icon Data", "Error loading PDF", e)
                        }
                } else {
                    Log.d("Read suppliers Icon Data", "fail")
                }
            }
            for (file in localFilesIcon) {
                Log.d("Read suppliers Icon Data", "File: ${file.name}")
            }
        }
    }

    fun getLocalFile(iconName: String): File? {

        val file = localFilesIcon.find { it.name.contains(iconName) }
        if (file == null) {
            Log.d("getLocalFile", "No local file found for $iconName")
        } else {
            Log.d("getLocalFile", "success found for $iconName")
//            val fileContent = file.readText()
//            Log.d("FileContent", fileContent)
        }
        return file

    }

    fun getLocalFileEnter(iconName: String): File? {

        val file = localFilesEnter.find { it.name.contains(iconName) }
        if (file == null) {
            Log.d("getLocalFileEnter", "No local file found for $iconName")
        } else {
            Log.d("getLocalFileEnter", "success found for $iconName")
//            val fileContent = file.readText()
//            Log.d("FileContent", fileContent)
        }
        return file

    }

    fun modifyInfo(userInfo:Myuser, callback: (String) -> Unit) {
        viewModelScope.launch {
            val currentUser = fAuth.currentUser
            if (currentUser != null) {

                db.collection("users")
                    .document(currentUser.uid)
                    .collection("inf")
                    .document("details")
                    .set(userInfo)
                    .addOnSuccessListener {
                        userInfoStore.value = userInfo
                        Log.d("Signup Init Database", "DocumentSnapshot added with ID:")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Signup Init Database", "Error adding document", e)
                    }

                Log.d("signup", currentUser.uid)
                userState = "Modify success"
                callback("Modify success")
//            navController.popBackStack("signup", inclusive = true)
//            navController.popBackStack("login", inclusive = true)
//            navController.navigate("home")
            }
        }
    }

    fun userSignup(email:String, passWord1:String, passWord2:String, userInfo:Myuser,callback: (String) -> Unit) {
        viewModelScope.launch {
            if(passWord1 == passWord2) {
                if (email.isNotEmpty() && passWord1.isNotEmpty()) {
                    fAuth.createUserWithEmailAndPassword(email, passWord1)
                        .addOnSuccessListener {
                            val currentUser = fAuth.currentUser
                            if (currentUser != null) {
                                db.collection("users")
                                    .document(currentUser.uid)
                                    .collection("inf")
                                    .document("details")
                                    .set(userInfo)
                                    .addOnSuccessListener {
                                        Log.d(
                                            "Signup Init Database",
                                            "DocumentSnapshot added with ID:"
                                        )
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("Signup Init Database", "Error adding document", e)
                                    }
                                Log.d("signup", currentUser.uid)

                                userState = "Signup success"

                                userInfoStore.value = userInfo

                                currentEmail = currentUser.email.toString()

                                readSuppliersData()

//                                navController.popBackStack("signup", inclusive = true)
//                                navController.popBackStack("login", inclusive = true)
//                                navController.navigate("home")

                                callback("Signup success")

                            }
                        }
                        .addOnFailureListener {
                            Log.d("Signup", it.message.toString())
                            userState = it.message.toString()
                            callback(it.message.toString())
                        }
                } else {
                    userState = "Email or Password is empty"
                    Log.d("Signup", "Email or Password is empty")
                    callback("Email or Password is empty")
                }
            } else {
                callback("Two passwords don't match")
            }
        }
    }

    fun userLogin(email:String, passWord:String, callback: (String) -> Unit) {
        viewModelScope.launch {
            if (email.isNotEmpty() && passWord.isNotEmpty()) {
                fAuth.signInWithEmailAndPassword(email, passWord)
                    .addOnSuccessListener {
                        val currentUser = fAuth.currentUser
                        if (currentUser != null) {
                            db.collection("users")
                                .document(currentUser.uid)
                                .collection("inf")
                                .document("details")
                                .get()
                                .addOnSuccessListener { document ->
                                    userInfoStore.value = Myuser(
                                        name = document.getString("name").toString(),
                                        address = document.getString("address").toString(),
                                        phonenum = document.getString("phonenum").toString()
                                    )
                                    Log.d("userLogin", "DocumentSnapshot added with ID:")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("userLogin", "Error adding document", e)
                                }

                            Log.d("userLogin", currentUser.uid)
                            userState = "Login success"
                            currentEmail = currentUser.email.toString()
//                            navController.popBackStack("login", inclusive = true)
//                            navController.navigate("home")
                            readSuppliersData()
                            callback("Login success")
                        }
                    }
                    .addOnFailureListener {
                        Log.d("userLogin", it.message.toString())
                        userState = "Email or Password is wrong"
                        callback("Email or Password is wrong")
                    }
            } else {
                userState = "Email or Password is empty"
                Log.d("Login", "Email or Password is empty")
                callback("Email or Password is empty")
            }
        }
    }

    fun userSignOut(navController: NavController) {
        viewModelScope.launch {
            emailDisplay = ""

            userState = ""
            userInfoStore.value.name = ""
            userInfoStore.value.phonenum = ""
            userInfoStore.value.address = ""

            fAuth.signOut()
            navController.navigate("login")
        }
    }

    private fun checkUserLogin(){
        viewModelScope.launch {
            startDestination = if (currentUser != null) "home" else "login"
            if (currentUser != null) {
                currentEmail = currentUser.email.toString()
                db.collection("users")
                    .document(currentUser.uid)
                    .collection("inf")
                    .document("details")
                    .get()
                    .addOnSuccessListener { document ->
                        userInfoStore.value = Myuser(
                            name = document.getString("name").toString(),
                            address = document.getString("address").toString(),
                            phonenum = document.getString("phonenum").toString()
                        )
                        Log.d("checkUserLogin", currentUser.uid)
//                        Log.d("checkUserLogin", userInfoStore.address)
//                        Log.d("checkUserLogin", userInfoStore.phonenum)
//                        Log.d("checkUserLogin", userInfoStore.name)
                    }
                    .addOnFailureListener { e ->
                        Log.w("checkUserLogin", "Error adding document", e)

                    }
                readSuppliersData()
            }
        }
    }

    fun forgotPassword(email:String, callback: (String) -> Unit){
        viewModelScope.launch {
            if (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                fAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            userState = "Email sent"
                            Log.d("forgotPassword", "Email sent.")
                            Log.d("forgotPassword", email)

                            callback("Email sent")
                        }
                    }
            }
            else {
                userState = "Error Email"
                callback("Error Email")
            }
        }
    }

    fun resetPassword(opw:String, npw1:String, npw2:String, callback: (String) -> Unit){
        viewModelScope.launch {
            if(npw1 == npw2) {
                fAuth.signInWithEmailAndPassword(currentEmail, opw)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("resetPassword", "signInWithEmail:success")
                            val user = fAuth.currentUser
                            // User can now enter a new password
                            user?.updatePassword(npw1)?.addOnCompleteListener { tasking ->
                                if (tasking.isSuccessful) {
                                    Log.d("resetPassword", "User password updated.")
                                    callback("Modify success, Please reload")
//                                    userSignOut(navController)
                                    emailDisplay = currentEmail
                                } else {
                                    Log.d("resetPassword", tasking.exception?.localizedMessage.toString())
                                    callback(tasking.exception?.localizedMessage.toString())
                                }
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("resetPassword", "signInWithEmail:failure", task.exception)
                            // Incorrect current password, do not allow password change
                            callback("Password is incorrect")
                        }
                    }
            } else {
                userState = "Two passwords don't match"
                callback("Two passwords don't match")
            }
        }
    }


//    fun writeSupportMessage(navController: NavController, supportMessage: SupportItem,context: Context){
//        viewModelScope.launch {
//
//            db.collection("support")
//                .add(supportMessage)
//                .addOnSuccessListener {
//                    Log.d("writeSupportMessage", "Success")
//                    showAlertDialog(context,"Support","Success","Confirm") {
//                        navController.popBackStack()
//                    }
//                }
//                .addOnFailureListener { e ->
//                    Log.w("writeSupportMessage", "Fail", e)
//                    showAlertDialog(context,"Support","Failed","Confirm") { }
//                }
//        }
//    }
    fun writeSupportMessage2(supportMessage: SupportItem, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
//            val documentId = supportMessage.email
            db.collection("support")
//                .document(documentId).set
                .add(supportMessage)
                .addOnSuccessListener {
                    Log.d("writeSupportMessage", "Success")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    Log.w("writeSupportMessage", "Fail", e)
                    callback(false)
                }
        }
    }
}