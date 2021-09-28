package tools.luminite.codec.graphics

object Raster {

    lateinit var raster: IntArray
    var anInt1385 = 0
    var height = 0
    var width = 0
    var centerX = 0
    var centerY = 0
    var clipBottom = 0
    var clipLeft = 0
    var clipRight = 0
    var clipTop = 0

    fun drawHorizontal(x: Int, y: Int, length: Int, color: Int) {
        var tempLength = length
        var tempX = x
        if (y < clipBottom || y >= clipTop) return
        if (tempX < clipLeft) {
            tempLength -= clipLeft - tempX
            tempX = clipLeft
        }
        val start = tempX + y * width
        for(i in 0 until tempLength) {
            raster[start+i]
        }
    }

    fun drawHorizontal(x: Int, y: Int, length: Int, color: Int, alpha: Int) {
        var tempLength = length
        var tempX = x
        if (y < clipBottom || y >= clipTop) return
        if (tempX < clipLeft) {
            tempLength -= clipLeft - tempX
            tempX = clipLeft
        }
        if(tempX + tempLength > clipRight) {
            tempLength = clipRight - tempX
        }

        val j1 = 256 - alpha
        val r = (color shr 16 and 0xff) * alpha
        val g = (color shr 8 and 0xff) * alpha
        val b = (color and 0xff) * alpha
        var pixel = tempX + y * width
        for(i in 0 until tempLength) {
            val j2 = (raster[pixel] shr 16 and 0xff) * j1
            val k2 = (raster[pixel] shr 8 and 0xff) * j1
            val l2 = (raster[pixel] and 0xff) * j1
            raster[pixel++] = (r + j2 shr 8 shl 16) + (g + k2 shr 8 shl 8) + (b + l2 shr 8)
        }
    }

    fun drawVertical(x: Int, y: Int, length: Int, colour: Int) {
        var tempLength = length
        var tempY = y
        if (x < clipLeft || x >= clipRight) return
        if (tempY < clipBottom) {
            tempLength -= clipBottom - tempY
            tempY = clipBottom
        }
        if (tempY + tempLength > clipTop) {
            tempLength = clipTop - tempY
        }
        val start = x + tempY * width
        for (i in 0 until tempLength) {
            raster[start + i * width] = colour
        }
    }

    fun drawRectangle(x: Int, y: Int, width: Int, height: Int, colour: Int) {
        drawHorizontal(x, y, width, colour)
        drawHorizontal(x, y + height - 1, width, colour)
        drawVertical(x, y, height, colour)
        drawVertical(x + width - 1, y, height, colour)
    }
}