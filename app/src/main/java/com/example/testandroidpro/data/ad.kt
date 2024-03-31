package com.example.testandroidpro.data

import com.google.firebase.firestore.DocumentSnapshot

data class Myusub(
    val P:Boolean,
    val S:Boolean,
    val K:Boolean,
    val L:Boolean
)
data class Myuser(
    val name:String,
    val address:String,
    val phonenum:String
)


data class SupplierAd(
    val supplier: DocumentSnapshot,
    val ads: List<DocumentSnapshot>
)