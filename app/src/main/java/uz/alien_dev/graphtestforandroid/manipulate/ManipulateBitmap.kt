package uz.alien_dev.graphtestforandroid.manipulate

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View


class ManipulateBitmap : View {

    constructor(context: Context) : super(context) { graphLoop.start() }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { graphLoop.start() }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { graphLoop.start() }


    class GraphLoop(private val graph: ManipulateBitmap) : Thread() {

        private var time: Long? = null
        var resultForPixels = 0.0
        var resultForPixel = 0.0
        var running = true

        override fun run() {
            while (running) {
                time?.let {
                    resultForPixels = (System.currentTimeMillis() - time!!).toDouble()
                    resultForPixel = resultForPixels / (graph.surfaceWidth * graph.surfaceHeight) * 1000.0
                }
                time = System.currentTimeMillis()
                try {
                    graph.update()
                    graph.postInvalidate()
                } catch (ignored: Exception) {}
            }
        }
    }

    private val graphLoop = GraphLoop(this)
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

    fun update() {
        for (x in 0 until width) for (y in 0 until height) bitmap.setPixel(x, y, Color.rgb(x * 255 / width, y * 255 / height, 255))
    }

    private fun showInformation(canvas: Canvas) {
        canvas.drawText("Hamma pikselni o'zgartirish uchun ketadigan o'rtacha vaqt:", 20.0f, 200.0f, textPaint)
        canvas.drawText("${graphLoop.resultForPixels} millisekund", 20.0f, 240.0f, textPaint)
        canvas.drawText("Har bir pikselni o'zgartirish uchun ketadigan o'rtacha vaqt:", 20.0f, 280.0f, textPaint)
        canvas.drawText("${graphLoop.resultForPixel} mikrosekund", 20.0f, 320.0f, textPaint)
        canvas.drawText("Erishish mumkin bo'lgan ekran yangilanish tezligi:", 20.0f, 360.0f, textPaint)
        canvas.drawText("${1000 / graphLoop.resultForPixels} FPS", 20.0f, 400.0f, textPaint)
        canvas.drawText("Biz kutgan natijadan nechchi marta tez:", 20.0f, 440.0f, textPaint)
        cache = 0.0144678819444444 / graphLoop.resultForPixel; if (cache < 1.0) cache = 0.0
        canvas.drawText("$cache barobar", 20.0f, 480.0f, textPaint)
        canvas.drawText("Biz kutgan natijadan nechchi marta sekin:", 20.0f, 520.0f, textPaint)
        cache = graphLoop.resultForPixel / 0.0144678819444444; if (cache < 1.0) cache = 0.0
        canvas.drawText("$cache barobar", 20.0f, 560.0f, textPaint)
        canvas.drawText("Izoh: View'dan foydalanilgani uchun rang chuqurligi yuqori", 20.0f, 600.0f, textPaint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, null, dstRect, null)
        showInformation(canvas)
    }

    override fun onDetachedFromWindow() { super.onDetachedFromWindow(); graphLoop.running = false }
}