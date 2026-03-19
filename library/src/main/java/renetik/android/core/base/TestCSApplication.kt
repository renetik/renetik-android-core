package renetik.android.core.base

import androidx.appcompat.app.AppCompatActivity

class TestCSApplication : CSApplication<AppCompatActivity>() {
    override val activityClass = AppCompatActivity::class
}