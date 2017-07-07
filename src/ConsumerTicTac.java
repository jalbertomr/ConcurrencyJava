import java.util.Random;

/**
 * Created by Bext on 06/07/2017.
 */
public class ConsumerTicTac implements Runnable {
    private Drop drop;

    class Consumer implements Runnable{
        @Override
        public void run() {
            Random random = new Random();
            for (String message = drop.take();
                 ! message.equals("HECHO");
                 message = drop.take() ){
                System.out.printf("Mensaje Recibido: %s%n", message);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) { }
            }
        }
    }

    public ConsumerTicTac(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        Thread t = new Thread( new Consumer());
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
