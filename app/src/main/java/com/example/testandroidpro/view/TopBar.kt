package com.example.testandroidpro.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testandroidpro.R
import com.example.testandroidpro.viewmodel.AdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, adViewModel: AdViewModel, screen:String) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val density = LocalDensity.current
    val fontSize = remember { mutableStateOf(0.sp) }
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned{ layoutCoordinates ->
                val newFontSize = with(density) { layoutCoordinates.size.height.toDp().toPx() / density.density } * 0.7f
                fontSize.value = newFontSize.sp
            },
        title = { },
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
//                        .border(width = 1.dp, color = Color.Black)
                    ,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(screen != context.getString(R.string.homePage)) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
                Row(
                    modifier = Modifier
//                        .fillMaxHeight()
                        .weight(8f)
//                        .border(width = 1.dp, color = Color.Red)
                    ,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.ComNameHead),
                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(1.dp)
                        ,
                        textAlign = TextAlign.Center,
                        fontSize = fontSize.value,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.ComNameBody),
                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(1.dp)
                        ,
                        textAlign = TextAlign.Center,
                        fontSize = fontSize.value,
                        color = Color(0xFFFF69B4)
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
//                        .border(width = 1.dp, color = Color.Black)
                    ,
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (screen != context.getString(R.string.supportPage)) {
                        IconButton(
                            onClick = {
                                expanded = !expanded
                            }
                        ) {
                            Icon(Icons.Filled.MoreVert, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            if (screen == context.getString(R.string.homePage)) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.drop_info)) },
                                    onClick = { navController.navigate(context.getString(R.string.infoPage)) }
                                )
                            } else if (screen == context.getString(R.string.infoPage)) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.ads_list)) },
                                    onClick = { navController.navigate(context.getString(R.string.homePage)) }
                                )
                            } else {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.drop_info)) },
                                    onClick = { navController.navigate(context.getString(R.string.infoPage)) }
                                )
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.ads_list)) },
                                    onClick = { navController.navigate(context.getString(R.string.homePage)) }
                                )
                            }
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.drop_signout)) },
                                onClick = {
                                    adViewModel.userSignOut(navController)
//                        navController.navigate("Settings")
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}