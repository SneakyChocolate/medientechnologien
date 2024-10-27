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

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            throw new IOException("At least one filename specified  (" + args.length + ")");
        }

        // Zugriff auf Pixel mit bmp.image.getRgbPixel(x, y);

        // Implementierung bei einem Eingabeparamter

        inFilename = args[0];
        in = new FileInputStream(inFilename);
        bmp = BmpReader.read_bmp(in);

        // BGR schreiben horizontal 2.1.
        for (int x = 0; x < bmp.image.getWidth(); x++) {
            // ********* ToDone ***************
            var pixel = bmp.image.getRgbPixel(x, 0);
            System.out.println(pixel.toString());
        }

        // BGR schreiben vertikal 2.1.
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            // ********* ToDone ***************
            var pixel = bmp.image.getRgbPixel(0, y);
            System.out.println(pixel.toString());
        }

        if (args.length == 1)
            System.exit(0);

        // Implementierung bei Ein- und Ausgabeparamter

        outFilename = args[1];
        OutputStream out = new FileOutputStream(outFilename);

        // erzeuge graustufenbild
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                // ********* TODO ***************
                var pixel = bmp.image.getRgbPixel(0, y);
                var nv = 0.3 * pixel.r + 0.6 * pixel.g + 0.1 * pixel.b;
            }
        }

        // downsampling
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {

                // ********* TODO ***************

            }
        }

        // bitreduzierung
        int reduced_bits = 1;
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {

                // ********* TODO ***************

            }
        }

        // bitreduzierung differenz
        reduced_bits = 1;
        int bitsPerColor = 8;
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {

                // ********* TODO ***************

            }
        }

        try {
            BmpWriter.write_bmp(out, bmp);
        } finally {
            out.close();
        }
    }
}
