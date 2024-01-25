package com.example.coffee1706.ui.screens.pay

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coffee1706.common.CoffeeButton
import com.example.coffee1706.common.CoffeeTopAppBar
import com.example.coffee1706.common.Digit
import com.example.coffee1706.common.compareTo
import com.example.coffee1706.data.network_source.model.toCoffeeMenu
import com.example.coffee1706.ui.screens.menu.CoffeeMenuUiState
import com.example.coffee1706.ui.screens.menu.CoffeeMenuViewModel
import com.example.coffee1706.ui.theme.BaseTextColor
import com.example.coffee1706.ui.theme.Beige200
import com.example.coffee1706.utils.clickableWithRipple

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeePayScreen(
	modifier: Modifier = Modifier,
	onClickBack: () -> Unit,
	viewModel: CoffeeMenuViewModel = hiltViewModel()
) {
	var isPay by remember { mutableStateOf(false) }

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
				title = "Ваш заказ",
			)
		},
		bottomBar = {
			CoffeeButton(
				onClick = {
					isPay = true
				},
				text = "Оплатить",
			)
		},
		containerColor = Color.White,
	) {
		LazyColumn(
			modifier = modifier
				.fillMaxSize()
				.padding(
					top = it.calculateTopPadding(),
					start = 16.dp,
					end = 16.dp,
				),
			verticalArrangement = Arrangement.spacedBy(6.dp)
		) {
			items(
				items = uiState.cartList
			) {
				val count by remember { mutableIntStateOf(it.quantity) }

				Card(
					modifier = modifier,
					shape = RoundedCornerShape(5.dp),
					colors = CardDefaults.cardColors(
						containerColor = Beige200
					),
					elevation = CardDefaults.elevatedCardElevation(
						defaultElevation = 3.dp
					)
				) {
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(
								horizontal = 10.dp
							),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Column(
							modifier = Modifier,
						) {
							Text(
								modifier = Modifier
									.padding(
										top = 14.dp,
										bottom = 6.dp,
									),
								text = it.cartList.name,
								color = BaseTextColor,
								fontSize = 18.sp,
								fontWeight = FontWeight(700),
							)
							Text(
								modifier = Modifier
									.padding(
										bottom = 9.dp,
									),
								text = "${it.cartList.price} руб",
								color = BaseTextColor,
								fontSize = 16.sp,
								fontWeight = FontWeight(500),
							)
						}
						Row(
							modifier = Modifier,
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.spacedBy(12.dp)
						) {
							Text(
								text = "$count",
								fontSize = 16.sp,
								color = BaseTextColor,
							)
							IconButton(
								onClick = {
									viewModel.removeFromCart(it.toCoffeeMenu(), count)
								},
							) {
								Icon(
									imageVector = Icons.Outlined.Delete,
									contentDescription = null,
									tint = BaseTextColor,
								)
							}
						}
					}
				}


			}
			item {
				if (isPay) {
					Text(
						modifier = Modifier
							.fillMaxWidth(),
						text = "Время ожидания заказа\n" +
								"15 минут!\n" +
								"Спасибо, что выбрали нас!",
						fontSize = 24.sp,
						color = BaseTextColor,
						fontWeight = FontWeight(500),
						textAlign = TextAlign.Center
					)
				}
			}
		}
	}
}