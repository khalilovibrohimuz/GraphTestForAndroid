package uz.alien_dev.graphtestforandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.alien_dev.graphtestforandroid.databinding.ActivityParentBinding

class ActivityParent : AppCompatActivity() {

    private lateinit var binding: ActivityParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}