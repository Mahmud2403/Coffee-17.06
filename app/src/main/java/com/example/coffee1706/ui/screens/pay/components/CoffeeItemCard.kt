package com.example.coffee1706.ui.screens.pay.components
//
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.animateContentSize
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.animation.with
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
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
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.coffee1706.common.Counter
//import com.example.coffee1706.common.Digit
//import com.example.coffee1706.common.compareTo
//import com.example.coffee1706.ui.theme.BaseTextColor
//import com.example.coffee1706.ui.theme.Beige200
//
//
//@Preview
//@Composable
//fun CoffeeItemCardPreview() {
//	CoffeeItemCard(
//		coffeeName = "Капучино",
//		coffeePrice = "200 руб",
//	)
//}
//
//@Composable
//fun CoffeeItemCard(
//	modifier: Modifier = Modifier,
//	coffeeName: String,
//	coffeePrice: String,
//) {
//	Card(
//		modifier = modifier,
//		shape = RoundedCornerShape(5.dp),
//		colors = CardDefaults.cardColors(
//			containerColor = Beige200
//		),
//		elevation = CardDefaults.elevatedCardElevation(
//			defaultElevation = 3.dp
//		)
//	){
//		Row(
//			modifier = Modifier
//				.fillMaxWidth()
//				.padding(
//					horizontal = 10.dp
//				),
//			horizontalArrangement = Arrangement.SpaceBetween,
//			verticalAlignment = Alignment.CenterVertically
//		) {
//			Column(
//				modifier = Modifier,
//			) {
//				Text(
//					modifier = Modifier
//						.padding(
//							top = 14.dp,
//							bottom = 6.dp,
//						),
//					text = coffeeName,
//					color = BaseTextColor,
//					fontSize = 18.sp,
//					fontWeight = FontWeight(700),
//				)
//				Text(
//					modifier = Modifier
//						.padding(
//							bottom = 9.dp,
//						),
//					text = coffeePrice,
//					color = BaseTextColor,
//					fontSize = 16.sp,
//					fontWeight = FontWeight(500),
//				)
//			}
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
//		}
//	}
//}