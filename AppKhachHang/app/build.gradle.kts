plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.appkhachhang"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appkhachhang"
        minSdk = 19
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database")
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation ("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-storage:20.3.0")
   // implementation(fileTree(mapOf("dir" to "D:\\FPT_Polytechnic\\DATN_PhoneHouse\\AppKhachHang\\app\\libs", "include" to listOf("*.aar", "*.jar"))))
    //zalo
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
//    implementation(fileTree(mapOf(
//        "dir" to "D:\\PhoneHouse_DoAnTotNghiep\\DATN_PhoneHouse\\AppKhachHang\\app\\libs",
//        "include" to listOf("*.aar", "*.jar")
//    )))
//    implementation(fileTree(mapOf(
//        "dir" to "D:\\ZaloPay",
//        "include" to listOf("*.aar", "*.jar")
//    )))
    implementation(fileTree(mapOf("dir" to "D:\\FPT_Polytechnic\\DATN\\DATN_PhoneHouse\\AppKhachHang\\app\\libs", "include" to listOf("*.aar", "*.jar"))))
    implementation(fileTree(mapOf(
        "dir" to "D:\\abc\\DATN_PhoneHouse\\AppKhachHang\\app\\libs",
        "include" to listOf("*.aar", "*.jar"),
    )))
//    implementation(fileTree(mapOf(
//        "dir" to "/Users/yenyen/Desktop/DATN_PhoneHouse/AppKhachHang/app/libs",
//        "include" to listOf("*.aar", "*.jar")
//    )))
//    implementation(fileTree(mapOf(
//        "dir" to "D:\\\\ZaloPay",
//        "include" to listOf("*.aar", "*.jar")
//    )))

    implementation(fileTree(mapOf(
        "dir" to "/Users/yenyen/Desktop/DATN_PhoneHouse/AppKhachHang/app/libs",
        "include" to listOf("*.aar", "*.jar")

    )))
    implementation("androidx.activity:activity:1.8.0")



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.google.android.gms:play-services-auth:19.0.0")
    implementation ("com.google.android.material:material:1.3.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.6.0")

    implementation("androidx.recyclerview:recyclerview:1.2.1")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.appcompat:appcompat:1.4.1")

    implementation ("com.github.MohammedAlaaMorsi:RangeSeekBar:1.0.6")

    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.0")
    implementation ("com.github.momo-wallet:mobile-sdk:1.0.7")

    //circle image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //barchart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
//    implementation ("com.github.Inconnu08:android-ratingreviews:1.2.0")


}