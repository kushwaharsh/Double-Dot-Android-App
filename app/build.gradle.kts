plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.doubledotproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.doubledotproject"
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
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
//    implementation(libs.androidx.runtime.saved.instance.state)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Lottie Dependency
    implementation ("com.airbnb.android:lottie:6.4.1")

    // for mvvm , coroutine lifecycle
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    implementation ("androidx.activity:activity-ktx:1.8.2")

    //Pin View Dependency
    implementation ("io.github.chaosleung:pinview:1.4.4")
    //Country Code Picker Dependency
    implementation ("com.hbb20:ccp:2.6.0")
    //Retrofit and Gson Dependency
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //ViewModel and LiveData Dependency
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
    //Coroutine Dependency
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    //OkHTTP Dependency
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    //Dependency to track location(Lati & Long)
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    //View Pager Adapter
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    //glide Dependency
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //duo navigation drawer dependency for sliding drawer
    implementation ("com.yarolegovich:sliding-root-nav:1.1.1")
    //circular image view dependency
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //Material dependency



}