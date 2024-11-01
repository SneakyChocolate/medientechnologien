package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class bmp_io {
    public static String inFilename = null;
    public static String outFilename = null;
    public static PixelColor pc = null;
    public static BmpImage bmp = null;
    public static InputStream in;
    public static RgbImage rgbImage;

    public static void readrgb() {
        // BGR schreiben horizontal 2.1.
        System.out.println("horizontal rgb");
        for (int x = 0; x < bmp.image.getWidth(); x++) {
            // ********* Done ***************
            var pixel = bmp.image.getRgbPixel(x, 0);
            System.out.println(pixel.toString());
        }

        // BGR schreiben vertikal 2.1.
        System.out.println("vertical rgb");
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            // ********* Done ***************
            var pixel = bmp.image.getRgbPixel(0, y);
            System.out.println(pixel.toString());
        }
    }
    public static void graustufen() {
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* Done ***************
                var pixel = bmp.image.getRgbPixel(x, y);
                var nv = (int) (0.3 * pixel.r + 0.6 * pixel.g + 0.1 * pixel.b);
                var newpixel = new PixelColor(nv, nv, nv);
                rgbImage.setRgbPixel(x, y, newpixel);
            }
        }
    }
    public static void downsamplingVertical(int pixels) {
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* Done ***************
                var ny = y - (y % pixels);
                var pixel = bmp.image.getRgbPixel(x, ny);
                rgbImage.setRgbPixel(x, y, pixel);
            }
        }
    }
    public static void downsamplingHorizontal(int pixels) {
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* Done ***************
                var nx = x - (x % pixels);
                var pixel = bmp.image.getRgbPixel(nx, y);
                rgbImage.setRgbPixel(x, y, pixel);
            }
        }
    }
    public static void downsampling(int pixels) {
        downsamplingHorizontal(pixels);
        downsamplingVertical(pixels);
    }
    public static void bitreducing(int reduced_bits) {
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* Done ***************
                var pixel = bmp.image.getRgbPixel(x, y);
                var newpixel = new PixelColor(
                    (pixel.r >> reduced_bits) << reduced_bits,
                    (pixel.g >> reduced_bits) << reduced_bits,
                    (pixel.b >> reduced_bits) << reduced_bits
                );
                if (x == 0 && y == 0) {
                    //System.out.println(pixel.toString());
                    //System.out.println(newpixel.toString());
                }
                rgbImage.setRgbPixel(x, y, newpixel);
            }
        }
    }
    public static void bitreducingdif(int reduced_bits) {
        int bitsPerColor = 8;
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* Done ***************
                var pixel = bmp.image.getRgbPixel(x, y);
                var newpixel = new PixelColor(
                    (pixel.r >> reduced_bits) << reduced_bits,
                    (pixel.g >> reduced_bits) << reduced_bits,
                    (pixel.b >> reduced_bits) << reduced_bits
                );
                if (x == 0 && y == 0) {
                    //System.out.println(pixel.toString());
                    //System.out.println(newpixel.toString());
                }
                var difpixel = new PixelColor(
                    (pixel.r - newpixel.r) << (bitsPerColor - reduced_bits),
                    (pixel.g - newpixel.g) << (bitsPerColor - reduced_bits),
                    (pixel.b - newpixel.b) << (bitsPerColor - reduced_bits)
                );
                rgbImage.setRgbPixel(x, y, difpixel);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            throw new IOException("At least one filename specified  (" + args.length + ")");
        }

        // Zugriff auf Pixel mit bmp.image.getRgbPixel(x, y);

        // Implementierung bei einem Eingabeparamter

        inFilename = args[0];
        in = new FileInputStream(inFilename);
        bmp = BmpReader.read_bmp(in);
        rgbImage = bmp.image;

        //readrgb();

        if (args.length == 1)
            System.exit(0);

        // Implementierung bei Ein- und Ausgabeparamter

        outFilename = args[1];
        OutputStream out = new FileOutputStream(outFilename);

        if (args.length >= 3) {
            // erzeuge graustufenbild
            if (args[2].compareTo("graustufen") == 0) {
                graustufen();
            }
            // downsampling
            else if (args[2].compareTo("downsampling") == 0) {
                int v = args.length == 4 ? Integer.parseInt(args[3]) : 2;
                downsampling(v);
            }
            else if (args[2].compareTo("downsampling_horizontal") == 0) {
                int v = args.length == 4 ? Integer.parseInt(args[3]) : 2;
                downsamplingHorizontal(v);
            }
            else if (args[2].compareTo("downsampling_vertical") == 0) {
                int v = args.length == 4 ? Integer.parseInt(args[3]) : 2;
                downsamplingVertical(v);
            }
            // bitreduzierung
            else if (args[2].compareTo("bitreducing") == 0) {
                int v = args.length == 4 ? Integer.parseInt(args[3]) : 5;
                bitreducing(v);
            }
            // bitreduzierung differenz
            else if (args[2].compareTo("bitreducingdif") == 0) {
                int v = args.length == 4 ? Integer.parseInt(args[3]) : 3;
                bitreducingdif(v);
            }
        }
        else {
            // erzeuge graustufenbild
            graustufen();
            // downsampling
            //downsampling();
            // bitreduzierung
            //bitreducing();
            // bitreduzierung differenz
            //bitreducingdif();
        }
        
        try {
            BmpWriter.write_bmp(out, bmp);
        } finally {
            out.close();
        }
    }
}
