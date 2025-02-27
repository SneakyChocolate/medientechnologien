package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class bmp_io {
	public static String inFilename = null;
	public static String outFilename = null;
	public static PixelColor pc = null;
	public static BmpImage bmp = null;
	public static InputStream in;
	public static RgbImage rgbImage;
	public static RgbImage new_rgbImage;

	public static void write_pixels(String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			for (int y = 0; y < bmp.image.getHeight(); y++) {
				var row = "";
				for (int x = 0; x < bmp.image.getWidth(); x++) {
					var pixel = bmp.image.getRgbPixel(x, y);
					row += pixel.toString();
				}
				writer.println(row);
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("file couldnt be created");
		}
	}
	public static void ycbcr_values(String filename) {
		HashMap<Integer, Integer> values = new HashMap<>();
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				var pixel = bmp.image.getRgbPixel(x, y);
				var v = values.get(pixel.g);
				if (v == null) {
					values.put(pixel.g, 1);
				}
				else {
					values.put(pixel.g, v + 1);
				}
			}
		}
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			for (var entry : values.entrySet()) {
				writer.println(entry.getKey() + ": " + entry.getValue());
			}
			writer.close();
			System.out.println("created file at: " + filename);
		} catch (Exception e) {
			System.out.println("file couldnt be created");
		}
	}
	public static void blur(int range) {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				double r = 0;
				double g = 0;
				double b = 0;
				int neighbors = 0;
				for (int nx = -range; nx <= range; nx ++) {
					for (int ny = -range; ny <= range; ny ++) {
						if (x + nx < 0 || x + nx >= bmp.image.getWidth()) continue;
						if (y + ny < 0 || y + ny >= bmp.image.getHeight()) continue;
						neighbors ++;
						var pixel = bmp.image.getRgbPixel(x + nx, y + ny);
						r += pixel.r;
						g += pixel.g;
						b += pixel.b;
					}
				}
				PixelColor newpixel = new PixelColor(r / neighbors, g / neighbors, b / neighbors);
				
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void blur_diff(int range) {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				double r = 0;
				double g = 0;
				double b = 0;
				int neighbors = 0;
				for (int nx = -range; nx <= range; nx ++) {
					for (int ny = -range; ny <= range; ny ++) {
						if (x + nx < 0 || x + nx >= bmp.image.getWidth()) continue;
						if (y + ny < 0 || y + ny >= bmp.image.getHeight()) continue;
						neighbors ++;
						var pixel = bmp.image.getRgbPixel(x + nx, y + ny);
						r += pixel.r;
						g += pixel.g;
						b += pixel.b;
					}
				}
				
				PixelColor blurPixel = new PixelColor(r / neighbors, g / neighbors, b / neighbors);
				PixelColor pixel = bmp.image.getRgbPixel(x, y);
				PixelColor newpixel = new PixelColor(
					128 + (pixel.r - blurPixel.r),
					128 + (pixel.g - blurPixel.g),
					128 + (pixel.b - blurPixel.b)
				);
				
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void gradient() {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				double r = 0;
				double g = 0;
				double b = 0;
				int sum = 0;
				int range = 1;
				double[][] weightMap = {
					{ 0,-2, 0},
					{-2,12,-2},
					{ 0,-2, 0},
				};
				for (int nx = -range; nx <= range; nx ++) {
					for (int ny = -range; ny <= range; ny ++) {
						if (x + nx < 0 || x + nx >= bmp.image.getWidth()) continue;
						if (y + ny < 0 || y + ny >= bmp.image.getHeight()) continue;
						var weight = weightMap[ny + range][nx + range];
						sum += weight;
						var pixel = bmp.image.getRgbPixel(x + nx, y + ny);
						r += pixel.r * weight;
						g += pixel.g * weight;
						b += pixel.b * weight;
					}
				}
				PixelColor newpixel = new PixelColor(r / sum, g / sum, b / sum);
				
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void gradient_diff() {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				double r = 0;
				double g = 0;
				double b = 0;
				int sum = 0;
				int range = 1;
				double[][] weightMap = {
					{ 0,-2, 0},
					{-2,12,-2},
					{ 0,-2, 0},
				};
				for (int nx = -range; nx <= range; nx ++) {
					for (int ny = -range; ny <= range; ny ++) {
						if (x + nx < 0 || x + nx >= bmp.image.getWidth()) continue;
						if (y + ny < 0 || y + ny >= bmp.image.getHeight()) continue;
						var weight = weightMap[ny + range][nx + range];
						sum += weight;
						var pixel = bmp.image.getRgbPixel(x + nx, y + ny);
						r += pixel.r * weight;
						g += pixel.g * weight;
						b += pixel.b * weight;
					}
				}
				PixelColor gradientPixel = new PixelColor(r / sum, g / sum, b / sum);
				PixelColor pixel = bmp.image.getRgbPixel(x, y);
				PixelColor newpixel = new PixelColor(
					128 + (pixel.r - gradientPixel.r),
					128 + (pixel.g - gradientPixel.g),
					128 + (pixel.b - gradientPixel.b)
				);
				
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void median(int range) {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				List<PixelColor> colors = new ArrayList<>();
				for (int nx = -range; nx <= range; nx ++) {
					for (int ny = -range; ny <= range; ny ++) {
						if (x + nx < 0 || x + nx >= bmp.image.getWidth()) continue;
						if (y + ny < 0 || y + ny >= bmp.image.getHeight()) continue;
						var neighborPixel = bmp.image.getRgbPixel(x + nx, y + ny);
						colors.add(neighborPixel);
					}
				}

				colors.sort((c1, c2) -> {
					int brightness1 = c1.r;
					int brightness2 = c2.r;
					return brightness1 - brightness2;
				});
				PixelColor newpixel = colors.get(colors.size() / 2);
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void sobelfilter(boolean vertical) {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				double r = 0;
				double g = 0;
				double b = 0;
				int range = 1;
				double[][] weightMap;
				if (vertical) {
					weightMap = new double[][]{
						{1,0,-1},
						{2,0,-2},
						{1,0,-1},
					};
				}
				else {
					weightMap = new double[][]{
						{1 ,2 ,1},
						{0 ,0 ,0},
						{-1,-2,-1},
					};
				}
				for (int nx = -range; nx <= range; nx ++) {
					for (int ny = -range; ny <= range; ny ++) {
						if (x + nx < 0 || x + nx >= bmp.image.getWidth()) continue;
						if (y + ny < 0 || y + ny >= bmp.image.getHeight()) continue;
						var weight = weightMap[ny + range][nx + range];
						var pixel = bmp.image.getRgbPixel(x + nx, y + ny);
						r += pixel.r * weight;
						g += pixel.g * weight;
						b += pixel.b * weight;
					}
				}
				PixelColor newpixel = new PixelColor(r, g, b);
				
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	// aufgabe 4.3.a
	public static void brightness_print() {
		int sum = 0;
		image_to_ycbcr_y();
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				var pixel = bmp.image.getRgbPixel(x, y);
				sum += pixel.r;
			}
		}
		int amount = bmp.image.getHeight() * bmp.image.getWidth();
		System.out.println("average brightness: " + sum / amount);
	}
	public static void contrast_print() {
		int amount = bmp.image.getHeight() * bmp.image.getWidth();
		// brightness
		int brightness_sum = 0;
		image_to_ycbcr_y();
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				var pixel = bmp.image.getRgbPixel(x, y);
				brightness_sum += pixel.r;
			}
		}
		int u = brightness_sum / amount;
		
		// abweichung
		int osum = 0;
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				var pixel = bmp.image.getRgbPixel(x, y);
				osum += Math.pow(pixel.r - u, 2);
			}
		}
		double o = Math.sqrt(osum / amount);
		System.out.println("standartabweichung: " + o);
	}
	// aufgabe 4.3.b
	public static void add_brightness(int b) {
		image_to_ycbcr_y();
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var pixel = bmp.image.getRgbPixel(x, y);
				var newpixel = new PixelColor(
					pixel.r + b,
					pixel.g + b,
					pixel.b + b
				);
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	// aufgabe 4.3.c
	public static void multiply_contrast(double k) {
		image_to_ycbcr_y();
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var pixel = bmp.image.getRgbPixel(x, y);
				var newpixel = new PixelColor(
					(pixel.r - 128) * k + 128,
					(pixel.g - 128) * k + 128,
					(pixel.b - 128) * k + 128
				);
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
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
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void downsamplingVertical(int pixels) {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var ny = y - (y % pixels);
				var pixel = bmp.image.getRgbPixel(x, ny);
				new_rgbImage.setRgbPixel(x, y, pixel);
			}
		}
	}
	public static void downsamplingHorizontal(int pixels) {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var nx = x - (x % pixels);
				var pixel = bmp.image.getRgbPixel(nx, y);
				new_rgbImage.setRgbPixel(x, y, pixel);
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
				new_rgbImage.setRgbPixel(x, y, newpixel);
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
				var difpixel = new PixelColor(
					(pixel.r - newpixel.r) << (bitsPerColor - reduced_bits),
					(pixel.g - newpixel.g) << (bitsPerColor - reduced_bits),
					(pixel.b - newpixel.b) << (bitsPerColor - reduced_bits)
				);
				new_rgbImage.setRgbPixel(x, y, difpixel);
			}
		}
	}

	// Aufgabe 4
	public static PixelColor copy(PixelColor pixel) {
		return new PixelColor(pixel.r, pixel.g, pixel.b);
	}
	public static PixelColor multiply_pixel(PixelColor pixelColor, double r, double g, double b) {
		pixelColor.r *= r;
		pixelColor.g *= g;
		pixelColor.b *= b;
		return pixelColor;
	}
	public static void multiply_rgb(double r, double g, double b) {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var pixel = bmp.image.getRgbPixel(x, y);
				var newpixel = multiply_pixel(copy(pixel), r, g, b);
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static PixelColor RGB_to_YCbCr(PixelColor rgb) {
		var mat = new double[][] {
			new double[] {0.299, 0.587, 0.114},
			new double[] {-0.169, -0.331, 0.5},
			new double[] {0.5, -0.419, -0.081},
		};
		var mul = new double[] {rgb.r, rgb.g, rgb.b};
		int y  = (int) (mat[0][0] * mul[0] + mat[0][1] * mul[1] + mat[0][2] * mul[2]);
		int cb = (int) (mat[1][0] * mul[0] + mat[1][1] * mul[1] + mat[1][2] * mul[2]) + 128;
		int cr = (int) (mat[2][0] * mul[0] + mat[2][1] * mul[1] + mat[2][2] * mul[2]) + 128;
		return new PixelColor(y, cb, cr);
	}
	public static PixelColor YCbCr_to_RGB(PixelColor ycbcr) {
		var mat = new double[][] {
			new double[] {1, 0, 1.403},
			new double[] {1, -0.344, -0.714},
			new double[] {1, 1.773, 0},
		};
		var mul = new double[] {ycbcr.r, ycbcr.g - 128, ycbcr.b - 128};
		int r = (int) (mat[0][0] * mul[0] + mat[0][1] * mul[1] + mat[0][2] * mul[2]);
		int g = (int) (mat[1][0] * mul[0] + mat[1][1] * mul[1] + mat[1][2] * mul[2]);
		int b = (int) (mat[2][0] * mul[0] + mat[2][1] * mul[1] + mat[2][2] * mul[2]);
		return new PixelColor(r, g, b);
	}
	public static void image_to_ycbcr() {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var pixel = bmp.image.getRgbPixel(x, y);
				var ycbcr = RGB_to_YCbCr(pixel);
				var newpixel = YCbCr_to_RGB(ycbcr);
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void image_to_ycbcr_y() {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var pixel = bmp.image.getRgbPixel(x, y);
				var ycbcr = RGB_to_YCbCr(pixel);
				ycbcr.g = 128;
				ycbcr.b = 128;
				var newpixel = YCbCr_to_RGB(ycbcr);
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void image_to_ycbcr_cr() {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var pixel = bmp.image.getRgbPixel(x, y);
				var ycbcr = RGB_to_YCbCr(pixel);
				ycbcr.r = 128;
				ycbcr.b = 128;
				var newpixel = YCbCr_to_RGB(ycbcr);
				new_rgbImage.setRgbPixel(x, y, newpixel);
			}
		}
	}
	public static void image_to_ycbcr_cb() {
		for (int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				// ********* Done ***************
				var pixel = bmp.image.getRgbPixel(x, y);
				var ycbcr = RGB_to_YCbCr(pixel);
				ycbcr.r = 128;
				ycbcr.g = 128;
				var newpixel = YCbCr_to_RGB(ycbcr);
				new_rgbImage.setRgbPixel(x, y, newpixel);
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
		new_rgbImage = rgbImage.copy();

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
			// rgb only
			else if (args[2].compareTo("red_only") == 0) {
				multiply_rgb(1, 0, 0);
			}
			else if (args[2].compareTo("green_only") == 0) {
				multiply_rgb(0, 1, 0);
			}
			else if (args[2].compareTo("blue_only") == 0) {
				multiply_rgb(0, 0, 1);
			}
			// ycbcr
			else if (args[2].compareTo("ycbcr") == 0) {
				image_to_ycbcr();
			}
			else if (args[2].compareTo("ycbcr_y") == 0) {
				image_to_ycbcr_y();
				ycbcr_values(outFilename + "_values.txt");
				//write_pixels("test.txt");
			}
			else if (args[2].compareTo("ycbcr_cb") == 0) {
				image_to_ycbcr_cb();
			}
			else if (args[2].compareTo("ycbcr_cr") == 0) {
				image_to_ycbcr_cr();
			}
			else if (args[2].compareTo("brightness_print") == 0) {
				brightness_print();
			}
			else if (args[2].compareTo("contrast_print") == 0) {
				contrast_print();
			}
			else if (args[2].compareTo("add_brightness") == 0) {
				int v = args.length == 4 ? Integer.parseInt(args[3]) : 5;
				add_brightness(v);
				ycbcr_values(outFilename + "_brightness_values_"+v+".txt");
			}
			else if (args[2].compareTo("multiply_contrast") == 0) {
				double k = args.length == 4 ? Double.parseDouble(args[3]) : 1;
				multiply_contrast(k);
				ycbcr_values(outFilename + "_contrast_values_"+k+".txt");
			}
			else if (args[2].compareTo("blur") == 0) {
				int range = args.length == 4 ? Integer.parseInt(args[3]) : 3;
				graustufen();
				bmp.image = new_rgbImage;
				blur(range);
			}
			else if (args[2].compareTo("blur_diff") == 0) {
				int range = args.length == 4 ? Integer.parseInt(args[3]) : 3;
				graustufen();
				bmp.image = new_rgbImage;
				blur_diff(range);
			}
			else if (args[2].compareTo("gradient_filter") == 0) {
				graustufen();
				bmp.image = new_rgbImage;
				gradient();
			}
			else if (args[2].compareTo("gradient_filter_diff") == 0) {
				graustufen();
				bmp.image = new_rgbImage;
				gradient_diff();
			}
			else if (args[2].compareTo("median") == 0) {
				int range = args.length == 4 ? Integer.parseInt(args[3]) : 3;
				graustufen();
				bmp.image = new_rgbImage;
				median(range);
			}
			else if (args[2].compareTo("sobelfilter") == 0) {
				String hv = args.length == 4 ? args[3] : "horizontal";
				// graustufen();
				// bmp.image = new_rgbImage;
				sobelfilter(hv.equals("vertikal"));
			}
		}
		
		try {
			System.out.println("saving image under: " + outFilename);
			bmp.image = new_rgbImage;
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}
