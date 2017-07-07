/**
 * Created by Bext on 06/07/2017.
 */
public class ProducerConsumerTicTac {
    public static void main(String... args) {
        Drop drop = new Drop();
        new Thread(new ProducerTicTac(drop)).start();
        new Thread(new ConsumerTicTac(drop)).start();
    }
}
