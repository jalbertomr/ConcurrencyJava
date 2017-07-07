import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ForkJoinTask
import java.util.concurrent.RecursiveAction
import javax.imageio.ImageIO

/**
 * Created by Bext on 07/07/2017.
 */
class ForkBlurKot(private val mSource: IntArray, private val mStart: Int, private val mLength: Int, private val mDestination: IntArray) : RecursiveAction() {
    private val mBlurWidth = 15 //Proccesing window size, should be odd

    //Average pixels from source, write results into destination.
    protected fun computeDirectly() {
        val sidePixels = (mBlurWidth - 1) / 2
        for (index in mStart..mStart + mLength - 1) {
            //calculate average
            var rt = 0f
            var gt = 0f
            var bt = 0f
            for (mi in -sidePixels..sidePixels) {
                val mindex = Math.min(Math.max(mi + index, 0), mSource.size - 1)
                val pixel = mSource[mindex]
                rt += (pixel and 0x00FF0000 shr 16).toFloat() / mBlurWidth
                gt += (pixel and 0x0000FF00 shr 8).toFloat() / mBlurWidth
                bt += (pixel and 0x000000FF).toFloat() / mBlurWidth
            }
            //reasemble destination pixel.
            val dpixel = 0xFF000000.toInt() or
                    (rt.toInt() shl 16) or
                    (gt.toInt() shl 8) or
                    bt.toInt()
            mDestination[index] = dpixel
        }
    }

    override fun compute() {
        if (mLength < sThreshold) {
            computeDirectly()
            return
        }
        val split = mLength / 2
        ForkJoinTask.invokeAll(ForkBlurKot(mSource, mStart, split, mDestination),
                ForkBlurKot(mSource, mStart + split, mLength - split, mDestination))
    }

    companion object {

        private val sThreshold = 100000

        @Throws(IOException::class)
        @JvmStatic fun main(args: Array<String>) {
            /*String name = "Borrame.jpg";
        FileOutputStream fos = new FileOutputStream(name);
        fos.close();
        */
            val srcName = "red-tulip.jpg"
            val srcFile = File(srcName)
            val image = ImageIO.read(srcFile)

            println("Source Image:" + srcName)

            val blurredImage = blur(image)

            val destName = "blurred-tulips.jpg"
            val destFile = File(destName)
            ImageIO.write(blurredImage, "jpg", destFile)

            println("Dest Image:" + destName)

        }

        fun blur(srcImage: BufferedImage): BufferedImage {
            val w = srcImage.width
            val h = srcImage.height

            val src = srcImage.getRGB(0, 0, w, h, null, 0, w)
            val dst = IntArray(src.size)

            println("Array size is: " + src.size)
            println("Threshold is: " + sThreshold)

            val processors = Runtime.getRuntime().availableProcessors()
            println(Integer.toString(processors) + " procesador" + (if (processors != 1) "es estan " else " esta ") + "disponible")

            val fb = ForkBlur(src, 0, src.size, dst)
            val pool = ForkJoinPool()

            val startTime = System.currentTimeMillis()
            pool.invoke(fb)
            val endTime = System.currentTimeMillis()
            println("difumunar la imagen tomo " + (endTime - startTime) + "milisegundos")

            val dstImage = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
            dstImage.setRGB(0, 0, w, h, dst, 0, w)
            return dstImage
        }
    }
}
