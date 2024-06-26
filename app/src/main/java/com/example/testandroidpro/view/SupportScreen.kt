package com.example.testandroidpro.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testandroidpro.R
import com.example.testandroidpro.data.CallBackSupport
import com.example.testandroidpro.data.DialogString
import com.example.testandroidpro.data.SupportItem
import com.example.testandroidpro.viewmodel.AdViewModel
import java.util.Calendar

@Composable
fun SupportScreen(navController: NavController, adViewModel: AdViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf(adViewModel.emailDisplay) }
    var name by remember { mutableStateOf(adViewModel.userInfoStore.value.name) }
    var message by remember { mutableStateOf("") }
    val dialogString = remember { mutableStateOf(
        DialogString(
            width = 200.dp,
            height = 150.dp,
            title = "Dialog Title",
            message = "Dialog Message",
            button = "OK",
            show = mutableStateOf(false),
            callback = null
        )
    )
    }
    DialogScreenAsDialog(dialogString.value)
    Scaffold (
        topBar = { TopBar(navController, adViewModel, "support") },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Support",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp)
                )
                Text(
                    text = "Need help? Let’s connect to customer support team",
                    fontSize = 12.sp,
                    lineHeight = 14.sp,

                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 6.dp)
                        .fillMaxWidth(0.6f)
                        .let {
                            if (stringResource(R.string.borderDebug) == "true") it.border(
                                1.dp, Color.Red
                            ) else it
                        }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(0.5f)
                        .background(Color(0xFFFFD700)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Write message",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = stringResource(R.string.email_icon)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.email),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.enter_your_email),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio(3.5f)
           ,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            capitalization = KeyboardCapitalization.None
                        ),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "name"
                            )
                        },
                        label = { Text(
                            text = stringResource(R.string.userName),
                            color = MaterialTheme.colorScheme.primary)
                        },
                        placeholder = { Text(
                            text = "Enter your name",
                            color = MaterialTheme.colorScheme.primary)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio(3.5f)
                ,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            capitalization = KeyboardCapitalization.None
                        ),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
                    )
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        leadingIcon = {
                            Column(
                                verticalArrangement = Arrangement.Top
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = "message"
                                )
}
                        },
                        label = { Text(text = "Message",
                            color = MaterialTheme.colorScheme.primary) },
                        placeholder = { Text(text = "Enter your message",
                            color = MaterialTheme.colorScheme.primary) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio(0.85f)
               ,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            capitalization = KeyboardCapitalization.None
                        ),
                        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(8.dp),
                        onClick = {
                            val currentTime = Calendar.getInstance().time
                            val supportItem = SupportItem(
                                email = email,
                                name = name,
                                message = message,
                                time = currentTime
                            )

                            adViewModel.writeSupportMessage2(
                                supportItem,
                                CallBackSupport(
                                    onSuccess = {
                                        dialogString.value.width = 400.dp
                                        dialogString.value.height = 200.dp
                                        dialogString.value.title =
                                            context.getString(R.string.dialogMessageSuccess)
                                        dialogString.value.message =
                                            context.getString(R.string.dialogMessageSuccessFeedback)
                                        dialogString.value.button = context.getString(R.string.dialogOk)
                                        dialogString.value.callback = {navController.popBackStack()}
                                        dialogString.value.show.value = true
                                    },
                                    onFailure = {
                                        dialogString.value.width = 400.dp
                                        dialogString.value.height = 200.dp
                                        dialogString.value.title =
                                            context.getString(R.string.dialogMessageFailed)
                                        dialogString.value.message =
                                            context.getString(R.string.dialogMessageFailedFeedback)
                                        dialogString.value.button =
                                            context.getString(R.string.dialogBack)
                                        dialogString.value.callback = {}
                                        dialogString.value.show.value = true
                                    }
                                )
                            )
                        },
                    ) {
                        Text(
                            text = "Submit",
)
                    }
                }

            }
        }
    )
}
