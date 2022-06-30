<!---Header--->
[![Android CI](https://github.com/renetik/renetik-android-core/workflows/Android%20CI/badge.svg)
](https://github.com/renetik/renetik-android-core/actions/workflows/android.yml)

# Renetik Android - Core

#### [https://github.com/renetik/renetik-android-core](https://github.com/renetik/renetik-android-core/)

#### [Documentation](https://renetik.github.io/renetik-android-core/)

Framework to enjoy, improve and speed up your application development while writing readable code.
Used as library in music production and performance app Renetik Instruments www.renetik.com as well
as in other projects.

```gradle
allprojects {
    repositories {
        // For master-SNAPSHOT
        maven { url 'https://github.com/renetik/maven-snapshot/raw/master/repository' }
        // For release builds
        maven { url 'https://github.com/renetik/maven/raw/master/repository' }
    }
}
```

Step 2. Add the dependency

```gradle
dependencies {
    implementation 'com.renetik.library:renetik-android-core:$renetik-android-version'
}
```
