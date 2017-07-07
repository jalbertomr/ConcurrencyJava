/**
 * Created by Bext on 05/07/2017.
 */
class HelloThreadKot : Thread() {
    override fun run() {
        println("Hello from a Thread (Class extends Thread)")
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            HelloThreadKot().start()
        }
    }
}
