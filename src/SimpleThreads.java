import sun.print.resources.serviceui;

/**
 * Created by Bext on 05/07/2017.
 */
public class SimpleThreads {

    //Display a message, preceded by the name of the current thread
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.printf("%s: %s%n", threadName, message);
    }

    private static class MessageLoop implements Runnable {
        @Override
        public void run() {
            String importantInfo[] = {
                    "1 Pepe come papas",
                    "2 Papas viven bien",
                    "3 Bienes me caen Siempre",
                    "4 Siempre soy feliz"
            };

            try {
                for (int i = 0; i < importantInfo.length; i++) {
                    Thread.sleep(3000);
                    threadMessage(importantInfo[i]);
                }
            } catch (InterruptedException e){
              threadMessage("thread de ciclos de mensajes ha sido interrumpido.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //Delay, in milliseconds before we interrupt MessageLoop thread (default one hou)r.
        long patience = 1000 * 20 ;
        //if command line argument present, give patience in seconds.
        if (args.length > 0) {
            try {
                patience = Long.parseLong(args[0]) * 1000;
            } catch (NumberFormatException e) {
                System.err.println("El argumento debe ser entero (segundos)");
                System.exit(1);
            }
        }

        threadMessage("Starting MessageLoop Thread");
        long startTime = System.currentTimeMillis();
        Thread t = new Thread( new MessageLoop());
        t.start();

        threadMessage("Waiting for MessageLoop thread to finish");
        // loop until MessageLoop  thread exits
        while (t.isAlive()){
            threadMessage("still waiting");
            //Wait maximum of 1 second for MessageLoop thread exits
            t.join(1000);
            if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive() ) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                //Shouldn't be long now -- wait indefinitely
                t.join();
            }
        }
        threadMessage("Finally!");
    }
}
