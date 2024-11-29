package org.example;


public class PixelColor {
	public int r;
	public int b;
	public int g;
	
	public PixelColor(int r, int g, int b) {
		this.r = r > 255 ? 255 : (r < 0 ? 0 : r);
		this.b = b > 255 ? 255 : (b < 0 ? 0 : b);
		this.g = g > 255 ? 255 : (g < 0 ? 0 : g);
	}
	public PixelColor(double r, double g, double b) {
		this.r = (int) (r > 255 ? 255 : (r < 0 ? 0 : r));
		this.b = (int) (b > 255 ? 255 : (b < 0 ? 0 : b));
		this.g = (int) (g > 255 ? 255 : (g < 0 ? 0 : g));
	}

    @Override
    public String toString() {
        return "rgb(" + r + "," + g + "," + b + ")";
    }
}
