package com.example.testandroidpro

import android.annotation.SuppressLint
import com.github.barteksc.pdfviewer.PDFView
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import com.example.testandroidpro.viewmodel.PdfLoadViewModel

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

// Create a new user with a first and last name
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )

    val sub = hashMapOf(
        "P" to false,
        "S" to false,
        "K" to false
    )

    data class Myuser(val fname:String, val lname:String, val street:String, val house:Int)
    data class Myusub(val P:Boolean, val S:Boolean, val K:Boolean, val L:Boolean)

//    db.collection("users")
//        .document("z7M2oq3IcMM1lvJBFRkb9tcowdR2")
//        .collection("inf")
//        .document("subscribe")
//        .set(Myusub(false,false,false,false))
//        .addOnSuccessListener { documentReference ->
//            Log.d(TAG, "DocumentSnapshot added with ID:")
//        }
//        .addOnFailureListener { e ->
//            Log.w(TAG, "Error adding document", e)
//        }


    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navController: NavController) {
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
                    onClick = { navController.navigate("info") })
//                DropdownMenuItem(
//                    text = { Text("Settings") },
//                    onClick = { navController.navigate("Settings") })
            }
        }
    )
}


@Composable
fun InfoScreen(navController: NavController, adViewModel: AdViewModel) {
    Text(
        text = "I am Info",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
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
//    val storage = Firebase.storage
//    var storageRef = storage.reference
//    val pathReference = storageRef.child("Koko-Suomen-tarjoukset-to-14-3-ke-20-3-05.pdf")
////    val gsReference = storage.getReferenceFromUrl("gs://testandroidpro-179da.appspot.com/Koko-Suomen-tarjoukset-to-14-3-ke-20-3-05.pdf")
//    var islandRef = storageRef.child("Koko-Suomen-tarjoukset-to-14-3-ke-20-3-05.pdf")
//
//    val localFile = File.createTempFile("tmpPDF", "pdf")
//
//    islandRef.getFile(localFile).addOnSuccessListener {
//        // Local temp file has been created
//        Log.d("TestPdf1","success1")
//    }.addOnFailureListener {
//        // Handle any errors
//    }



//    val context = LocalContext.current
//    val storageReference = FirebaseStorage.getInstance().reference
//    val pathReference = storageReference.child("Koko-Suomen-tarjoukset-to-14-3-ke-20-3-05.pdf")
//    val localFile = File.createTempFile("tempPdf", "pdf")
//
//    val pdfFile = remember { mutableStateOf<File?>(null) }
//
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    lifecycleOwner.lifecycleScope.launch {
//        pathReference.getFile(localFile).addOnSuccessListener {
//            pdfFile.value = localFile
//            Log.d("TestPdf","success")
//        }.addOnFailureListener {
//            // Handle any errors
//        }
//    }


@Composable
fun PdfScreen(navController: NavController, adViewModel: AdViewModel, filePath: String) {

    val context = LocalContext.current
    val storageReference = FirebaseStorage.getInstance().reference
    val pathReference = storageReference.child(filePath)//pdfLoadViewModel.filePath
    val localFile = File.createTempFile("tempPdf", "pdf")

    val pdfFile = remember { mutableStateOf<File?>(null) }
    val pdfLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            pathReference.getFile(localFile).await()
            pdfFile.value = localFile
            pdfLoading.value = false
            Log.d("TestPdf","success")
        } catch (e: Exception) {
            Log.e("TestPdf", "Error loading PDF", e)
//            Log.d("TestPdf",pdfLoadViewModel.filePath)
        }
    }

//    pdfLoadViewModel.loadPdfFile()
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
        Log.d("qqqqq",pdfLoading.value.toString())//pdfLoadViewModel.
        if (pdfLoading.value) {//pdfLoadViewModel.
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
                pdfFile.value?.let {//pdfLoadViewModel.
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
//    val db = Firebase.firestore
//    db.collection("suppliers")
//        .get()
//        .addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d(TAG, "${document.id} => ${document.data}")
//            }
//        }
//        .addOnFailureListener { e ->
//            Log.w("test read database", "Error adding document", e)
//        }
//    val db = Firebase.firestore
//    val dataState = remember { mutableStateOf<List<DocumentSnapshot>>(emptyList()) }
//
//    LaunchedEffect(Unit) {
//        try {
//            val result = db.collection("suppliers").get().await()
//            dataState.value = result.documents
//        } catch (e: Exception) {
//            Log.e(TAG, "Error fetching data: ", e)
//        }
//    }
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(navController: NavController, adViewModel: AdViewModel, pdfLoadViewModel:PdfLoadViewModel) {
    Log.d("MainScreen","AAAAAAAAAAAAAAA")
    Scaffold (
        topBar = {MainTopBar(navController)},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Log.d("MainScreen","BBBBBBBBBBBB")
                Text(
                    text = "I am Main",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .weight(0.1f)
                )
                Log.d("MainScreen","CCCCCCCCCCCCCCCCCC")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                ) {
                    Log.d("MainScreen","DDDDDDDDDDDDDDDD")
                    items(adViewModel.dataState.value) { document ->
                        val name = document.id
                        Row(
                            modifier = Modifier
//                                .border(1.dp, Color.Black)
                                .padding(8.dp)
                                .clickable {
                                    pdfLoadViewModel.filePath = document.getString("pdf").toString()
//                                    pdfLoadViewModel.loadPdfFile()
                                    Log.d("test click","click success")
                                    navController.navigate("pdf")
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            name.let { iconName ->
                                val localFile = adViewModel.getLocalFile(iconName)
                                if (localFile != null) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            ImageRequest.Builder(
                                                LocalContext.current
                                            ).data(data = localFile).apply(block = fun ImageRequest.Builder.() {
                                                crossfade(true)
                                            }).build()
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(0.5f)
                                            .size(80.dp)
                                    )
                                }
                            }

                            Text(
                                text = document.id,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    )
}
//                            AsyncImage(
//                                model = (R.drawable.lidl),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .padding(16.dp)
//                                    .weight(0.5f)
//                                    .size(50.dp)
//                            )
//                            Text(
//                                text = "document.id",
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .padding(16.dp),
//                                textAlign = TextAlign.Center
//                            )

//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(it)
////                        .verticalScroll(rememberScrollState())
//                        .clipToBounds(),
//                )
//                {
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
    NavHost(navController = navController, startDestination = "login") {
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
                "pdf" -> PdfScreen(navController,adViewModel,pdfLoadViewModel.filePath)
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

