package com.example.coffee1706.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.clickableWithRipple(
	radius: Dp = 18.dp,
	color: Color = Color.Unspecified,
	enabled: Boolean = true,
	bounded: Boolean = false,
	onClick: () -> Unit,
) = composed {
	then(
		Modifier.clickable(
			interactionSource = remember { MutableInteractionSource() },
			indication = rememberRipple(radius = radius, bounded = bounded, color = color),
			enabled = enabled,
			onClick = onClick
		)
	)
}