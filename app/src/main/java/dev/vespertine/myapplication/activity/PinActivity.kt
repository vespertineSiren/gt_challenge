package dev.vespertine.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.android.AndroidInjection
import dev.vespertine.myapplication.R
import kotlinx.android.synthetic.main.app_bar_base.*

class PinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)



        toolbar.title = "Test"

        AndroidInjection.inject(this)

    }
}
