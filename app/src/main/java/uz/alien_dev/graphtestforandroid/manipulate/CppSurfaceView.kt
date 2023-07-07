package uz.alien_dev.graphtestforandroid.manipulate

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception


class CppSurfaceView : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context) : super(context) { holder.addCallback(this); isFocusable = true }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { holder.addCallback(this); isFocusable = true }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { holder.addCallback(this); isFocusable = true }


    class SurfaceLoop(private val surface: CppSurfaceView, private val surfaceHolder: SurfaceHolder) : Thread() {

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
    private lateinit var bitmap: Bitmap
    private lateinit var dstRect: Rect
    private var surfaceWidth = 0
    private var surfaceHeight = 0
    private var cache = 0.0
    private var scale = 1


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        surfaceWidth = w / scale
        surfaceHeight = h / scale
        bitmap = Bitmap.createBitmap(surfaceWidth, surfaceHeight, Bitmap.Config.ARGB_8888)
        dstRect = Rect(0, 0, surfaceWidth * scale, surfaceHeight * scale)
        bitmap.eraseColor(-16777216)
    }

    private external fun lockBitmap(bitmap: Bitmap)
    private external fun unlockBitmap()
    private external fun pixel(x: Int, y: Int, r: Int, g: Int, b: Int)
    private external fun gradient()

    private fun createTask() {
        gradient()
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, null, dstRect, null)
        showInformation(canvas)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        lockBitmap(bitmap)
        createTask()
        unlockBitmap()
        canvas.drawBitmap(bitmap, null, dstRect, null)
        showInformation(canvas)
    }

    companion object { init { System.loadLibrary("graphtestforandroid") } }
    override fun surfaceCreated(holder: SurfaceHolder) { if (!surfaceLoop.running) { surfaceLoop.start(); surfaceLoop.running = true } }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) { surfaceLoop.running = false }
}