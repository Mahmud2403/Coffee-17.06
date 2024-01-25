package com.example.coffee1706.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee1706.ui.theme.BaseTextColor


@Preview(
	showBackground = true
)
@Composable
fun CoffeeTextFieldPreviewEmail() {
	CoffeeTextField(
		value = "",
		onValueChange = {},
		title = "e-mail",
		placeholder = "example@example.ru"
	)
}

@Preview(
	showBackground = true
)
@Composable
fun CoffeeTextFieldPassword() {
	CoffeeTextField(
		value = "",
		onValueChange = {},
		title = "Пароль",
		trailingIcon = {
			Icon(
				imageVector = Icons.Default.RemoveRedEye,
				contentDescription = null,
				tint = Color.Gray
			)
		},
		placeholder = "password",
	)
}

@Composable
fun CoffeeTextField(
	modifier: Modifier = Modifier,
	value: String,
	onValueChange: (String) -> Unit,
	trailingIcon: @Composable (() -> Unit)? = null,
	title: String,
	placeholder: String,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	visualTransformation: VisualTransformation = VisualTransformation.None,
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
	) {
		Text(
			text = title,
			color = BaseTextColor,
			fontSize = 15.sp,
		)
		Spacer(modifier = Modifier.padding(top = 8.dp))
		OutlinedTextField(
			modifier = modifier
				.fillMaxWidth()
				.border(
					width = 2.dp,
					color = BaseTextColor,
					shape = RoundedCornerShape(24.5.dp),
				),
			value = value,
			onValueChange = onValueChange,
			placeholder = {
				Text(
					text = placeholder,
					fontSize = 18.sp,
					color = Color(0xFFAF9479),
				)
			},
			keyboardOptions = keyboardOptions,
			visualTransformation = visualTransformation,
			trailingIcon = trailingIcon,
			colors = OutlinedTextFieldDefaults.colors(
				unfocusedContainerColor = Color.White,
				focusedContainerColor = Color.White,
				focusedTextColor = Color.Black,
				unfocusedTextColor = Color.Black,
			),
			shape = RoundedCornerShape(24.5.dp),
			maxLines = 1,
		)
	}
}