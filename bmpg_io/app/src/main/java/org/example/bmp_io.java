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

    public static void readrgb() {
        // BGR schreiben horizontal 2.1.
        for (int x = 0; x < bmp.image.getWidth(); x++) {
            // ********* Done ***************
            var pixel = bmp.image.getRgbPixel(x, 0);
            System.out.println(pixel.toString());
        }

        // BGR schreiben vertikal 2.1.
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            // ********* Done ***************
            var pixel = bmp.image.getRgbPixel(0, y);
            System.out.println(pixel.toString());
        }
    }
    public static void graustufen() {
        RgbImage rgbImage = new RgbImage(960, 540, 24);
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* Done ***************
                var pixel = bmp.image.getRgbPixel(x, y);
                var nv = (int) (0.3 * pixel.r + 0.6 * pixel.g + 0.1 * pixel.b);
                var newpixel = new PixelColor(nv, nv, nv);
                rgbImage.setRgbPixel(x, y, newpixel);
            }
        }
        bmp = new BmpImage();
        bmp.image = rgbImage;
        bmp.horizontalResolution = rgbImage.width;
        bmp.verticalResolution = rgbImage.height;
    }
    public static void downsampling() {
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* TODO ***************
            }
        }
    }
    public static void bitreducing() {
        int reduced_bits = 1;
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* TODO ***************
            }
        }
    }
    public static void bitreducingdif() {
        int reduced_bits = 1;
        int bitsPerColor = 8;
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {

                // ********* TODO ***************

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

        // readrgb();

        if (args.length == 1)
            System.exit(0);

        // Implementierung bei Ein- und Ausgabeparamter

        outFilename = args[1];
        OutputStream out = new FileOutputStream(outFilename);

        if (args.length == 3) {
            // erzeuge graustufenbild
            if (args[2].compareTo("graustufen") == 0) {
                graustufen();
            }
            // downsampling
            else if (args[2].compareTo("downsampling") == 0) {
                downsampling();
            }
            // bitreduzierung
            else if (args[2].compareTo("bitreducing") == 0) {
                bitreducing();
            }
            // bitreduzierung differenz
            else if (args[2].compareTo("bitreducingdif") == 0) {
                bitreducingdif();
            }
        }
        else {
            // erzeuge graustufenbild
            graustufen();
            // downsampling
            downsampling();
            // bitreduzierung
            bitreducing();
            // bitreduzierung differenz
            bitreducingdif();
        }
        
        try {
            BmpWriter.write_bmp(out, bmp);
        } finally {
            out.close();
        }
    }
}
