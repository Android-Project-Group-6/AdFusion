package com.example.testandroidpro.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testandroidpro.R
import com.example.testandroidpro.data.*
import com.example.testandroidpro.viewmodel.AdViewModel

@Composable
fun InfoScreen(navController: NavController, adViewModel: AdViewModel) {
    val context = LocalContext.current
    var oldShowKey by remember { mutableStateOf(false) }
    var newShowKey by remember { mutableStateOf(false) }
    var opw by remember { mutableStateOf("") }
    var npw1 by remember { mutableStateOf("") }
    var npw2 by remember { mutableStateOf("") }
    var userInfo by remember { mutableStateOf(adViewModel.userInfoStore.value) }
    val dialogString = remember { mutableStateOf(
        DialogString(
            width = 200.dp,
            height = 150.dp,
            title = "",
            message = "",
            button = "",
            show = mutableStateOf(false),
            callback = null
        )
    )
    }
    DialogScreenAsDialog(dialogString.value)
    Scaffold (
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                TopBar(navController, adViewModel, context.getString(R.string.infoPage))
                Text(
                    text = adViewModel.currentEmail,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                )
            }
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = stringResource(R.string.screen_settings),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp)
                )
                OutlinedTextField(
                    value = userInfo.name,
                    onValueChange = {newValue ->
                        userInfo = userInfo.copy(name = newValue)},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.email_icon)
                        )
                    },
                    label = {Text(stringResource(R.string.userName))},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
                )
                OutlinedTextField(
                    value = userInfo.phonenum,
                    onValueChange = {newValue ->
                        userInfo = userInfo.copy(phonenum = newValue)},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = stringResource(R.string.email_icon)
                        )
                    },
                    label = {Text(stringResource(R.string.user_phone_number))},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
                )
                OutlinedTextField(
                    value = userInfo.address,
                    onValueChange = {newValue ->
                        userInfo = userInfo.copy(address = newValue)},
                    label = {Text(stringResource(R.string.user_address))},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = stringResource(R.string.email_icon)
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(8.dp),
                    onClick = {
                        adViewModel.modifyInfo(
                            userInfo,
                            CallBackModifyInfo(
                                onSuccess = {
                                    dialogString.value.width = 400.dp
                                    dialogString.value.height = 200.dp
                                    dialogString.value.title = context.getString(R.string.dialogInformation)
                                    dialogString.value.message =
                                        context.getString(R.string.dialogInfoModifySuccessfully)
                                    dialogString.value.button = context.getString(R.string.dialogOk)
                                    dialogString.value.callback = {}
                                    dialogString.value.show.value = true
                                }
                            )
                        )
                    },
                ) {
                    Text(text = stringResource(R.string.button_modify))
                }
                Text(
                    text = stringResource(R.string.change_your_password),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp)
                )
                OutlinedTextField(
                    value = opw,
                    onValueChange = { opw = it },
                    label = { Text(text = stringResource(R.string.oldPassword)) },
                    placeholder = { Text(text = stringResource(R.string.enterYourOldPassword)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.lock_icon)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { oldShowKey = !oldShowKey }
                        ) {
                            Icon(
                                imageVector = if (oldShowKey) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription =
                                if (oldShowKey) stringResource(R.string.show_password)
                                else stringResource(R.string.hide_password)
                            )
                        }
                    },
                    visualTransformation = if (oldShowKey) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = npw1,
                    onValueChange = {npw1 = it},
                    label = { Text(text = stringResource(R.string.newPassword1)) },
                    placeholder = { Text(text = stringResource(R.string.enterYourNewPassword1)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.lock_icon)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { newShowKey = !newShowKey }
                        ) {
                            Icon(
                                imageVector = if (newShowKey) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription =
                                if (newShowKey) stringResource(R.string.show_password)
                                else stringResource(R.string.hide_password)
                            )
                        }
                    },
                    visualTransformation = if (newShowKey) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = npw2,
                    onValueChange = {npw2 = it},
                    label = { Text(text = stringResource(R.string.newPassword2)) },
                    placeholder = { Text(text = stringResource(R.string.enterYourNewPassword2)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.lock_icon)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { newShowKey = !newShowKey }
                        ) {
                            Icon(
                                imageVector = if (newShowKey) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription =
                                if (newShowKey) stringResource(R.string.show_password)
                                else stringResource(R.string.hide_password)
                            )
                        }
                    },
                    visualTransformation = if (newShowKey) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(8.dp),
                    onClick = {
                        adViewModel.resetPassword(
                            opw,npw1,npw2,
                            CallBackReset(
                                onSuccess = {
                                    dialogString.value.width = 400.dp
                                    dialogString.value.height = 200.dp
                                    dialogString.value.title = context.getString(R.string.dialogChangePassword)
                                    dialogString.value.message =
                                        context.getString(R.string.dialogModifySuccessfully)
                                    dialogString.value.button = context.getString(R.string.dialogOk)
                                    dialogString.value.callback = {
                                        adViewModel.userSignOut(navController)
                                    }
                                    dialogString.value.show.value = true
                                },
                                onSystemError = {string->
                                    dialogString.value.width = 400.dp
                                    dialogString.value.height = 200.dp
                                    dialogString.value.title = context.getString(R.string.dialogChangePassword)
                                    dialogString.value.message = string
                                    dialogString.value.button = context.getString(R.string.dialogOk)
                                    dialogString.value.callback = { }
                                    dialogString.value.show.value = true
                                },
                                onIncorrectPassword = {
                                    dialogString.value.width = 400.dp
                                    dialogString.value.height = 200.dp
                                    dialogString.value.title = context.getString(R.string.dialogChangePassword)
                                    dialogString.value.message =
                                        context.getString(R.string.dialogOldPasswordIncorrect)
                                    dialogString.value.button = context.getString(R.string.dialogOk)
                                    dialogString.value.callback = { }
                                    dialogString.value.show.value = true
                                },
                                onPasswordMismatch = {
                                    dialogString.value.width = 400.dp
                                    dialogString.value.height = 200.dp
                                    dialogString.value.title = context.getString(R.string.dialogChangePassword)
                                    dialogString.value.message =
                                        context.getString(R.string.dialogPasswordMismatch)
                                    dialogString.value.button = context.getString(R.string.dialogOk)
                                    dialogString.value.callback = { }
                                    dialogString.value.show.value = true
                                }
                            )
                        )
                    },
                ) {
                    Text(text = stringResource(R.string.button_modify))
                }
            }
        }
    )
}
