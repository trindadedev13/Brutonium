plugins {
  alias(libs.plugins.agp.app)
}

android {
  namespace = "dev.trindadedev.brut"
  compileSdk = libs.versions.android.compileSdk.get().toInt()
  
  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    applicationId = "dev.trindadedev.brut"
    versionCode = 1
    versionName = "1.0"
    
    vectorDrawables.useSupportLibrary = true

    ndk {
      abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
    }
  }

  externalNativeBuild {
    cmake {
      path = file("CMakeLists.txt")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.android.jvm.get().toInt())
    targetCompatibility = JavaVersion.toVersion(libs.versions.android.jvm.get().toInt())
  }

  signingConfigs {
    create("release") {
      // temporary keystore
      storeFile = file(layout.buildDirectory.dir("../release_key.jks"))
      storePassword = "release_temp"
      keyAlias = "release_temp"
      keyPassword = "release_temp"
    }
    getByName("debug") {
      storeFile = file(layout.buildDirectory.dir("../testkey.keystore"))
      storePassword = "testkey"
      keyAlias = "testkey"
      keyPassword = "testkey"
    }
  }
    
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")
    }
    debug {
      applicationIdSuffix = ".debug"
    }
  }

  sourceSets["main"].java.srcDirs("src/main/java")
}