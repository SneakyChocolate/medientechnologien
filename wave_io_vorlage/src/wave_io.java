import java.io.IOException;

public class wave_io {
	private static String[] args;
	private static int samples = 0;
	private static int validBits = 0;
	private static long sampleRate = 0;
	private static long numFrames = 0;
	private static int numChannels = 0;

	private static String inFilename = null;
	private static String outFilename = null;
	private static WavFile readWavFile = null;

	private static double sine_f(short[] audioValues, int rate) {
		boolean start = audioValues[0] > 0;
		int intersections = 1;
		for (int i = 0; i < audioValues.length; i++) {
			if (start != audioValues[i] > 0) {
				intersections++;
				start = audioValues[i] > 0;
			}
		}
		return intersections / 2 * rate / audioValues.length;
		// return intersections;
	}

	private static void sample2a() {
		try {
			readWavFile = WavFile.read_wav(inFilename);

			// headerangaben
			numFrames = readWavFile.getNumFrames();
			numChannels = readWavFile.getNumChannels();
			samples = (int) numFrames * numChannels;
			validBits = readWavFile.getValidBits();
			sampleRate = readWavFile.getSampleRate();

			// 2a Samples schreiben
			for (int i = 0; i < samples; i++) {
				// ********* Done ***************
				// System.out.print(readWavFile.sound[i] + ",");
			}

			// short[] values = new
			// short[]{-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196};
			// System.out.println(sine_f(values, 16000));

			if (args.length == 1)
				System.exit(0);

		} catch (IOException e1) {
			// Done Auto-generated catch block
			e1.printStackTrace();
		} catch (WavFileException e1) {
			// Done Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void downsample() {
		// 2e Downsampling
		sampleRate /= 2;
		numFrames /= 2;
		for (int i = 0; i < samples / 2; i++) {
			// ********* Done ***************
			readWavFile.sound[i] = readWavFile.sound[i * 2];
		}
	}

	private static void bitreduzierung(int n) {
		// 3b Bitreduzierung
		int reduced_bits = 1;
		for (int i = 0; i < samples; i++) {
			// ********* Done ***************
			int v = readWavFile.sound[i];
			readWavFile.sound[i] = (short) (v >> n << n);
		}
	}

	private static void bitreduzierungdiff(int n) {
		// 3e Bitreduzierung Differenz
		int reduced_bits = 1;
		for (int i = 0; i < samples; i++) {
			// ********* Done ***************
			int v = readWavFile.sound[i];
			int newv = (v >> n << n);
			int diff = Math.abs(v - newv);
			// man hoert nichts aber in praat sieht man die frequenzen
			readWavFile.sound[i] = (short) (diff << n);
		}
	}

	private static void amplificate(int db) {
		double a = Math.pow(10.0, (double) db / 20.0);
		System.out.println("a: " + a);
		for (int i = 0; i < samples; i++) {
			// ********* Done ***************
			float f_sound = readWavFile.sound[i];
			f_sound *= a;
			if (f_sound > Short.MAX_VALUE) {
				f_sound = Short.MAX_VALUE;
			} else if (f_sound < Short.MIN_VALUE) {
				f_sound = Short.MIN_VALUE;
			}
			readWavFile.sound[i] = (short) f_sound;
		}
	}

	
	private static void echo(int ms, double factor) {
		int indicies = (int) (((double) ms / 1000.0) * sampleRate);
		numFrames += indicies * numChannels;
		short[] echo_sound = new short[readWavFile.sound.length + indicies];
		for (int i = 0; i < echo_sound.length; i++) {
			// ********* Done ***************
			double add = 0;
			if (i < indicies) {
				add = readWavFile.sound[i];
			}
			else if (i < samples) {
				add = readWavFile.sound[i] + readWavFile.sound[i - indicies] * factor;
			}
			else {
				add = readWavFile.sound[i - indicies] * factor;
			}
			
			if (add > Short.MAX_VALUE) {
				add = Short.MAX_VALUE;
			} else if (add < Short.MIN_VALUE) {
				add = Short.MIN_VALUE;
			}
			echo_sound[i] = (short) add;
		}
		readWavFile.sound = echo_sound;
	}

	public static void main(String[] argsp) {
		args = argsp;
		if (args.length < 1) {
			try {
				throw new WavFileException("At least one filename specified  (" + args.length + ")");
			} catch (WavFileException e1) {
				e1.printStackTrace();
			}
		}

		// Samples in dem Array readWavFile.sound

		inFilename = args[0];

		// Implementierung bei einem Eingabeparameter

		wave_io.sample2a();

		// Implementierung bei Ein-und Ausgabeparameter

		outFilename = args[1];
		try {
			if (args.length >= 3) {
				// different methods
				if (args[2].compareTo("downsample") == 0) {
					downsample();
				}
				else if (args[2].compareTo("bitred") == 0) {
					bitreduzierung(8);
				}
				else if (args[2].compareTo("bitreddif") == 0) {
					bitreduzierungdiff(10);
				}
				else if (args[2].compareTo("amplificate") == 0) {
					if (args.length >= 4) {
						int db = Integer.parseInt(args[3]);
						System.out.println("db: " + db);
						amplificate(db);
					}
				}
				else if (args[2].compareTo("echo") == 0) {
					if (args.length >= 4) {
						int ms = Integer.parseInt(args[3]);
						System.out.println("ms: " + ms);
						echo(ms, 0.6);
					}
				}
				// filename
				if (args.length == 3) {
					outFilename += "_" + args[2] + ".wav";
				} else if (args.length == 4) {
					outFilename += "_" + args[2] + "_" + args[3] + ".wav";
				}
			} else {
				downsample();
				// bitreduzierung(10);
				// bitreduzierungdiff(10);
			}

			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
