package com.example.testandroidpro.view

import android.annotation.SuppressLint
import android.graphics.Paint
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.testandroidpro.R
import com.example.testandroidpro.viewmodel.AdViewModel
import java.util.Locale
import androidx.compose.ui.text.TextLayoutResult



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(navController: NavController, adViewModel: AdViewModel) {
    val context = LocalContext.current
    Scaffold (
        topBar = { TopBar(navController,adViewModel,context.getString(R.string.homePage)) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val state = adViewModel.listState
                val density3 = LocalDensity.current
                val fontSize3 = remember { mutableStateOf(0.sp) }
                val adjusted = remember { mutableStateOf(false) }
                val welcomeString = stringResource(R.string.welcome)
                val spaceString = stringResource(R.string.string_space)
                val text1 = welcomeString + spaceString + adViewModel.userInfoStore.value.name
                val parentWidth = remember { mutableFloatStateOf(0f) }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.1f)
                        .onGloballyPositioned { layoutCoordinates ->
                            if(!adjusted.value) {
                                parentWidth.floatValue = with(density3) {
                                    layoutCoordinates.size.width.toDp().toPx() / density3.density
                                } * 0.8f
                                val newFontSize = with(density3) {
                                    layoutCoordinates.size.height.toDp().toPx() / density3.density
                                } * 0.8f
                                fontSize3.value = newFontSize.sp
                            }
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text1,//stringResource(R.string.welcome) + stringResource(R.string.string_space) + adViewModel.userName,
                        fontSize = fontSize3.value,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        onTextLayout = { layoutResult: TextLayoutResult ->
                            val lines = layoutResult.lineCount
                            val paint = Paint().apply {
                                textSize = fontSize3.value.value
                            }
                            var textWidth = paint.measureText(text1+context.getString(R.string.string_space))
                            if (lines > 1 && fontSize3.value > 16.sp || textWidth > parentWidth.floatValue) {
                                adjusted.value = true
                                while (textWidth > parentWidth.floatValue && fontSize3.value > 16.sp) {

                                    fontSize3.value = with(density3) { fontSize3.value.toPx() / density - 1.sp.toPx() / density }.sp
                                    paint.textSize = fontSize3.value.value
                                    textWidth = paint.measureText(text1+context.getString(R.string.string_space))
                                }
                            }
                        },
                    )

                }
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
                                                        colors = listOf(
                                                            Color.Transparent,
                                                            Color.LightGray
                                                        ),
                                                        startY = 0f,
                                                        endY = shadowSize.toPx()
                                                    ),
                                                    size = Size(
                                                        size.width - shadowSize.toPx() * 2,
                                                        shadowSize.toPx()
                                                    ),
                                                    topLeft = Offset(shadowSize.toPx(), 0f)
                                                )
                                                drawRect(
                                                    brush = Brush.horizontalGradient(
                                                        colors = listOf(
                                                            Color.Transparent,
                                                            Color.LightGray
                                                        ),
                                                        startX = 0f,
                                                        endX = shadowSize.toPx()
                                                    ),
                                                    size = Size(
                                                        shadowSize.toPx(),
                                                        size.height - shadowSize.toPx() * 2
                                                    ),
                                                    topLeft = Offset(0f, shadowSize.toPx())
                                                )
                                                drawRect(
                                                    brush = Brush.verticalGradient(
                                                        colors = listOf(
                                                            Color.LightGray,
                                                            Color.Transparent
                                                        ),
                                                        startY = size.height - shadowSize.toPx(),
                                                        endY = size.height
                                                    ),
                                                    size = Size(
                                                        size.width - shadowSize.toPx() * 2,
                                                        shadowSize.toPx()
                                                    ),
                                                    topLeft = Offset(
                                                        shadowSize.toPx(),
                                                        size.height - shadowSize.toPx()
                                                    )
                                                )
                                                drawRect(
                                                    brush = Brush.horizontalGradient(
                                                        colors = listOf(
                                                            Color.LightGray,
                                                            Color.Transparent
                                                        ),
                                                        startX = size.width - shadowSize.toPx(),
                                                        endX = size.width
                                                    ),
                                                    size = Size(
                                                        shadowSize.toPx(),
                                                        size.height - shadowSize.toPx() * 2
                                                    ),
                                                    topLeft = Offset(
                                                        size.width - shadowSize.toPx(),
                                                        shadowSize.toPx()
                                                    )
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            Color.LightGray,
                                                            Color.Transparent
                                                        ),
                                                        center = Offset(
                                                            shadowSize.toPx(),
                                                            shadowSize.toPx()
                                                        ),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -180f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(0.dp.toPx(), 0.dp.toPx()),
                                                    size = Size(
                                                        shadowSize.toPx() * 2,
                                                        shadowSize.toPx() * 2
                                                    ),
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            Color.LightGray,
                                                            Color.Transparent
                                                        ),
                                                        center = Offset(
                                                            shadowSize.toPx(),
                                                            size.height - shadowSize.toPx()
                                                        ),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -270f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(
                                                        0.dp.toPx(),
                                                        size.height - shadowSize.toPx() * 2
                                                    ),
                                                    size = Size(
                                                        shadowSize.toPx() * 2,
                                                        shadowSize.toPx() * 2
                                                    ),
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            Color.LightGray,
                                                            Color.Transparent
                                                        ),
                                                        center = Offset(
                                                            size.width - shadowSize.toPx(),
                                                            size.height - shadowSize.toPx()
                                                        ),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -0f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(
                                                        size.width - shadowSize.toPx() * 2,
                                                        size.height - shadowSize.toPx() * 2
                                                    ),
                                                    size = Size(
                                                        shadowSize.toPx() * 2,
                                                        shadowSize.toPx() * 2
                                                    ),
                                                )
                                                drawArc(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            Color.LightGray,
                                                            Color.Transparent
                                                        ),
                                                        center = Offset(
                                                            size.width - shadowSize.toPx(),
                                                            shadowSize.toPx()
                                                        ),
                                                        radius = shadowSize.toPx()
                                                    ),
                                                    startAngle = -90f,
                                                    sweepAngle = 90f,
                                                    useCenter = true,
                                                    topLeft = Offset(
                                                        size.width - shadowSize.toPx() * 2,
                                                        0.dp.toPx()
                                                    ),
                                                    size = Size(
                                                        shadowSize.toPx() * 2,
                                                        shadowSize.toPx() * 2
                                                    ),
                                                )
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            modifier = Modifier

                                                .fillMaxSize()
                                                .padding(shadowSize)
                                                .clickable {
                                                    adViewModel.filePath = document.ads[0]
                                                        .getString(context.getString(R.string.data_attachment))
                                                        .toString()
                                                    Log.d("test click", "click success")
                                                    adViewModel.market = document.supplier.id
                                                    navController.navigate(context.getString(R.string.screen_pdf))
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {

                                            Row(
                                                modifier = Modifier
                                                    .weight(1.5f)
                                                    .padding(8.dp)
                                                    .fillMaxWidth(),
                                            ) {
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
                                            val density1 = LocalDensity.current
                                            val fontSize1 = remember { mutableStateOf(0.sp) }
                                            Row(
                                                modifier = Modifier
                                                    .weight(1.5f)
                                                    .fillMaxWidth()
                                                    .onGloballyPositioned{ layoutCoordinates ->
                                                        val newFontSize = with(density1) { layoutCoordinates.size.height.toDp().toPx() / density1.density } * 0.5f
                                                        fontSize1.value = newFontSize.sp
                                                    },
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                document.ads[0].getString(context.getString(R.string.data_adName))?.let { it2 ->
                                                    Text(
                                                        text = it2,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(1.dp),
                                                        textAlign = TextAlign.Center,
                                                        fontSize = fontSize1.value,
                                                        color = Color(0xFF69BD00)
                                                    )
                                                }
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .height(2.dp)
                                                    .fillMaxWidth(),
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
                                            val density2 = LocalDensity.current
                                            val fontSize2 = remember { mutableStateOf(0.sp) }
                                            Row(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxWidth()
                                                    .onGloballyPositioned{ layoutCoordinates ->
                                                        val newFontSize = with(density2) { layoutCoordinates.size.height.toDp().toPx() / density2.density } * 0.38f
                                                        fontSize2.value = newFontSize.sp
                                                    },
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                val timestampStart = document.ads[0].getTimestamp(context.getString(R.string.data_periodStart))
                                                timestampStart?.let { periodStart ->
                                                    val date = periodStart.toDate()
                                                    val formatter = SimpleDateFormat(context.getString(
                                                        R.string.time_form
                                                    ), Locale.getDefault())
                                                    val formattedDate = formatter.format(date)
                                                    Text(
                                                        text = formattedDate,
                                                        modifier = Modifier
                                                            .padding(1.dp),
                                                        textAlign = TextAlign.Center,
                                                        fontSize = fontSize2.value
                                                    )
                                                }
                                                Text(
                                                    text = context.getString(R.string.date_separator),
                                                    modifier = Modifier
                                                        .padding(1.dp),
                                                    textAlign = TextAlign.Center,
                                                    fontSize = fontSize2.value
                                                )
                                                val timestampStop = document.ads[0].getTimestamp(
                                                    context.getString(
                                                        R.string.data_periodStop
                                                    ))
                                                timestampStop?.let { periodStart ->
                                                    val date = periodStart.toDate()
                                                    val formatter = SimpleDateFormat(
                                                        context.getString(
                                                            R.string.time_form
                                                        ), Locale.getDefault())
                                                    val formattedDate = formatter.format(date)
                                                    Text(
                                                        text = formattedDate,
                                                        modifier = Modifier
                                                            .padding(1.dp),
                                                        textAlign = TextAlign.Center,
                                                        fontSize = fontSize2.value
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