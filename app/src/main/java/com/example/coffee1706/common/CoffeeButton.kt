package com.example.coffee1706.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.coffee1706.ui.theme.BaseButtonColor


@Preview
@Composable
fun CoffeeButtonPreview() {
	CoffeeButton(
		onClick = {  },
		text = "Регистрация"
	)
}

@Composable
fun CoffeeButton(
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	text: String,
) {
	Button(
		modifier = modifier
			.fillMaxWidth(),
		onClick = onClick,
		colors = ButtonDefaults.buttonColors(
			containerColor = BaseButtonColor,
			contentColor = Color(0xFFF6E5D1),
		),
	) {
		Text(
			text = text,
			fontSize = 18.sp,
			fontWeight = FontWeight(700),
			maxLines = 1,
		)
	}
}