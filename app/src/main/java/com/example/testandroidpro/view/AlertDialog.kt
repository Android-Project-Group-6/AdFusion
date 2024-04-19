package com.example.testandroidpro.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.testandroidpro.data.DialogString

@Composable
fun DialogScreenAsDialog(dialogString: DialogString) {
    if (dialogString.show.value) {
        Dialog(onDismissRequest = {  }) {
            val shadowSize = 10.dp
            Surface(
                color = Color.Transparent,
                shape = RoundedCornerShape(shadowSize),
                shadowElevation = shadowSize,
                modifier = Modifier
                    .width(dialogString.width)
                    .height(dialogString.height)
            ) {
                Column(
                    modifier = Modifier
                        .width(dialogString.width)
                        .height(dialogString.height)
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = dialogString.title,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = dialogString.message,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    )
                    Button(
                        onClick = {
                            dialogString.show.value = false
                            dialogString.callback?.let { it() }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(dialogString.button)
                    }
                }
            }
        }
    }
}
