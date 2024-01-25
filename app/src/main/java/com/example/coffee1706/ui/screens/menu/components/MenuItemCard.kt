package com.example.coffee1706.ui.screens.menu.components
//
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.animateContentSize
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.animation.with
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import com.example.coffee1706.R
//import com.example.coffee1706.common.Counter
//import com.example.coffee1706.common.Digit
//import com.example.coffee1706.common.compareTo
//import com.example.coffee1706.common.placeholder.placeholder
//import com.example.coffee1706.ui.theme.BaseTextColor
//import com.example.coffee1706.ui.theme.Beige200
//
//
//@Preview
//@Composable
//fun MenuItemCardPreview() {
//	Row {
//		MenuItemCard(
//			model = null,
//			coffeeName = "Эспрессо",
//			coffeePrice = "200 руб",
//		)
//		MenuItemCard(
//			model = null,
//			coffeeName = "Эспрессо",
//			coffeePrice = "200 руб",
//		)
//	}
//}
//
//@Composable
//fun MenuItemCard(
//	modifier: Modifier = Modifier,
//	isLoading: Boolean = false,
//	model: Any?,
//	coffeeName: String,
//	coffeePrice: String,
//) {
//	var count by remember { mutableStateOf(0) }
//
//	Card(
//		modifier = modifier
//			.padding(12.dp),
//		shape = RoundedCornerShape(5.dp),
//		colors = CardDefaults.cardColors(
//			containerColor = Beige200
//		),
//		elevation = CardDefaults.elevatedCardElevation(
//			defaultElevation = 3.dp
//		)
//	) {
//		Box(
//			modifier = modifier
//				.fillMaxWidth()
//				.aspectRatio(165 / 137f)
//				.clip(RoundedCornerShape(5.dp))
//				.placeholder(isLoading),
//		) {
//			AsyncImage(
//				modifier = Modifier
//					.fillMaxSize(),
//				model = model,
//				contentScale = ContentScale.Crop,
//				contentDescription = null,
//				placeholder = painterResource(R.drawable.place_holder),
//				error = painterResource(R.drawable.place_holder)
//			)
//		}
//		Text(
//			modifier = Modifier
//				.padding(
//					top = 10.dp,
//					bottom = 12.dp,
//					start = 11.dp,
//				),
//			text = coffeeName,
//			color = BaseTextColor,
//			fontSize = 15.sp,
//			fontWeight = FontWeight(500),
//		)
//		Row(
//			modifier = Modifier
//				.fillMaxWidth()
//				.padding(
//					start = 11.dp,
//				),
//			horizontalArrangement = Arrangement.spacedBy(11.dp),
//			verticalAlignment = Alignment.CenterVertically,
//		) {
//			Text(
//				text = coffeePrice,
//				color = BaseTextColor,
//				fontWeight = FontWeight(700),
//			)
//			Row(
//				modifier = Modifier,
//				verticalAlignment = Alignment.CenterVertically,
//				horizontalArrangement = Arrangement.Center
//			) {
//				Button(
//					onClick = { if (count > 0) count-- },
//					modifier = Modifier
//						.size(24.dp),
//					colors = ButtonDefaults.buttonColors(
//						contentColor = BaseTextColor.copy(0.5f),
//						containerColor = Beige200,
//					),
//					contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
//				) {
//					Text(
//						text = "—",
//					)
//				}
//
//				Row(
//					modifier = Modifier
//						.animateContentSize()
//						.padding(horizontal = 3.dp)
//						.size(24.dp),
//					horizontalArrangement = Arrangement.Center,
//					verticalAlignment = Alignment.CenterVertically,
//				) {
//					count.toString()
//						.mapIndexed { index, c -> Digit(c, count, index) }
//						.forEach { digit ->
//							AnimatedContent(
//								targetState = digit,
//								transitionSpec = {
//									if (targetState > initialState) {
//										slideInVertically { -it } with slideOutVertically { it }
//									} else {
//										slideInVertically { it } with slideOutVertically { -it }
//									}
//								}, label = ""
//							) { digit ->
//								Text(
//									"${digit.digitChar}",
//									textAlign = TextAlign.Center,
//									color = BaseTextColor,
//									fontSize = 14.sp,
//								)
//							}
//						}
//				}
//				Button(
//					onClick = { count++ },
//					modifier = Modifier
//						.size(24.dp),
//					colors = ButtonDefaults.buttonColors(
//						contentColor = BaseTextColor.copy(0.5f),
//						containerColor = Beige200,
//					),
//					contentPadding = PaddingValues(horizontal = 0.dp)
//				) {
//					Text(text = "+")
//				}
//			}
//		}
//	}
//}