package com.example.androidagain.coresystem

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.constraintlayout.helper.widget.Layer
import kotlin.properties.Delegates

class GridField(var xDimension: Int, var yDimension: Int, var row: Int, var col: Int) {

    val bitmap: Bitmap = Bitmap.createBitmap(xDimension, yDimension, Bitmap.Config.ARGB_8888)
    val canvas: Canvas = Canvas(bitmap)
    var padding: Float = 40F

    private var xFloat: Float = xDimension.toFloat()
    private var yFloat: Float = yDimension.toFloat()
    private lateinit var sharedXYPadding: Pair<Float, Float>
    private lateinit var gridDimension: Pair<Float, Float>
    private val paint = Paint()

    init {
//        defineBorders()
        defineGrids()
    }

    fun applyLayeredColorUnit(colorUnit: LayeredColorUnit) {
        val (x, y) = colorUnit.gridXY
        for (index in 0 until colorUnit.layerState.size - 1) {
            if (colorUnit.layerState[index] != null) {
                // Locate starting (x, y)
                var startX = x * (sharedXYPadding.first + gridDimension.first)
                var startY = y * (sharedXYPadding.second + gridDimension.second)

                // Apply paint style and call draw method
                useColorToPainLayer(colorUnit.layerState[index]!!)
                drawColorLayer(
                    startX, startY,
                    gridDimension.first, gridDimension.second,
                    index, LayeredColorUnit.globalMaxLayer, LayeredColorUnit.globalLayerSpacing
                )
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
        val xGridPadding = xGridTotalSpace * 5F / 100F
        val yGridPadding = yGridTotalSpace * 5F / 100F
        sharedXYPadding = Pair(xGridPadding, yGridPadding)

        // Method
        useGridPaintStyle()

        // calculate startXY, accounting for stacking padding inbetween and absence of this at sides
        val xGridUsableSpace = xFloat - (4 * xGridPadding)
        val yGridUsableSpace = yFloat - (4 * yGridPadding)

        val xGridDimension = xGridUsableSpace / col
        val yGridDimension = yGridUsableSpace / row
        gridDimension = Pair(xGridDimension, yGridDimension)

        // default drawing 9 grids, might want to change later
        // hardcoding 9 for now
        for (rowIter in 0 until row) {
            for (colIter in 0 until col) {
                // No idea why this works... but it does
                drawRectContainer(
                    colIter * (xGridDimension + xGridPadding) + xGridPadding,
                    rowIter * (yGridDimension + yGridPadding) + yGridPadding,
                    xGridDimension, xGridDimension, xGridPadding / 2F
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

    private fun useColorToPainLayer(color: Int) {
        paint.color = color
        paint.strokeWidth = 10F
    }

    private fun drawColorLayer(
        startX: Float, startY: Float,
        width: Float, height: Float,
        layer: Int, maxLayer: Int, layerSpacing: Float
    ) {

        // determining how to the layer
        var layerThickness = Pair(
            (width / maxLayer) - layerSpacing,
            (height / maxLayer) - layerSpacing
        )
        var layerWidth = (width * layer / maxLayer) - layerSpacing
        var layerHeight = (height * layer / maxLayer) - layerSpacing

        val lineStartX = layer * (startX + layerSpacing)
        val lineStartY = layer * (startY + layerSpacing)

        // layer starts at 0, ends at globalMaxLayer - 1
        canvas.drawLine(
            lineStartX, lineStartY,
            lineStartX + layerWidth, lineStartY, paint
        )
        canvas.drawLine(
            lineStartX, lineStartY,
            lineStartX, lineStartY + layerHeight, paint
        )
        canvas.drawLine(
            lineStartX + layerWidth, lineStartY,
            lineStartX + layerWidth, lineStartY + layerHeight, paint
        )
        canvas.drawLine(
            lineStartX, lineStartY + layerHeight,
            lineStartX + layerWidth, lineStartY + layerHeight, paint
        )
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