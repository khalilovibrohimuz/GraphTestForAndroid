package uz.alien_dev.graphtestforandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien_dev.graphtestforandroid.databinding.ActivityPixel1Binding

class ActivityPixel1 : AppCompatActivity() {

    private lateinit var binding: ActivityPixel1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPixel1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}