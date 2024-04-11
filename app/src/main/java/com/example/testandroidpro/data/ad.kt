package com.example.testandroidpro.data

import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

data class Myusub(
    val P:Boolean,
    val S:Boolean,
    val K:Boolean,
    val L:Boolean
)

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