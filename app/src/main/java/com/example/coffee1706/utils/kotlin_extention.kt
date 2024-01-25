package com.example.coffee1706.utils

import android.util.Patterns

fun Boolean.toggle() = !this

fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(this.replace(" ", "")).matches()
