package com.example.coffee1706.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee1706.ui.theme.BaseTextColor
import com.example.coffee1706.ui.theme.Beige200

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Counter() {
	var count by remember { mutableStateOf(0) }
	Row(
		modifier = Modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		Button(
			onClick = { if (count > 0) count-- },
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
			onClick = { count++ },
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

data class Digit(val digitChar: Char, val fullNumber: Int, val place: Int) {
	override fun equals(other: Any?): Boolean {
		return when (other) {
			is Digit -> digitChar == other.digitChar
			else -> super.equals(other)
		}
	}
}

operator fun Digit.compareTo(other: Digit): Int {
	return fullNumber.compareTo(other.fullNumber)
}