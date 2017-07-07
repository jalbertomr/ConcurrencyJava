import java.sql.DriverManager.println

/**
 * Created by Bext on 05/07/2017.
 */
class HelloRunnableKot : Runnable {

    override fun run() {
        println("Hello form a thread that implements runnable")
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            Thread(HelloRunnableKot()).start()
        }
    }
}
