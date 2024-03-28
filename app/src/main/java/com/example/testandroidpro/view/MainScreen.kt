package com.example.testandroidpro.view

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.testandroidpro.viewmodel.AdViewModel
import com.example.testandroidpro.viewmodel.PdfLoadViewModel
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navController: NavController, adViewModel: AdViewModel) {
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
                DropdownMenuItem(
                    text = { Text("info") },
                    onClick = { navController.navigate("info") }
                )
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



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(navController: NavController, adViewModel: AdViewModel, pdfLoadViewModel: PdfLoadViewModel) {
    Scaffold (
        topBar = { MainTopBar(navController,adViewModel) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Log.d("MainScreen",adViewModel.userName)
                val state = adViewModel.listState
                Text(
                    text = "Welcome "+adViewModel.userName.toString(),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .weight(0.1f)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp)
                        .weight(1f),
                    state = state
                ) {
                    adViewModel.adList.value.let { it1 ->
                        items(it1.chunked(2)) { chunk ->
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                items(chunk) { document ->
                                    val name = document.supplier.id
                                    val shadowSize = 3.dp
                                    //                                Spacer(modifier = Modifier.weight(1f).border(1.dp, Color.Green))
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillParentMaxWidth(0.44f)
                                            .aspectRatio(0.6f)
                                            .clipToBounds()
                                            .drawWithContent {
                                                drawContent()
                                                drawRect(
                                                    brush = Brush.verticalGradient(
                                                        colors = listOf(Color.Transparent, Color.LightGray),
                                                        startY = 0f,
                                                        endY = shadowSize.toPx()
                                                    ),
                                                    size = Size(size.width-shadowSize.toPx()*2, shadowSize.toPx()),
                                                    topLeft = Offset(shadowSize.toPx(), 0f)
                                                )
                                                drawRect(
                                                    brush = Brush.horizontalGradient(
                                                        colors = listOf(Color.Transparent, Color.LightGray),
                                                        startX = 0f,
                                                        endX = shadowSize.toPx()
                                                    ),
                                                    size = Size(shadowSize.toPx(), size.height-shadowSize.toPx()*2),
                                                    topLeft = Offset(0f, shadowSize.toPx())
                                                )
                                                drawRect(
                                                    brush = Brush.verticalGradient(
                                                        colors = listOf(Color.LightGray, Color.Transparent),
                                                        startY = size.height-shadowSize.toPx(),
                                                        endY = size.height
                                                    ),
                                                    size = Size(size.width-shadowSize.toPx()*2, shadowSize.toPx()),
                                                    topLeft = Offset(shadowSize.toPx(), size.height-shadowSize.toPx())
                                                )
                                                drawRect(
                                                    brush = Brush.horizontalGradient(
                                                        colors = listOf(Color.LightGray, Color.Transparent),
                                                        startX = size.width-shadowSize.toPx(),
                                                        endX = size.width
                                                    ),
                                                    size = Size(shadowSize.toPx(), size.height-shadowSize.toPx()*2),
                                                    topLeft = Offset(size.width-shadowSize.toPx(), shadowSize.toPx())
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(Color.LightGray, Color.Transparent),
                                                        center = Offset(shadowSize.toPx(),shadowSize.toPx()),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -180f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(0.dp.toPx(),0.dp.toPx()),
                                                    size = Size(shadowSize.toPx()*2, shadowSize.toPx()*2),
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(Color.LightGray, Color.Transparent),
                                                        center = Offset(shadowSize.toPx(),size.height-shadowSize.toPx()),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -270f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(0.dp.toPx(),size.height-shadowSize.toPx()*2),
                                                    size = Size(shadowSize.toPx()*2, shadowSize.toPx()*2),
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(Color.LightGray, Color.Transparent),
                                                        center = Offset(size.width-shadowSize.toPx(),size.height-shadowSize.toPx()),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -0f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(size.width-shadowSize.toPx()*2,size.height-shadowSize.toPx()*2),
                                                    size = Size(shadowSize.toPx()*2, shadowSize.toPx()*2),
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(Color.LightGray, Color.Transparent),
                                                        center = Offset(size.width-shadowSize.toPx(),shadowSize.toPx()),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -90f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(size.width-shadowSize.toPx()*2,0.dp.toPx()),
                                                    size = Size(shadowSize.toPx()*2, shadowSize.toPx()*2),
                                                )
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                //                                              .border(1.dp, Color.Red)
                                                .fillMaxSize()
                                                .padding(shadowSize)
                                                .clickable {
                                                    adViewModel.filePath = document.ads[0]
                                                        .getString("attachment")
                                                        .toString()
                                                    Log.d("test click", "click success")
                                                    navController.navigate("pdf")
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            //                                    Spacer(
                                            //                                        modifier = Modifier
                                            //                                            .weight(0.05f)
                                            //                                    )
                                            Row(
                                                modifier = Modifier
                                                    .weight(1.5f)
                                                    //                                            .border(1.dp, Color.Red)
                                                    .padding(8.dp)
                                                    .fillMaxWidth(),
                                                //                                        horizontalArrangement = Arrangement.Start
                                            )
                                            {
                                                name.let { iconName ->
                                                    adViewModel.getLocalFile(iconName).let {localFile->
                                                        if (localFile != null) {
                                                            Image(
                                                                painter = rememberAsyncImagePainter(
                                                                    ImageRequest.Builder(
                                                                        LocalContext.current
                                                                    ).data(data = localFile)
                                                                        .apply(block = fun ImageRequest.Builder.() {
                                                                            crossfade(true)
                                                                        }).build()
                                                                ),
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(0.dp)
                                                                    //                                                        .size(80.dp)
                                                                    .fillMaxHeight()
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .aspectRatio(1f),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                name.let { iconName ->
                                                    val localFile = adViewModel.getLocalFileEnter(iconName)
                                                    if (localFile != null) {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(
                                                                ImageRequest.Builder(
                                                                    LocalContext.current
                                                                ).data(data = localFile)
                                                                    .apply(block = fun ImageRequest.Builder.() {
                                                                        crossfade(true)
                                                                    }).build()
                                                            ),
                                                            contentDescription = null,
                                                            modifier = Modifier
                                                                .padding(0.dp)
                                                                .fillMaxWidth()
                                                        )
                                                    }
                                                }
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                document.ads[0].getString("adName")?.let { it2 ->
                                                    Text(
                                                        text = it2,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(1.dp),
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 22.sp
                                                    )
                                                }
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .height(2.dp)
                                                    .fillMaxWidth(),
                                                //                                            horizontalArrangement = Arrangement.Center
                                            ) {
                                                Spacer(
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                )
                                                Divider(
                                                    color = Color.LightGray,
                                                    thickness = 2.dp,
                                                    modifier = Modifier
                                                        .weight(9f),
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                val timestampStart = document.ads[0].getTimestamp("periodStart")
                                                timestampStart?.let { periodStart ->
                                                    val date = periodStart.toDate()
                                                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                                    val formattedDate = formatter.format(date)
                                                    Text(
                                                        text = formattedDate,
                                                        modifier = Modifier
                                                            .padding(1.dp),
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                                Text(
                                                    text = "~",
                                                    modifier = Modifier
                                                        .padding(1.dp),
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 12.sp
                                                )
                                                val timestampStop = document.ads[0].getTimestamp("periodStop")
                                                timestampStop?.let { periodStart ->
                                                    val date = periodStart.toDate()
                                                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                                    val formattedDate = formatter.format(date)
                                                    Text(
                                                        text = formattedDate,
                                                        modifier = Modifier
                                                            .padding(1.dp),
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    //                                Spacer(modifier = Modifier.height(300.dp).weight(1f).border(1.dp, Color.Green))
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .height(10.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}