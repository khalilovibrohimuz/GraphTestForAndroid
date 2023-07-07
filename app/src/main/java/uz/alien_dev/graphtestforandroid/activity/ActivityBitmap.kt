package uz.alien_dev.graphtestforandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien_dev.graphtestforandroid.databinding.ActivityBitmapBinding

class ActivityBitmap : AppCompatActivity() {

    private lateinit var binding: ActivityBitmapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}