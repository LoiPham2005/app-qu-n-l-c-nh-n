plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "poly.edu.vn.asm"
    compileSdk = 34

    defaultConfig {
        applicationId = "poly.edu.vn.asm"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
// lưu thông tin đăng nhập vào firebase
    implementation("com.google.firebase:firebase-auth")
// chỉnh ảnh bo góc
    implementation ("androidx.cardview:cardview:1.0.0")
// cho phép chỉnh ảnh bo góc
    implementation ("com.makeramen:roundedimageview:2.3.0")
// truy cập firebase storage
    implementation("com.google.firebase:firebase-firestore")

    implementation("com.google.firebase:firebase-storage")

    implementation ("com.google.firebase:firebase-firestore:24.2.0")
    // hoặc nếu bạn sử dụng Realtime Database
     implementation ("com.google.firebase:firebase-database:20.0.5")

    implementation ("com.google.firebase:firebase-storage:20.0.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")


}