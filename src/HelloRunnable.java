/**
 * Created by Bext on 05/07/2017.
 * This way of run a thread implements Runnable that has a single method run, which contain the code to be executed
 * in the thread, this Runnable Object is passed in the constructor of the Thread
 * this is more general, Runnable object can subclass other than Thread
 */
public class HelloRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello form a thread that implements runnable");
    }

    public static void main(String[] args) {
        (new Thread(new HelloRunnableKot())).start();
    }
}
