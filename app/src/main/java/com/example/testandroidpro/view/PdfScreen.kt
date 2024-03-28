package com.example.testandroidpro.view

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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

            val prevButton = Button(context).apply {
                text = "Previous"
                setOnClickListener {
                    if (pageIndex > 0) {
                        pageIndex--
                    }
                }
            }
            layout.addView(prevButton)

            val nextButton = Button(context).apply {
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
