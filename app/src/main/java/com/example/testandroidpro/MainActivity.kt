package com.example.testandroidpro

import android.annotation.SuppressLint
import com.github.barteksc.pdfviewer.PDFView
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testandroidpro.ui.theme.TestAndroidProTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.testandroidpro.viewmodel.AdViewModel
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Divider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import com.example.testandroidpro.viewmodel.PdfLoadViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAndroidProTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navController: NavController, adViewModel:AdViewModel) {
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
        topBar = {InfoTopBar(navController,adViewModel)},
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
@Composable
fun PdfViewer(pdfFile: File, modifier: Modifier = Modifier) {
        AndroidView(
            factory = { context ->
                PDFView(context, null).apply {
                    fromFile(pdfFile)
                        .enableSwipe(true) // 启用滑动翻页
                        .swipeHorizontal(false) // 设置为垂直滑动
                        .enableDoubletap(true) // 启用双击缩放
                        .defaultPage(0) // 设置默认打开的页面
                        .enableAnnotationRendering(false) // 渲染注释（默认为false）
                        .password(null) // 如果PDF有密码，则输入密码
                        .scrollHandle(null) // 设置滚动条样式（默认为null）
                        .enableAntialiasing(true) // 改善低分辨率屏幕上的渲染
                        .spacing(0) // 在页面之间添加间距（默认为0）
                        .autoSpacing(false) // 在页面之间添加动态间距以适应屏幕方向
                        .pageFitPolicy(FitPolicy.WIDTH) // 模式设置为适应屏幕宽度
                        .load()
                }
            },
            update = { view ->
                view.fromFile(pdfFile).load()
            },
            modifier = modifier
        )

}
@Composable
fun PdfRendererView(pdfFile: File) {
    var pageIndex by remember { mutableStateOf(0) }
    var pdfRenderer: PdfRenderer? = null
    var page: PdfRenderer.Page? = null

    AndroidView(
        factory = { context ->
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL

            val imageView = ImageView(context)
            layout.addView(imageView)

            val prevButton = android.widget.Button(context).apply {
                text = "Previous"
                setOnClickListener {
                    if (pageIndex > 0) {
                        pageIndex--
                    }
                }
            }
            layout.addView(prevButton)

            val nextButton = android.widget.Button(context).apply {
                text = "Next"
                setOnClickListener {
                    if (pageIndex < (pdfRenderer?.pageCount ?: 0) - 1) {
                        pageIndex++
                    }
                }
            }
            layout.addView(nextButton)

            val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            pdfRenderer = PdfRenderer(fileDescriptor)

            layout
        },
        update = { view ->
            page?.close()
            page = pdfRenderer?.openPage(pageIndex)
            val bitmap = Bitmap.createBitmap(page?.width ?: 0, page?.height ?: 0, Bitmap.Config.ARGB_8888)
            page?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            (view.getChildAt(0) as ImageView).setImageBitmap(bitmap)
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            page?.close()
            pdfRenderer?.close()
        }
    }
}

@Composable
fun PdfScreen(navController: NavController, filePath: String) {
    val context = LocalContext.current
    val pdfLoadViewModel: PdfLoadViewModel = viewModel()
    pdfLoadViewModel.loadPdfFile(filePath)

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "I am Pdf",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
                .weight(0.1f)
        )
        if (pdfLoadViewModel.pdfLoading.value) {//pdfLoadViewModel.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Loading...",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .weight(0.1f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally // 水平居中对齐
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .border(2.dp, Color.Black)
                    .weight(1f)
            ) {
                pdfLoadViewModel.pdfFile.value?.let {//pdfLoadViewModel.
                    PdfViewer(
                        it, Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .clipToBounds()
                    )
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(navController: NavController, adViewModel: AdViewModel, pdfLoadViewModel:PdfLoadViewModel) {
    Scaffold (
        topBar = {MainTopBar(navController,adViewModel)},
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(navController: NavController, adViewModel: AdViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Welcome",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            text = "Please Signup",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        )
        OutlinedTextField(
            value = adViewModel.email,
            onValueChange = {adViewModel.email = it.replace(',','.')},
            label =  {Text("Email *")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
        OutlinedTextField(
            value = adViewModel.passWord,
            onValueChange = {adViewModel.passWord = it.replace(',','.')},
            label = {Text("PassWord *")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
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
                adViewModel.userSignup(navController)
            },
        ) {
            Text(text = "Reg")
        }
        Text(
            text = adViewModel.userState,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
@Composable
fun WaitingScreen(navController: NavController, adViewModel: AdViewModel) {



}
@Composable
fun LoginScreen(navController: NavController, adViewModel: AdViewModel) {
    var showKey by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Text(
            text = "Welcome",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            text = "Please Login",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        )
        OutlinedTextField(
            value = adViewModel.email,
            onValueChange = {
                adViewModel.email = it.replace(',','.')
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon"
                )
            },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Type your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.None
            )
        )
        OutlinedTextField(
            value = adViewModel.passWord,
            onValueChange = {
                adViewModel.passWord = it.replace(',','.')
            },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Lock Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { showKey = !showKey }) {
                    Icon(
                        imageVector = if (showKey) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = if (showKey) "Show Password" else "Hide Password"
                    )
                }
            },
            visualTransformation = if (showKey) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(8.dp),
            onClick = {
                adViewModel.userLogin(navController)
            },
        ) {
            Text(text = "Login")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(8.dp),
            onClick = {
                navController.navigate("signup")
            },
        ) {
            Text(text = "Signup")
        }
        Text(
            text = adViewModel.userState,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        )
    }
}

//@Composable
//fun MyApp() {
//    val navController = rememberNavController()
//    val context = LocalContext.current
//    val adViewModel: AdViewModel = viewModel()
//    val pdfLoadViewModel:PdfLoadViewModel = viewModel()
//    NavHost(navController = navController, startDestination = "login"
//    )
//    {
//        composable(route = "login"){
//            LoginScreen(navController,adViewModel)
//        }
//        composable(route = context.getString(R.string.HomePage)){
//            MainScreen(navController,adViewModel,pdfLoadViewModel)
//        }
//        composable(route = "signup"){
//            SignupScreen(navController,adViewModel)
//        }
//        composable(route = "info"){
//            InfoScreen(navController,adViewModel)
//        }
//        composable(route = "pdf"){
//            PdfScreen(navController,adViewModel,pdfLoadViewModel)
//        }
//
//    }
//}
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val adViewModel: AdViewModel = viewModel()
    val pdfLoadViewModel:PdfLoadViewModel = viewModel()
    NavHost(navController = navController, startDestination = adViewModel.startDestination) {
        composable(route = "login") {
            when (navController.currentDestination?.route) {
                "login" -> LoginScreen(navController,adViewModel)
            }
        }
        composable(route = context.getString(R.string.HomePage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.HomePage) -> MainScreen(navController,adViewModel,pdfLoadViewModel)
            }
        }
        composable(route = "signup") {
            when (navController.currentDestination?.route) {
                "signup" -> SignupScreen(navController,adViewModel)
            }
        }
        composable(route = "info") {
            when (navController.currentDestination?.route) {
                "info" -> InfoScreen(navController,adViewModel)
            }
        }
        composable(route = "pdf") {
            when (navController.currentDestination?.route) {
                "pdf" -> PdfScreen(navController,adViewModel.filePath)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAndroidProTheme {
        MyApp()
    }
}

