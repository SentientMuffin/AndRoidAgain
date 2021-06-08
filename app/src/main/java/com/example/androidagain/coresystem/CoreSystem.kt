package com.example.androidagain.coresystem

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import kotlin.random.Random

class CoreSystem(
    mainRootCount: Int,
    colorUnitCount: Int,
    mainX: Int,
    mainY: Int,
    rngX: Int,
    rngY: Int
) {

    val mainField = GridField(mainX, mainY, mainRootCount, mainRootCount)
    val rngField = GridField(rngX, rngY, 1, colorUnitCount)

    //    var options: List<>
    // var systemState
    val colorList = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
    val grids: Array<LayeredColorUnit?> = arrayOfNulls(mainRootCount * mainRootCount)
    val retrievable: Array<LayeredColorUnit?> = arrayOfNulls(colorUnitCount)


    init {
        // TODO populate rngField with LayeredColorUnit
        for (index in 0 until colorUnitCount) {
            retrievable[index] = LayeredColorUnit.new(colorList, index, 0)
        }
    }
}