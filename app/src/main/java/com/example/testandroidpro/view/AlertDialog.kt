package com.example.testandroidpro.view

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.testandroidpro.R
import com.example.testandroidpro.viewmodel.AdViewModel

fun showAlertDialog(context: Context, title: String, message: String, buttonText: String, onClick: () -> Unit) {
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder.setTitle(title)
    alertDialogBuilder.setMessage(message)
    alertDialogBuilder.setPositiveButton(buttonText) { dialog, which ->
        onClick.invoke()
    }
    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()
}
@Composable
fun DialogScreenAsDialog(showDialog: MutableState<Boolean>, onClick: () -> Unit) {
    if (showDialog.value) {
        Dialog(onDismissRequest = {  }) {

            Column(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    "Title",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "Message",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        showDialog.value = false
                        onClick()
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("OK")
                }
            }
        }
    }
}
