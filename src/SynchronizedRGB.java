/**
 * Created by Bext on 07/07/2017.
 */
public class SynchronizedRGB {
    //values must be between 0 and 255
    private int red;
    private int green;
    private int blue;
    private String name;

    private void check(int red, int green, int blue) {
        if (red < 0 || red > 255 ||
                green < 0 || green > 255 ||
                blue < 0 || blue > 255){
            throw new IllegalArgumentException();
        }
    }

    public SynchronizedRGB(int red, int green, int blue, String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    public void set(int red, int green, int blue) {
        check(red, green, blue);
        synchronized (this) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    public synchronized int getRGB() {
        return 0; //((red << 16) | (green < 8) | blue);
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void invert() {
        red = 255 - red;
        green =  255 - green;
        blue = 255 - blue;
        name = "inverse of" + name;
    }

    public static void main(String... args) {
        SynchronizedRGB color = new SynchronizedRGB(0,0,0,"Pitch Black");
        //...
//        int myColorInt = color.getRGB();        //Statement 1
//        String myColorName = color.getName();   //Statement 2   Here between statement1 and statement2 can entera Thread and alterate de color

        synchronized (color) {                    //Correct
            int myColorInt = color.getRGB();        //Statement 1
            String myColorName = color.getName();   //Statement 2

        }
    }
}

