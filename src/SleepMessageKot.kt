/**
 * Created by Bext on 05/07/2017.
 */
object SleepMessageKot {
    @Throws(InterruptedException::class)
    @JvmStatic fun main(args: Array<String>) {
        val importantInfo = arrayOf("Pepe come papas", "Papas viven bien", "Bienes me caen Siempre", "Siempre soy feliz")

        for (i in importantInfo.indices) {
            Thread.sleep(3000)
            println(importantInfo[i])
        }
    }
}
