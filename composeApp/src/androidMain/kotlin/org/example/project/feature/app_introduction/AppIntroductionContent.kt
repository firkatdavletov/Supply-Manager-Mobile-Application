package org.example.project.feature.app_introduction

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.R
import org.example.project.features.app_introduction.AppIntroductionComponent
import org.example.project.features.app_introduction.AppIntroductionViewEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppIntroductionContent(
    component: AppIntroductionComponent,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })

    BackHandler {
        val currentPage = pagerState.currentPage
        if (currentPage != 0) {
            scope.launch {
                pagerState.animateScrollToPage(currentPage - 1)
            }
        }
    }
    Scaffold(
        contentWindowInsets = WindowInsets(top = 48.dp)
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        component.onEvent(AppIntroductionViewEvent.OnContinue)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF006aFF),
                    )
                ) {
                    Text(
                        text = stringResource(R.string.continue_button_text)
                    )
                }
            }
            HorizontalPager(
                state = pagerState
            ) { page ->
                Column {
                    Image(
                        modifier = Modifier
                            .height(358.dp)
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp)),
                        painter = painterResource(R.drawable.villa),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.app_introduction_title),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.app_introduction_text)
                    )
                }
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
            Spacer(Modifier.height(70.dp))
            Button(
                modifier = Modifier
                    .sizeIn(maxWidth = 232.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    val currentPage = pagerState.currentPage
                    val nextPage = currentPage + 1
                    if (nextPage < pagerState.pageCount) {
                        scope.launch {
                            pagerState.animateScrollToPage(page = nextPage)
                        }
                    } else {
                        component.onEvent(AppIntroductionViewEvent.OnContinue)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006aFF),
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Continue",
                )
            }
        }
    }

}