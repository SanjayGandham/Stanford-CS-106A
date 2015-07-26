/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  acm.graphics.GImage
 */
import acm.graphics.GImage;

public class HistogramEqualizationImageTransforms {
    public static GImage toGrayscale(GImage image) {
        int[][] pixels = image.getPixelArray();
        for (int row = 0; row < pixels.length; ++row) {
            for (int col = 0; col < pixels[row].length; ++col) {
                int intensity = (int)(0.3 * (double)GImage.getRed((int)pixels[row][col]) + 0.59 * (double)GImage.getGreen((int)pixels[row][col]) + 0.11 * (double)GImage.getBlue((int)pixels[row][col]) + 0.5);
                pixels[row][col] = GImage.createRGBPixel((int)intensity, (int)intensity, (int)intensity);
            }
        }
        return new GImage(pixels);
    }

    public static int[][] imageToLuminances(int[][] pixels) {
        int[][] luminances = new int[pixels.length][pixels[0].length];
        for (int row = 0; row < pixels.length; ++row) {
            for (int col = 0; col < pixels[row].length; ++col) {
                luminances[row][col] = GImage.getRed((int)pixels[row][col]);
            }
        }
        return luminances;
    }
}

