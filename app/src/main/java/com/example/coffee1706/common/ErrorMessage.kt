package com.example.coffee1706.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorMessage(
	modifier: Modifier = Modifier,
	text: String
) {
	Text(
		modifier = modifier,
		text = text,
		color = Color.Red,
		style = MaterialTheme.typography.bodyLarge,
		textAlign = TextAlign.Center
	)
}