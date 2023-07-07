package uz.alien_dev.graphtestforandroid.manipulate

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception


class ManipulateCanvas : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context) : super(context) { holder.addCallback(this); isFocusable = true }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { holder.addCallback(this); isFocusable = true }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { holder.addCallback(this); isFocusable = true }


    class SurfaceLoop(private val surface: ManipulateCanvas, private val surfaceHolder: SurfaceHolder) : Thread() {

        private lateinit var canvas: Canvas
        private var time: Long? = null
        var resultForPixels = 0.0
        var resultForPixel = 0.0
        var running = false

        override fun run() {
            super.run()
            while (running) {
                time?.let {
                    resultForPixels = (System.currentTimeMillis() - time!!).toDouble()
                    resultForPixel = resultForPixels / (surface.surfaceWidth * surface.surfaceHeight) * 1000.0
                }
                time = System.currentTimeMillis()
                try {
                    canvas = surfaceHolder.lockCanvas()
                    surface.draw(canvas)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                } catch (ignored: Exception) {}
            }
        }
    }

    private val surfaceLoop: SurfaceLoop = SurfaceLoop(this, holder)
    private val textPaint = Paint().apply { color = Color.WHITE; textSize = 24f }
    private val paint = Paint().apply { strokeWidth = 2f }
    var surfaceWidth = 0
    var surfaceHeight = 0
    private var cache = 0.0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        surfaceWidth = w
        surfaceHeight = h
    }

    private fun crateTask(canvas: Canvas) {
        for (x in 0 until width) for (y in 0 until height) canvas.drawPoint(x.toFloat(), y.toFloat(), paint.apply { color = Color.rgb(x * 255 / width, y * 255 / height, 255) })
    }

    private fun showInformation(canvas: Canvas) {
        canvas.drawText("Hamma pikselni o'zgartirish uchun ketadigan o'rtacha vaqt:", 20.0f, 200.0f, textPaint)
        canvas.drawText("${surfaceLoop.resultForPixels} millisekund", 20.0f, 240.0f, textPaint)
        canvas.drawText("Har bir pikselni o'zgartirish uchun ketadigan o'rtacha vaqt:", 20.0f, 280.0f, textPaint)
        canvas.drawText("${surfaceLoop.resultForPixel} mikrosekund", 20.0f, 320.0f, textPaint)
        canvas.drawText("Erishish mumkin bo'lgan ekran yangilanish tezligi:", 20.0f, 360.0f, textPaint)
        canvas.drawText("${1000 / surfaceLoop.resultForPixels} FPS", 20.0f, 400.0f, textPaint)
        canvas.drawText("Biz kutgan natijadan nechchi marta tez:", 20.0f, 440.0f, textPaint)
        cache = 0.0144678819444444 / surfaceLoop.resultForPixel; if (cache < 1.0) cache = 0.0
        canvas.drawText("$cache barobar", 20.0f, 480.0f, textPaint)
        canvas.drawText("Biz kutgan natijadan nechchi marta sekin:", 20.0f, 520.0f, textPaint)
        cache = surfaceLoop.resultForPixel / 0.0144678819444444; if (cache < 1.0) cache = 0.0
        canvas.drawText("$cache barobar", 20.0f, 560.0f, textPaint)
        canvas.drawText("Izoh: SurfaceView'dan foydalanilgani uchun rang chuqurligi past", 20.0f, 600.0f, textPaint)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        crateTask(canvas)
        showInformation(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) { if (!surfaceLoop.running) { surfaceLoop.start(); surfaceLoop.running = true } }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) { surfaceLoop.running = false }
}