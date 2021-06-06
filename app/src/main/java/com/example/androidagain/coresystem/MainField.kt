package com.example.androidagain.coresystem

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class MainField(var mainDimension: Int) {

    val bitmap: Bitmap = Bitmap.createBitmap(mainDimension, mainDimension, Bitmap.Config.ARGB_8888)
    val canvas: Canvas = Canvas(bitmap)
    var padding: Float = 40F

    private var mainFloat: Float = mainDimension.toFloat()
    private val paint = Paint()

    fun setup() {
        defineBorders()
        defineGrids()
    }

    private fun defineBorders() {
        useBorderPaintStyle()
        drawRectContainer(0F, 0F, mainFloat, mainFloat, padding)
    }

    private fun defineGrids() {
        // Local Var
        val gridTotalSpace = mainFloat / 3
        var gridPadding = gridTotalSpace * 5F / 100F

        // Method
        useGridPaintStyle()

        // calculate startXY, accounting for stacking padding inbetween and absence of this at sides
        val gridUsableSpace = mainFloat - (4 * gridPadding)
        val gridDimension = gridUsableSpace / 3F
        gridPadding /= 2F

        // default drawing 9 grids, might want to change later
        // hardcoding 9 for now
        for (row in 0..2) {
            for (col in 0..2) {
                // No idea why this works... but it does
                drawRectContainer(
                    col * (gridDimension + 2 * gridPadding) + 2 * gridPadding,
                    row * (gridDimension + 2 * gridPadding) + 2 * gridPadding,
                    gridDimension, gridDimension, gridPadding
                )
            }
        }
    }

    private fun useBorderPaintStyle() {
        paint.color = Color.TRANSPARENT
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
        paint.isAntiAlias = true
    }

    private fun useGridPaintStyle() {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
        paint.isAntiAlias = true
    }

    /*
     * (startX, startY) will indicate the top left corner of the rectangular container to draw
     * padding will be applied to all sides
     *
     * paint style is not modified here, use the paintStyle modifiers prior to calling this method
     */
    private fun drawRectContainer(
        startX: Float,
        startY: Float,
        width: Float,
        height: Float,
        padding: Float
    ) {
        // Top
        canvas.drawLine(
            startX + padding, startY + padding,
            startX + width - padding, startY + padding,
            paint
        )

        // Left
        canvas.drawLine(
            startX + padding, startY + padding,
            startX + padding, startY + height - padding,
            paint
        )

        // Right
        canvas.drawLine(
            startX + width - padding, startY + padding,
            startX + width - padding, startY + height - padding,
            paint
        )

        // Bottom
        canvas.drawLine(
            startX + padding, startY + height - padding,
            startX + width - padding, startY + height - padding,
            paint
        )
    }

}