package com.example.learningapp.faceDetection

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CameraOverlayCircleUtil(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val ovalPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate the dimensions of the egg shape
        val ovalWidth = width * 0.8f // Adjust width ratio to create the oval
        val ovalHeight = height * 0.6f // Adjust height ratio to create the oval

        // Center the oval within the view
        val left = (width - ovalWidth) / 2
        val top = (height - ovalHeight) / 2
        val right = left + ovalWidth
        val bottom = top + ovalHeight

        // Draw the egg-shaped oval
        canvas.drawOval(left, top, right, bottom, ovalPaint)
    }
}
