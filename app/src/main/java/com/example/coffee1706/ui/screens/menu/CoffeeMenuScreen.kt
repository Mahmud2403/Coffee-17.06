package com.example.coffee1706.ui.screens.menu

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.coffee1706.R
import com.example.coffee1706.common.CoffeeButton
import com.example.coffee1706.common.CoffeeTopAppBar
import com.example.coffee1706.common.Digit
import com.example.coffee1706.common.compareTo
import com.example.coffee1706.data.network_source.model.CoffeeMenuDto
import com.example.coffee1706.data.network_source.model.toCoffeeMenu
import com.example.coffee1706.domain.data.CoffeeMenu
import com.example.coffee1706.ui.theme.BaseTextColor
import com.example.coffee1706.ui.theme.Beige200
import com.example.coffee1706.utils.clickableWithRipple
import kotlin.reflect.KFunction1

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeeMenuScreen(
	modifier: Modifier = Modifier,
	onClickBack: () -> Unit,
	onClickPayScreen: () -> Unit,
	viewModel: CoffeeMenuViewModel = hiltViewModel(),
) {
	val uiState by viewModel.menuUiState.collectAsStateWithLifecycle()

	Scaffold(
		modifier = modifier
			.fillMaxSize()
			.systemBarsPadding(),
		topBar = {
			CoffeeTopAppBar(
				leadingIcon = {
					Icon(
						modifier = Modifier
							.clickableWithRipple(
								onClick = onClickBack
							),
						imageVector = Icons.Default.ArrowBack,
						contentDescription = null,
						tint = BaseTextColor,
					)
				},
				title = "Меню",
			)
		},
		bottomBar = {
			CoffeeButton(
				onClick = onClickPayScreen,
				text = "Перейти к оплате",
			)
		},
		containerColor = Color.White,
	) {
		LazyVerticalGrid(
			modifier = modifier
				.fillMaxSize()
				.padding(
					top = it.calculateTopPadding(),
					start = 16.dp,
					end = 16.dp,
				),
			columns = GridCells.Fixed(2)
		) {
			items(
				items = uiState.menuList
			) { coffeeMenu ->
				var count by remember { mutableIntStateOf(0) }

				Card(
					modifier = modifier
						.padding(12.dp),
					shape = RoundedCornerShape(5.dp),
					colors = CardDefaults.cardColors(
						containerColor = Beige200
					),
					elevation = CardDefaults.elevatedCardElevation(
						defaultElevation = 3.dp
					)
				) {
					Box(
						modifier = modifier
							.fillMaxWidth()
							.aspectRatio(165 / 137f)
							.clip(RoundedCornerShape(5.dp))
					) {
						AsyncImage(
							modifier = Modifier
								.fillMaxSize(),
							model = coffeeMenu.imageURL,
							contentScale = ContentScale.Crop,
							contentDescription = null,
							placeholder = painterResource(R.drawable.place_holder),
							error = painterResource(R.drawable.place_holder)
						)
					}
					Text(
						modifier = Modifier
							.padding(
								top = 10.dp,
								bottom = 12.dp,
								start = 11.dp,
							),
						text = coffeeMenu.name,
						color = BaseTextColor,
						fontSize = 15.sp,
						fontWeight = FontWeight(500),
					)
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(
								start = 11.dp,
							),
						horizontalArrangement = Arrangement.spacedBy(11.dp),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Text(
							text = "${coffeeMenu.price} руб",
							color = BaseTextColor,
							fontWeight = FontWeight(700),
						)
						Row(
							modifier = Modifier,
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.Center
						) {
							Button(
								onClick = {
									if (count > 0) {
										count--
										viewModel.removeFromCart(coffeeMenu, count)
									}
								},
								modifier = Modifier
									.size(24.dp),
								colors = ButtonDefaults.buttonColors(
									contentColor = BaseTextColor.copy(0.5f),
									containerColor = Beige200,
								),
								contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
							) {
								Text(
									text = "—",
								)
							}
							Row(
								modifier = Modifier
									.animateContentSize()
									.padding(horizontal = 3.dp)
									.size(24.dp),
								horizontalArrangement = Arrangement.Center,
								verticalAlignment = Alignment.CenterVertically,
							) {
								count.toString()
									.mapIndexed { index, c -> Digit(c, count, index) }
									.forEach { digit ->
										AnimatedContent(
											targetState = digit,
											transitionSpec = {
												if (targetState > initialState) {
													slideInVertically { -it } with slideOutVertically { it }
												} else {
													slideInVertically { it } with slideOutVertically { -it }
												}
											}, label = ""
										) { digit ->
											Text(
												"${digit.digitChar}",
												textAlign = TextAlign.Center,
												color = BaseTextColor,
												fontSize = 14.sp,
											)
										}
									}
							}
							Button(
								onClick = {
									count++
									viewModel.addToCart(coffeeMenu, count)
								},
								modifier = Modifier
									.size(24.dp),
								colors = ButtonDefaults.buttonColors(
									contentColor = BaseTextColor.copy(0.5f),
									containerColor = Beige200,
								),
								contentPadding = PaddingValues(horizontal = 0.dp)
							) {
								Text(text = "+")
							}
						}
					}
				}
			}
		}
	}
}