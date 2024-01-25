package com.example.coffee1706.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.coffee1706.ui.theme.BaseTextColor
import com.example.coffee1706.utils.clickableWithRipple

@Preview
@Composable
fun CoffeeTopBarLogIn() {
	CoffeeTopAppBar(
		title = "Вход",
	)
}

@Preview
@Composable
fun CoffeeTopBarMap() {
	CoffeeTopAppBar(
		leadingIcon = {
			Icon(
				imageVector = Icons.Default.ArrowBack,
				contentDescription = null,
				tint = BaseTextColor,
			)
		},
		title = "Карта",
	)
}

@Composable
fun CoffeeTopAppBar(
	modifier: Modifier = Modifier,
	title: String,
	leadingIcon: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
) {
	TopAppBar(
		modifier = modifier
			.fillMaxWidth(),
		backgroundColor = Color.White,
	) {
		leadingIcon?.invoke()
		Text(
			modifier = Modifier
				.fillMaxWidth(),
			text = title,
			fontSize = 18.sp,
			color = BaseTextColor,
			textAlign = TextAlign.Center,
			fontWeight = FontWeight(700),
		)
		trailingIcon?.invoke()
	}
}