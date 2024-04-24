package com.example.testandroidpro.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true)
                    .spacing(0)
                    .autoSpacing(false)
                    .pageFitPolicy(FitPolicy.WIDTH)
                    .load()
            }
        },
        modifier = modifier
    )
}


@Composable
fun PdfScreen(navController: NavController, adViewModel: AdViewModel) {

    val pdfLoadViewModel: PdfLoadViewModel = viewModel()
    val context = LocalContext.current
    pdfLoadViewModel.LoadPdfFile(adViewModel.filePath)

    Scaffold (
        topBar = { TopBar(navController,adViewModel,context.getString(R.string.pdfPage)) },
        content = { it ->
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
                                .weight(0.15f)
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
                            .let {
                                if (stringResource(R.string.borderDebug) == "true") it.border(
                                    1.dp, Color.Red
                                ) else it
                            }
                            .weight(1f)
                    ) {
                        pdfLoadViewModel.pdfFile.value?.let {//pdfLoadViewModel.
                            PdfViewer(
                                it, Modifier
                                    .fillMaxSize()
                                    .padding(6.dp)
                                    .clipToBounds()
                            )
                        }
                    }
                }
            }
        }
    )
}
