/**
 * Created by Bext on 07/07/2017.
 */
public class ImmutableRGB {
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

    public ImmutableRGB(int red, int green, int blue, String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    public int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public String getName() {
        return name;
    }

    public ImmutableRGB invert() {
        return new ImmutableRGB(
            red = 255 - red,
            green =  255 - green,
            blue = 255 - blue,
            name = "inverse of" + name);
    }

}
