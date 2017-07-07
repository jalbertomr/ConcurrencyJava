import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Bext on 06/07/2017.
 */
public class SafeLock {
    static class Friend {
        private final String name;
        private final Lock lock = new ReentrantLock();

        Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean impendingBow(Friend bower) {
            Boolean myLock = false;
            Boolean yourLock = false;
            try {
                myLock = lock.tryLock();
                yourLock = bower.lock.tryLock();
            } finally {
                if ( !(myLock && yourLock)){
                    if (myLock){
                        lock.unlock();
                    }
                    if (yourLock){
                        bower.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        public void bow(Friend bower) {
            if (impendingBow(bower)){
                try{
                    System.out.printf("%s: %s has bower to me!%n", this.name, bower.getName());
                    bower.bowBack(this);
                }finally {
                    lock.unlock();
                    bower.lock.unlock();
                }
            } else {
                System.out.printf("%s: %s started to bow to me, but saw that I was already bowing to him.%n", this.name, bower.getName());
            }
        }

        public void bowBack(Friend bower) {
            System.out.printf("%s: %s has bowed back to me!%n", this.name, bower.getName());
        }

        static class BowLoop implements Runnable {
            private Friend bower;
            private Friend bowee;

            public BowLoop(Friend bower, Friend bowee) {
                this.bower = bower;
                this.bowee = bowee;
            }

            @Override
            public void run() {
                Random random = new Random();
                for (;;){
                    try {
                        Thread.sleep(random.nextInt(10));
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                    bowee.bow(bower);
                }
            }
        }

        public static void main(String[] args) {
            final Friend alfonso = new Friend("Alfonso");
            final Friend gaston = new Friend("Gaston");

            new Thread( new BowLoop(alfonso, gaston)).start();
            new Thread( new BowLoop(gaston, alfonso)).start();
        }
    }
}
