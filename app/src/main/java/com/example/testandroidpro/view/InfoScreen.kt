package com.example.testandroidpro.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testandroidpro.viewmodel.AdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTopBar(navController: NavController, adViewModel:AdViewModel) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text("AdFusion") },
        actions = {
            IconButton(
                onClick = {
                    expanded = !expanded
                }
            ) {
                Icon(Icons.Filled.MoreVert,contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
//                DropdownMenuItem(
//                    text = { Text("info") },
//                    onClick = { navController.navigate("info") }
//                )
                DropdownMenuItem(
                    text = { Text("Signout") },
                    onClick = {
                        adViewModel.userSignOut(navController)
//                        navController.navigate("Settings")
                    }
                )
            }
        }
    )
}
@Composable
fun InfoScreen(navController: NavController, adViewModel: AdViewModel) {
    Scaffold (
        topBar = { InfoTopBar(navController,adViewModel) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Text(
                    text = "I am Info",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                )
                OutlinedTextField(
                    value = adViewModel.userName,
                    onValueChange = {adViewModel.userName = it.replace(',','.')},
                    label = {Text("Name")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                )
                OutlinedTextField(
                    value = adViewModel.userPhoneNum,
                    onValueChange = {adViewModel.userPhoneNum = it.replace(',','.')},
                    label = {Text("Phone Number")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                )
                OutlinedTextField(
                    value = adViewModel.userAddress,
                    onValueChange = {adViewModel.userAddress = it.replace(',','.')},
                    label = {Text("Address")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(8.dp),
                    onClick = {
                        adViewModel.modifyInfo(navController)
                    },
                ) {
                    Text(text = "Modify")
                }
            }
        }
    )
}
