package uz.alien_dev.graphtestforandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien_dev.graphtestforandroid.databinding.ActivityPixel2Binding

class ActivityPixel2 : AppCompatActivity() {

    private lateinit var binding: ActivityPixel2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPixel2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}