/**
 * Created by Bext on 05/07/2017.
 */
public class SleepMessageInterrupted extends Thread {

    public static void main(String... args) throws InterruptedException {
        String[] importantInfo = {
                "1 Pepe come papas",
                "2 Papas viven bien",
                "3 Bienes me caen Siempre",
                "4 Siempre soy feliz"
        };

        SleepMessageInterrupted t1 = new SleepMessageInterrupted();
        t1.start();

        for (int i = 0; i < importantInfo.length; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e){
                //we've been interrupted, no more messages
                System.out.println("Thread ha sido interrumpido en ciclo de mensajes");
                //return;  //sleep method is designed to return immediately when an interrupt is received
            }
            System.out.println(importantInfo[i]);
            t1.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            //we've been interrupted, no more messages
            System.out.println("Thread que interrumpe ha sido interrumpido");
            return;  //in sleep, return not necesary
        }
        System.out.println("Thread que interrumpe");
    }
}
