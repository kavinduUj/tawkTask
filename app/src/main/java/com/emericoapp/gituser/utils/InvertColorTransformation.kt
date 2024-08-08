package com.emericoapp.gituser.utils


import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import coil.size.Size
import coil.transform.Transformation

/**
 *  this is color inverted class for each 4th items
 */
class InvertColorTransformation : Transformation {

    override val cacheKey: String = "invert_color"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val invertedBitmap = Bitmap.createBitmap(input.width, input.height, input.config)
        val canvas = android.graphics.Canvas(invertedBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.set(floatArrayOf(
            -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
            0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
            0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        ))
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(input, 0f, 0f, paint)
        return invertedBitmap
    }
}


