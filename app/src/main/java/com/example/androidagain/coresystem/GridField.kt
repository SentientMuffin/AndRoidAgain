package com.example.androidagain.coresystem

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GridField(var xDimension: Int, var yDimension: Int, var row: Int, var col: Int) {

    val bitmap: Bitmap = Bitmap.createBitmap(xDimension, yDimension, Bitmap.Config.ARGB_8888)
    val canvas: Canvas = Canvas(bitmap)
    var padding: Float = 40F

    private var xFloat: Float = xDimension.toFloat()
    private var yFloat: Float = yDimension.toFloat()
    private val paint = Paint()

    init {
        defineBorders()
        defineGrids()
    }

    fun applyLayeredColorUnit(colorUnit: LayeredColorUnit) {
        val (x, y) = colorUnit.gridXY
        for (index in 0 until colorUnit.layerState.size - 1) {
            if (colorUnit.layerState[index] != null) {
                drawColorLayer(colorUnit.layerState[index]!!, x, y, index)
            }
        }
    }

    // TODO: Grids should be contained within the border..
    private fun defineBorders() {
        useBorderPaintStyle()
        drawRectContainer(0F, 0F, xFloat, yFloat, padding)
    }

    private fun defineGrids() {
        // Local Var
        val xGridTotalSpace = xFloat / col
        val yGridTotalSpace = yFloat / row

        var xGridPadding = xGridTotalSpace * 5F / 100F
        var yGridPadding = yGridTotalSpace * 5F / 100F

        // Method
        useGridPaintStyle()

        // calculate startXY, accounting for stacking padding inbetween and absence of this at sides
        val xGridUsableSpace = xFloat - (4 * xGridPadding)
        val yGridUsableSpace = yFloat - (4 * yGridPadding)

        val xGridDimension = xGridUsableSpace / col
        val yGridDimension = yGridUsableSpace / row
        xGridPadding /= 2F
        yGridPadding /= 2F

        // default drawing 9 grids, might want to change later
        // hardcoding 9 for now
        for (rowIter in 0 until row) {
            for (colIter in 0 until col) {
                // No idea why this works... but it does
                drawRectContainer(
                    colIter * (xGridDimension + 2 * xGridPadding) + 2 * xGridPadding,
                    rowIter * (yGridDimension + 2 * yGridPadding) + 2 * yGridPadding,
                    xGridDimension, xGridDimension, xGridPadding
                )
            }
        }
    }

    private fun useBorderPaintStyle() {
        paint.color = Color.RED
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

    private fun useColorToPainLayer(color: Int) {
        paint.color = color
        paint.strokeWidth = 25F
    }

    private fun drawColorLayer(color: Int, gridX: Int, gridY: Int, layer: Int) {
        useColorToPainLayer(color)

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