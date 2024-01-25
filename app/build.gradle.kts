import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
	id("dagger.hilt.android.plugin")
	id("kotlin-parcelize")
	id("kotlinx-serialization")
}

android {
	namespace = "com.example.coffee1706"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.coffee1706"
		minSdk = 21
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.7.2")
	implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0-rc02")
	implementation(platform("androidx.compose:compose-bom:2023.10.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.compose.material:material")
	implementation("androidx.paging:paging-compose:3.2.1")
	implementation("androidx.compose.material:material-icons-extended:")
	implementation("androidx.compose.ui:ui-util")
	implementation("androidx.startup:startup-runtime:1.1.1")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.10.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")

	//ViewModel
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

	//Hilt
	implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
	kapt("com.google.dagger:hilt-compiler:2.48")
	implementation("com.google.dagger:hilt-android:2.48")

	//Coil
	implementation("io.coil-kt:coil-compose:2.4.0")
	implementation("io.coil-kt:coil-svg:2.5.0")

	//OkHttps
	implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
	implementation("com.squareup.okhttp3:okhttp:4.12.0")

	//Retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

	//Navigation
	implementation("androidx.navigation:navigation-compose:2.7.6")

	//DataStore
	implementation("androidx.datastore:datastore-preferences:1.0.0")

	//System UI controller
	implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

	//Yandex MapKit
	implementation("com.yandex.android:maps.mobile:4.4.0-full")
}