package com.example.testandroidpro.data

import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date


data class Myuser(
    var name:String,
    var address:String,
    var phonenum:String
)

data class SupplierAd(
    val supplier: DocumentSnapshot,
    val ads: List<DocumentSnapshot>
)

data class SupportItem(
    val email: String,
    val name: String,
    val message: String,
    val time: Date,
)

data class DialogString(
    var width: Dp,
    var height: Dp,
    var title: String,
    var message: String,
    var button: String,
    var show: MutableState<Boolean>,
    var callback: (() -> Unit)? = null
)



data class CallBackSupport(
    val onSuccess: () -> Unit,
    val onFailure: () -> Unit,
)

data class CallBackReset(
    val onSuccess: () -> Unit,
    val onSystemError: (String) -> Unit,
    val onIncorrectPassword: () -> Unit,
    val onPasswordMismatch: () -> Unit,
)

data class CallBackForgot(
    val onSuccess: () -> Unit,
    val onFailure: () -> Unit,
)

data class CallBackUserLogin(
    val onSuccess: () -> Unit,
    val onEmailPwError: () -> Unit,
    val onEmailPwEmpty: () -> Unit,
)

data class CallBackUserSignup(
    val onSuccess: () -> Unit,
    val onSystemError: (String) -> Unit,
    val onEmailPwEmpty: () -> Unit,
    val onPwMismatch: () -> Unit,
)
data class CallBackModifyInfo(
    val onSuccess: () -> Unit,
)