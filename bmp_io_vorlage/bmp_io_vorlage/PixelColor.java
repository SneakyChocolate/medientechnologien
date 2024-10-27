
public class PixelColor {
	int r;
	int b;
	int g;
	
	public PixelColor(int r, int g, int b) {
		this.r = r;
		this.b = b;
		this.g = g;
	}

    @Override
    public String toString() {
        return "rgb(" + r + "," + g + "," + b + ")";
    }
}
