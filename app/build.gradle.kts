plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.umc6th.kioki"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.umc6th.kioki"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }

}
dependencies {
    implementation("com.github.ome450901:SimpleRatingBar:1.5.1")
    implementation ("com.github.clans:fab:1.6.4")
    implementation ("com.tbuonomo:dotsindicator:5.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //circle imageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    // flexbox
    implementation ("com.google.android.flexbox:flexbox:3.0.0")
    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // 스와이프 구현을 위한 circleindicator
    implementation("me.relex:circleindicator:2.1.6")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    // 컬러피커 라이브러리
    implementation ("com.github.mrudultora:Colorpicker:1.2.0")

    // Room
    val room_version = "2.6.1"
    //implementation ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // OkHttp3
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.2")

}