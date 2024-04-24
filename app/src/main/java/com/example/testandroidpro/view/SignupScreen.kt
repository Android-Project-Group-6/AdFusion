package com.example.testandroidpro.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testandroidpro.R
import com.example.testandroidpro.data.CallBackUserSignup
import com.example.testandroidpro.data.DialogString
import com.example.testandroidpro.data.Myuser
import com.example.testandroidpro.viewmodel.AdViewModel

@Composable
fun SignupScreen(navController: NavController, adViewModel: AdViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var userInfo by remember { mutableStateOf(Myuser("", "", "")) }
    var showKey by remember { mutableStateOf(false) }
    var pw1 by remember { mutableStateOf("") }
    var pw2 by remember { mutableStateOf("") }
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
        topBar = { TopBar(navController,adViewModel,context.getString(R.string.signupPage)) },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(it)
            ) {
                Text(
                    text = stringResource(R.string.please_signup),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                )
                Card(
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.required_items),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            adViewModel.userState = ""
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = stringResource(R.string.email_icon)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(R.string.person_icon)
                            )
                        },
                        label = { Text(text = stringResource(R.string.email)) },
                        placeholder = { Text(text = stringResource(R.string.enter_your_email)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            capitalization = KeyboardCapitalization.None
                        ),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = pw1,
                        onValueChange = { pw1 = it },
                        label = { Text(text = stringResource(R.string.password)) },
                        placeholder = { Text(text = stringResource(R.string.enter_your_password)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = stringResource(R.string.lock_icon)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { showKey = !showKey }
                            ) {
                                Icon(
                                    imageVector = if (showKey) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription =
                                    if (showKey) stringResource(R.string.show_password)
                                    else stringResource(R.string.hide_password)
                                )
                            }
                        },
                        visualTransformation = if (showKey) VisualTransformation.None else PasswordVisualTransformation(),
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
                        value = pw2,
                        onValueChange = { pw2 = it },
                        label = { Text(text = stringResource(R.string.password)) },
                        placeholder = { Text(text = stringResource(R.string.enter_your_password)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = stringResource(R.string.lock_icon)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { showKey = !showKey }
                            ) {
                                Icon(
                                    imageVector = if (showKey) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription =
                                    if (showKey) stringResource(R.string.show_password)
                                    else stringResource(R.string.hide_password)
                                )
                            }
                        },
                        visualTransformation = if (showKey) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true
                    )
                }
                Card(
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.optional_items),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    )
                    OutlinedTextField(
                        value = userInfo.name,
                        onValueChange = { newValue ->
                            userInfo = userInfo.copy(name = newValue)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(R.string.email_icon)
                            )
                        },
                        label = { Text(stringResource(R.string.userName)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
                    )
                    OutlinedTextField(
                        value = userInfo.phonenum,
                        onValueChange = { newValue ->
                            userInfo = userInfo.copy(phonenum = newValue)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = stringResource(R.string.email_icon)
                            )
                        },
                        label = { Text(stringResource(R.string.user_phone_number)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
                    )
                    OutlinedTextField(
                        value = userInfo.address,
                        onValueChange = { newValue ->
                            userInfo = userInfo.copy(address = newValue)
                        },
                        label = { Text(stringResource(R.string.user_address)) },
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
                            .padding(top = 6.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(8.dp),
                    onClick = {
                        dialogString.value.width = 400.dp
                        dialogString.value.height = 200.dp
                        dialogString.value.title = context.getString(R.string.button_signup)
                        dialogString.value.button = context.getString(R.string.dialogOk)

                        adViewModel.userSignup(
                            email, pw1, pw2, userInfo,
                            CallBackUserSignup(
                                onSuccess = {
                                    dialogString.value.message =
                                        context.getString(R.string.dialogSignupSuccessfully)
                                    dialogString.value.callback = {
                                        navController.popBackStack(context.getString(R.string.signupPage), inclusive = true)
                                        navController.popBackStack(context.getString(R.string.loginPage), inclusive = true)
                                        navController.navigate(context.getString(R.string.homePage))
                                    }
                                    dialogString.value.show.value = true
                                },
                                onSystemError = {
                                    dialogString.value.message = it
                                    dialogString.value.callback = {}
                                    dialogString.value.show.value = true
                                },
                                onEmailPwEmpty = {
                                    dialogString.value.message = context.getString(R.string.dialogEmailPasswordEmpty)
                                    dialogString.value.callback = {}
                                    dialogString.value.show.value = true
                                },
                                onPwMismatch = {
                                    dialogString.value.message = context.getString(R.string.dialogPasswordMismatch)
                                    dialogString.value.callback = {}
                                    dialogString.value.show.value = true
                                }
                            )
                        )
                    },
                ) {
                    Text(text = stringResource(R.string.button_reg))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}