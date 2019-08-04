package dev.vespertine.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.vespertine.myapplication.R
import kotlinx.android.synthetic.main.app_bar_base.*

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        toolbar.title = "Test"

    }
}
