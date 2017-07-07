/**
 * Created by Bext on 05/07/2017.
 */

object SimpleThreadsKot {

    //Display a message, preceded by the name of the current thread
    internal fun threadMessage(message: String) {
        val threadName = Thread.currentThread().name
        System.out.printf("%s: %s%n", threadName, message)
    }

    private class MessageLoop : Runnable {
        override fun run() {
            val importantInfo = arrayOf("1 Pepe come papas", "2 Papas viven bien", "3 Bienes me caen Siempre", "4 Siempre soy feliz")

            try {
                for (i in importantInfo.indices) {
                    Thread.sleep(3000)
                    threadMessage(importantInfo[i])
                }
            } catch (e: InterruptedException) {
                threadMessage("thread de ciclos de mensajes ha sido interrumpido.")
            }

        }
    }

    @Throws(InterruptedException::class)
    @JvmStatic fun main(args: Array<String>) {
        //Delay, in milliseconds before we interrupt MessageLoop thread (default one hou)r.
        var patience = (1000 * 20).toLong()
        //if command line argument present, give patience in seconds.
        if (args.size > 0) {
            try {
                patience = java.lang.Long.parseLong(args[0]) * 1000
            } catch (e: NumberFormatException) {
                System.err.println("El argumento debe ser entero (segundos)")
                System.exit(1)
            }

        }

        threadMessage("Starting MessageLoop Thread")
        val startTime = System.currentTimeMillis()
        val t = Thread(MessageLoop())
        t.start()

        threadMessage("Waiting for MessageLoop thread to finish")
        // loop until MessageLoop  thread exits
        while (t.isAlive) {
            threadMessage("still waiting")
            //Wait maximum of 1 second for MessageLoop thread exits
            t.join(1000)
            if (System.currentTimeMillis() - startTime > patience && t.isAlive) {
                threadMessage("Tired of waiting!")
                t.interrupt()
                //Shouldn't be long now -- wait indefinitely
                t.join()
            }
        }
        threadMessage("Finally!")
    }
}
