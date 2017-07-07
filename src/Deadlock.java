/**
 * Created by Bext on 06/07/2017.
 */
public class Deadlock {
    static class Friend {
        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public  synchronized void bow(Friend bower) {
            System.out.printf("%s: %s " + "has bowed to me!%n", this.name, bower.getName());
            bower.bowBack(this);
        }

        private synchronized void bowBack(Friend bower) {
            System.out.printf("%s: %s " + "has bowed back to me!%n", this.name, bower.getName());
            bower.bowBack(this);
        }
    }

    public static void main(String... args) {
        final Friend alfonso = new Friend("Alfonso");
        final Friend gaston = new Friend("Gaston");
/*
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                alfonso.bow(gaston);
            }
        };

        new Thread( myRunnable);
*/

        new Thread( new Runnable() {
            @Override
            public void run() {
                alfonso.bow(gaston);
            }
        }).start();


// Must be the two threads writen the same way to get in deadlock
//        new Thread( () -> alfonso.bow(gaston)).start();

        // The same but whit Lambda expression
        new Thread( () -> gaston.bow(alfonso)).start();
    }
}
