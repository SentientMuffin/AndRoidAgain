package com.example.androidagain.coresystem

import android.graphics.Color
import java.util.*
import kotlin.random.Random

class LayeredColorUnit private constructor(
    val colorList: List<Int>,
    val maxLayer: Int,
    val x: Int,
    val y: Int
) {

    // low index is on the outside, high index inside
    var layerState: Array<Int?> = arrayOfNulls(maxLayer)
    var interacts = false
    var gridXY = Pair(x, y)

    // Randomize the number of layers, from 1 to max, cannot generate 0, that would be empty
    // unit fill direction: out -> in, index wise, always fill low to high
    init {
        val layerCount = Random.nextInt(1, maxLayer)
        for (index in 0 until layerCount - 1) {
            layerState[index] = colorList.random()
        }
    }

    fun willInteract() {
        interacts = true
    }

    companion object StaticFactory {
        var globalMaxLayer = 4
        fun new(colorList: List<Int>, x: Int, y: Int): LayeredColorUnit =
            LayeredColorUnit(colorList, globalMaxLayer, x, y)
    }

}