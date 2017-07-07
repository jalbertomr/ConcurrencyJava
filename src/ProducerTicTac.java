import java.util.Random;

/**
 * Created by Bext on 06/07/2017.
 */
public class ProducerTicTac implements Runnable {
    private Drop drop;

    class Producer implements Runnable {

        @Override
        public void run() {
            String[] importantInfo = {
                    "1 Pepe come papas",
                    "2 Papas viven bien",
                    "3 Bienes me caen Siempre",
                    "4 Siempre soy feliz"
            };
            Random random = new Random();

            for (int i = 0; i < importantInfo.length; i++) {
                drop.put(importantInfo[i]);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) { }
            }
            drop.put("HECHO");
        }
    }

    public ProducerTicTac(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        Thread t = new Thread( new Producer());
        t.start();
        while(t.isAlive()){
            System.out.printf("%s",".");
            try {
                t.join(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
