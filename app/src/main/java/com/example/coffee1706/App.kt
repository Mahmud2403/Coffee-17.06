package com.example.coffee1706

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
	override fun onCreate() {
		MapKitFactory.setApiKey("ed85d1d2-c0b0-4642-a201-bd55f2d04472")
		super.onCreate()
	}
}