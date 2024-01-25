package com.example.coffee1706.ui.screens.maps

import android.annotation.SuppressLint
import android.health.connect.datatypes.ExerciseRoute.Location
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffee1706.common.CoffeeButton
import com.example.coffee1706.common.CoffeeTopAppBar
import com.example.coffee1706.ui.screens.maps.components.CoffeeHouseCard
import com.example.coffee1706.ui.theme.BaseTextColor
import com.example.coffee1706.utils.clickableWithRipple


@Preview
@Composable
fun NearestCoffeeHouseScreenPreview() {
	NearestCoffeeHouseScreen(
		onClickMaps = { /*TODO*/ },
		onClickCoffee = { /*TODO*/ },
		onClickBack = {},
		uiState = LocationUiState()
	)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NearestCoffeeHouseScreen(
	modifier: Modifier = Modifier,
	onClickMaps: () -> Unit,
	onClickCoffee: (id: Int) -> Unit,
	onClickBack: () -> Unit,
	uiState: LocationUiState,
) {
	Scaffold(
		modifier = modifier
			.fillMaxSize()
			.systemBarsPadding(),
		topBar = {
			CoffeeTopAppBar(
				leadingIcon = {
					Icon(
						modifier = Modifier
							.padding(start = 28.dp)
							.clickableWithRipple(
								onClick = onClickBack
							),
						imageVector = Icons.Default.ArrowBack,
						contentDescription = null,
						tint = BaseTextColor,
					)
				},
				title = "Ближайшие кофейни",
			)
		},
		bottomBar = {
			CoffeeButton(
				modifier = modifier
					.padding(horizontal = 16.dp),
				onClick = onClickMaps,
				text = "На карте",
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
		) {
			items(
				items = uiState.coffeeHouseList,
			) {
				Spacer(modifier = Modifier.padding(top = 15.dp))
				CoffeeHouseCard(
					coffeeName = it.name,
					coffeeDistance = "1 км от вас",
					onClick = {
						onClickCoffee(it.id)
					},
				)
			}
		}
	}
}