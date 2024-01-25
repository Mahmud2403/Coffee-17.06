package com.example.coffee1706.ui.screens.maps.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee1706.ui.theme.BaseTextColor
import com.example.coffee1706.ui.theme.Beige200


@Preview(showBackground = true)
@Composable
fun CoffeeCardPreview() {
	Box(
		modifier = Modifier.fillMaxSize().padding(8.dp),
	) {
		CoffeeHouseCard(
			coffeeName = "BEDOEV COFFEE",
			coffeeDistance = "1 км от вас",
			onClick = {},
		)
	}
}

@Composable
fun CoffeeHouseCard(
	modifier: Modifier = Modifier,
	coffeeName: String,
	coffeeDistance: String,
	onClick: () -> Unit,
) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.clickable(
				onClick = onClick,
			),
		shape = RoundedCornerShape(5.dp),
		colors = CardDefaults.cardColors(
			containerColor = Beige200
		),
		elevation = CardDefaults.elevatedCardElevation(
			defaultElevation = 3.dp
		)
	) {
		Column(
			modifier = modifier
				.fillMaxWidth()
				.padding(
					top = 14.dp,
					bottom = 9.dp,
					start = 10.dp,
				)
		) {
			Text(
				text = coffeeName,
				fontSize = 18.sp,
				color = BaseTextColor,
				fontWeight = FontWeight(700)
			)
			Spacer(modifier = Modifier.padding(top = 6.dp))
			Text(
				text = coffeeDistance,
				color = BaseTextColor,
				fontSize = 14.sp,
			)
		}
	}
}