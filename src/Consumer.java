import java.util.Random;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by Bext on 06/07/2017.
 */
public class Consumer implements Runnable {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

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
