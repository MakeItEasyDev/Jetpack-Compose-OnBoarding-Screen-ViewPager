package com.jetpack.onboardscreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.onboardscreen.ui.theme.Gray200
import com.jetpack.onboardscreen.ui.theme.OnBoardScreenTheme
import kotlinx.coroutines.launch

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)

        setContent {
            OnBoardScreenTheme {
                Surface(color = MaterialTheme.colors.background) {
                    OnBoardScreen()
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun OnBoardScreen() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val onBoardViewModel : OnBoardViewModel = viewModel()
    val context = LocalContext.current
    val currentPage = onBoardViewModel.currentPage.collectAsState()

    Toast.makeText(context, "${currentPage.value}", Toast.LENGTH_SHORT).show()

    val pagerState = rememberPagerState(
        pageCount = onBoardItem.size,
        initialOffscreenLimit = 2,
        initialPage = 0,
        infiniteLoop = false
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            scope.launch {
                pagerState.animateScrollToPage(
                    page = currentPage.value
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Gray200)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        state = pagerState
                    ) { page ->
                        Column(
                            modifier = Modifier
                                .padding(top = 65.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = onBoardItem[page].image),
                                contentDescription = "OnBoardImage",
                                modifier = Modifier
                                    .size(250.dp)
                            )

                            Text(
                                text = onBoardItem[page].title,
                                modifier = Modifier
                                    .padding(top = 50.dp),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Text(
                                text = onBoardItem[page].desc,
                                modifier = Modifier
                                    .padding(30.dp),
                                color = Color.White,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    PagerIndicator(onBoardItem.size, pagerState.currentPage)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = if (pagerState.currentPage != 2 ) {
                            Arrangement.SpaceBetween
                        } else {
                            Arrangement.Center
                        }
                    ) {
                        if (pagerState.currentPage == 2) {
                            OutlinedButton(
                                onClick = {
                                    Toast.makeText(context, "Start the Screen", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(45.dp)
                            ) {
                                Text(
                                    text = "Get Started",
                                    modifier = Modifier.padding(
                                        vertical = 8.dp,
                                        horizontal = 40.dp
                                    ),
                                    color = Color.Black
                                )
                            }
                        } else {
                            Text(
                                text = "Skip",
                                color = Color.White,
                                modifier = Modifier.padding(start = 20.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = "Next",
                                color = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        onBoardViewModel.setCurrentPage(pagerState.currentPage + 1)
                                    }
                                    .padding(end = 20.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(size: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 60.dp)
    ) {
        repeat(size) {
            IndicateIcon(
                isSelected = it == currentPage
            )
        }
    }
}

@Composable
fun IndicateIcon(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp
    )

    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) {
                    Color.Green
                } else {
                    Color.Gray.copy(alpha = 0.5f)
                }
            )
    )
}






















