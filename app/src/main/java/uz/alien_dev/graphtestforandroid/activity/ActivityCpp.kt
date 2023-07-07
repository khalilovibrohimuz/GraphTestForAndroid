package uz.alien_dev.graphtestforandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien_dev.graphtestforandroid.databinding.ActivityCppBinding

class ActivityCpp : AppCompatActivity() {

    private lateinit var binding: ActivityCppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCppBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}