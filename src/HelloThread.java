/**
 * Created by Bext on 05/07/2017.
 * Easier to use in simple applications, but limited the class must be descendent of thread
 */
public class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from a Thread (Class extends Thread)");
    }

    public static void main(String... args) {
        new HelloThreadKot().start();
    }
}
