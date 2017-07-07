/**
 * Created by Bext on 06/07/2017.
 */
public class ProducerConsumer {
    public static void main(String[] args ) {
        Drop drop = new Drop();
        (new Thread( new Producer(drop))).start();
        (new Thread( new Consumer(drop))).start();
    }
}
