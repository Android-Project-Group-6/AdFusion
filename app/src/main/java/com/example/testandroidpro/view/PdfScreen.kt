package com.example.testandroidpro.view

//import android.graphics.Bitmap
//import android.graphics.pdf.PdfRenderer
//import android.os.ParcelFileDescriptor
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.LinearLayout
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
//import androidx.navigation.NavController
import com.example.testandroidpro.R
import com.example.testandroidpro.viewmodel.AdViewModel
import com.example.testandroidpro.viewmodel.PdfLoadViewModel
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File

@Composable
fun PdfViewer(pdfFile: File, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            PDFView(context, null).apply {
                fromFile(pdfFile)
                    .enableSwipe(true) // Enable swipe to flip pages
                    .swipeHorizontal(false) // Set to vertical swipe
                    .enableDoubletap(true) // Enable double tap to zoom
                    .defaultPage(0) // Set the default open page
                    .enableAnnotationRendering(false) // Render annotations (default is false)
                    .password(null) // If the PDF has a password, enter it here
                    .scrollHandle(null) // Set the scrollbar style (default is null)
                    .enableAntialiasing(true) // Improve rendering on low-resolution screens
                    .spacing(0) // Add spacing between pages (default is 0)
                    .autoSpacing(false) // Add dynamic spacing between pages to fit screen orientation
                    .pageFitPolicy(FitPolicy.WIDTH) // Set the mode to fit screen width
                    .load()
            }
        },
//        update = { view ->
//            view.fromFile(pdfFile)
//                .load()
//        },
        modifier = modifier
    )
}
//@Composable
//fun PdfRendererView(pdfFile: File) {
//    var pageIndex by remember { mutableStateOf(0) }
//    var pdfRenderer: PdfRenderer? = null
//    var page: PdfRenderer.Page? = null
//
//    AndroidView(
//        factory = { context ->
//            val layout = LinearLayout(context)
//            layout.orientation = LinearLayout.VERTICAL
//
//            val imageView = ImageView(context)
//            layout.addView(imageView)
//
//            val prevButton = Button(context).apply {
//                text = "Previous"
//                setOnClickListener {
//                    if (pageIndex > 0) {
//                        pageIndex--
//                    }
//                }
//            }
//            layout.addView(prevButton)
//
//            val nextButton = Button(context).apply {
//                text = "Next"
//                setOnClickListener {
//                    if (pageIndex < (pdfRenderer?.pageCount ?: 0) - 1) {
//                        pageIndex++
//                    }
//                }
//            }
//            layout.addView(nextButton)
//
//            val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
//            pdfRenderer = PdfRenderer(fileDescriptor)
//
//            layout
//        },
//        update = { view ->
//            page?.close()
//            page = pdfRenderer?.openPage(pageIndex)
//            val bitmap = Bitmap.createBitmap(page?.width ?: 0, page?.height ?: 0, Bitmap.Config.ARGB_8888)
//            page?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
//            (view.getChildAt(0) as ImageView).setImageBitmap(bitmap)
//        }
//    )
//
//    DisposableEffect(Unit) {
//        onDispose {
//            page?.close()
//            pdfRenderer?.close()
//        }
//    }
//}

@Composable
fun PdfScreen(navController: NavController, adViewModel: AdViewModel) {//, userSignOut:()-> Unit//, market: String, filePath: String
//    val context = LocalContext.current
    val pdfLoadViewModel: PdfLoadViewModel = viewModel()
    val context = LocalContext.current
    pdfLoadViewModel.LoadPdfFile(adViewModel.filePath)

    Scaffold (
        topBar = { TopBar(navController,adViewModel,context.getString(R.string.pdfPage)) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = adViewModel.market,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp)
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
                            text = stringResource(R.string.loading),
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
                            horizontalAlignment = Alignment.CenterHorizontally
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
    )
}
