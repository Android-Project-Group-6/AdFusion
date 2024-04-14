package com.example.testandroidpro.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testandroidpro.R
import com.example.testandroidpro.data.DialogString
import com.example.testandroidpro.viewmodel.AdViewModel

@Composable
fun LoginScreen(navController: NavController, adViewModel: AdViewModel) {
    var showKey by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var email by remember { mutableStateOf(adViewModel.emailDisplay) }
    var pw by remember { mutableStateOf("") }
    val dialogString = DialogString(
        width = 200.dp,
        height = 150.dp,
        title = "Dialog Title",
        message = "Dialog Message",
        button = "OK",
        show = remember { mutableStateOf(false) }
    ){}
    DialogScreenAsDialog(dialogString)

    val density = LocalDensity.current
    val fontSizeItem = remember { mutableStateOf(0.sp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
//                    .weight(1f)
                .onGloballyPositioned { layoutCoordinates ->
                    val newFontSize = with(density) {
                        layoutCoordinates.size.height
                            .toDp()
                            .toPx() / density.density
                    } * 0.8f
                    fontSizeItem.value = 32.sp//newFontSize.sp
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.welcome),
                fontSize = fontSizeItem.value,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
//                    .fillMaxWidth()
            )
            Text(
                text = " to ",
                fontSize = fontSizeItem.value,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
//                    .fillMaxWidth()
            )
            Text(
                text = "Ad",
                fontSize = fontSizeItem.value,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
//                    .fillMaxWidth()
            )
            Text(
                text = "Fusion",
                fontSize = fontSizeItem.value,
                color = Color(0xFFFF69B4),
                textAlign = TextAlign.Center,
                modifier = Modifier
//                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
//                    .weight(0.8f)
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.please_login),
                fontSize = fontSizeItem.value*0.8,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
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
//                    .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.None
            ),
            singleLine = true
        )
        OutlinedTextField(
            value = pw,
            onValueChange = {
                pw = it
                adViewModel.userState = ""
            },
            label = { Text(text = stringResource(R.string.password)) },
            placeholder = { Text(text = stringResource(R.string.enter_your_password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = stringResource(R.string.lock_icon)
                )
            },
            trailingIcon = {
                IconButton(onClick = { showKey = !showKey }) {
                    Icon(
                        imageVector = if (showKey) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription =
                        if (showKey) stringResource(R.string.show_password)
                        else stringResource(R.string.hide_password)
                    )
                }
            },
            visualTransformation = if (showKey) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
//                    .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )
        Row(
            modifier = Modifier
//                    .weight(0.8f)
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                .let {
                    if (stringResource(R.string.borderdebug) == "true") it.border(
                        1.dp, Color.Red
                    ) else it
                },
            horizontalArrangement = Arrangement.End
        ) {
            ClickableText(
                text = AnnotatedString(stringResource(R.string.forgot_password)),
                onClick = { offset ->

                    adViewModel.forgotPassword(email){

                        dialogString.width = 400.dp
                        dialogString.height = 200.dp
                        dialogString.title = "Forgot Password"
                        dialogString.message = it
                        dialogString.button = "Ok"
                        dialogString.show.value = true
                        dialogString.callback = {}

                    }
                    Log.d("Clicked on offset:", " $offset")
                }
            )
        }
        val density1 = LocalDensity.current
        val fontSize1 = remember { mutableStateOf(0.sp) }
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(8.dp)
                .aspectRatio(3.5f)
                .onGloballyPositioned { layoutCoordinates ->
                    val newFontSize = with(density1) {
                        layoutCoordinates.size.height
                            .toDp()
                            .toPx() / density1.density
                    } * 0.3f
                    fontSize1.value = newFontSize.sp
                },
            onClick = {
                adViewModel.userLogin(navController, email, pw) { string ->
                    if (string == "Login success") {
                        Log.d("Login", "Login success")
                        dialogString.width = 400.dp
                        dialogString.height = 200.dp
                        dialogString.title = "Login"
                        dialogString.message = string
                        dialogString.button = "Ok"
                        dialogString.show.value = false
                        dialogString.callback = {
                            navController.popBackStack("login", inclusive = true)
                            navController.navigate("home")
                        }
                        navController.popBackStack("login", inclusive = true)
                        navController.navigate("home")
                    } else {
                        Log.e("Login", string)
                        dialogString.width = 400.dp
                        dialogString.height = 200.dp
                        dialogString.title = "Login"
                        dialogString.message = string
                        dialogString.button = "Back"
                        dialogString.show.value = true
                        dialogString.callback = {}
//                                    Toast.makeText(context, "Failed to write message", Toast.LENGTH_SHORT).show()
                    }
                }


            },
        ) {
            Text(
                text = stringResource(R.string.button_login),
                fontSize = fontSize1.value,
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(8.dp)
                .aspectRatio(3.5f),
            onClick = {
                navController.navigate(context.getString(R.string.screen_signup))
            },
        ) {
            Text(
                text = stringResource(R.string.button_signup),
                fontSize = fontSize1.value,
            )
        }
//        Text(
//            text = adViewModel.userState,
//            fontSize = 24.sp,
//            color = MaterialTheme.colorScheme.primary,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp, bottom = 16.dp)
//        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3.5f),
            )
        Text(
            text = "Need help? Let’s connect to customer support team",
            fontSize = fontSize1.value*0.9,
            lineHeight = fontSize1.value,
//                    color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 6.dp, bottom = 6.dp)
                .fillMaxWidth(0.6f)
                .let {
                    if (stringResource(R.string.borderdebug) == "true") it.border(
                        1.dp, Color.Red
                    ) else it
                }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(8.dp)
                .aspectRatio(3.5f),
            onClick = {
                navController.navigate(context.getString(R.string.supportPage))
            },
        ) {
            Text(
                text = "Support",
                fontSize = fontSize1.value,
            )
        }
    }
}