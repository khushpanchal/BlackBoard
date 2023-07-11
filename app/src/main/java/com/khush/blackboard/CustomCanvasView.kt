package com.khush.blackboard

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs

class CustomCanvasView(context: Context): View(context) {

    private lateinit var tempCanvas: Canvas
    private lateinit var tempBitmap: Bitmap
    private var previousX = 0f
    private var previousY = 0f
    private var currentX = 0f
    private var currentY = 0f
    private val tolerance = ViewConfiguration.get(context).scaledTouchSlop
    private val backGroundColor = ResourcesCompat.getColor(resources, R.color.black, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.white, null)
    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true //smooth edge
        isDither = true
        strokeWidth = 12f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND

    }
    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(::tempBitmap.isInitialized) tempBitmap.recycle()
        tempBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        tempCanvas = Canvas(tempBitmap)
        tempCanvas.drawColor(backGroundColor)
        tempCanvas.drawRect(Rect(10,10,w,h), paint)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(tempBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        currentX = event.x
        currentY = event.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                path.moveTo(currentX, currentY)
                previousX = currentX
                previousY = currentY
            }
            MotionEvent.ACTION_UP -> {
                path.reset()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(currentX - previousX)
                val dy = abs(currentY - previousY)
                if(dx >= tolerance || dy >= tolerance) {
                    path.quadTo(previousX, previousY, currentX, currentY)
                    previousX = currentX
                    previousY = currentY
                    tempCanvas.drawPath(path, paint)
                }
                invalidate()
            }
        }
        return true
    }
}
