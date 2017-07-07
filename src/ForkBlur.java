import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
/**
 * Created by Bext on 07/07/2017.
 */
public class ForkBlur extends RecursiveAction {
    private int[] mSource;
    private int mStart;
    private int mLength;
    private int[] mDestination;
    private int mBlurWidth = 15; //Proccesing window size, should be odd

    public ForkBlur(int[] mSource, int mStart, int mLength, int[] mDestination) {
        this.mSource = mSource;
        this.mStart = mStart;
        this.mLength = mLength;
        this.mDestination = mDestination;
    }

    //Average pixels from source, write results into destination.
    protected void computeDirectly() {
        int sidePixels = (mBlurWidth - 1)/ 2;
        for (int index = mStart; index < mStart + mLength; index++) {
            //calculate average
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0), mSource.length - 1);
                int pixel = mSource[mindex];
                rt += (float)((pixel & 0x00FF0000) >> 16) / mBlurWidth;
                gt += (float)((pixel & 0x0000FF00) >> 8) / mBlurWidth;
                bt += (float)((pixel & 0x000000FF) ) / mBlurWidth;
            }
            //reasemble destination pixel.
            int dpixel = ( 0xFF000000) |
                    ((int)rt << 16) |
                    ((int)gt << 8) |
                    ((int)bt);
            mDestination[index] = dpixel;
        }
    }

    private static int sThreshold = 100000;
    @Override
    protected void compute() {
        if (mLength < sThreshold){
            computeDirectly();
            return;
        }
        int split = mLength / 2;
        invokeAll(new ForkBlur(mSource, mStart, split, mDestination),
                  new ForkBlur(mSource, mStart + split, mLength - split, mDestination));
    }

    public static void  main(String... args) throws IOException {
        /*String name = "Borrame.jpg";
        FileOutputStream fos = new FileOutputStream(name);
        fos.close();
        */
        String srcName = "red-tulip.jpg";
        File srcFile = new File(srcName);
        BufferedImage image = ImageIO.read(srcFile);

        System.out.println("Source Image:" + srcName);

        BufferedImage blurredImage = blur(image);

        String destName = "blurred-tulips.jpg";
        File destFile = new File(destName);
        ImageIO.write(blurredImage, "jpg", destFile);

        System.out.println("Dest Image:" + destName);

    }

    public static BufferedImage blur(BufferedImage srcImage) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int[] src = srcImage.getRGB(0 ,0, w, h, null, 0, w);
        int [] dst = new int[src.length];

        System.out.println("Array size is: " + src.length);
        System.out.println("Threshold is: " + sThreshold);

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println( Integer.toString(processors) + " procesador" + (processors != 1 ? "es estan " : " esta ") + "disponible");

        ForkBlur fb = new ForkBlur(src, 0, src.length, dst);
        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();
        System.out.println("difumunar la imagen tomo " + (endTime - startTime) + "milisegundos");

        BufferedImage dstImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        dstImage.setRGB(0,0,w,h,dst,0,w);
        return dstImage;
    }
}
