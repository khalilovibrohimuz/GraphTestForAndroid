package uz.alien_dev.graphtestforandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.alien_dev.graphtestforandroid.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {

    private val context = this
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bSurfaceviewVsView.setOnClickListener { startActivity(Intent(context, ActivityParent::class.java)) }
        binding.bCanvas.setOnClickListener { startActivity(Intent(context, ActivityCanvas::class.java)) }
        binding.bBitmap.setOnClickListener { startActivity(Intent(context, ActivityBitmap::class.java)) }
        binding.bMatrix.setOnClickListener { startActivity(Intent(context, ActivityMatrix::class.java)) }
        binding.bPixel1.setOnClickListener { startActivity(Intent(context, ActivityPixel1::class.java)) }
        binding.bPixel2.setOnClickListener { startActivity(Intent(context, ActivityPixel2::class.java)) }
        binding.bCpp.setOnClickListener { startActivity(Intent(context, ActivityCpp::class.java)) }
    }
}