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
        for (int i = 0; i < audioValues.length; i ++) {
            if (start != audioValues[i] > 0) {
                intersections ++;
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
                // ********* ToDo ***************
                // sine hi - 7000 hz (praat)
                System.out.print(readWavFile.sound[i] + ",");
            }

            short[] values = new short[]{-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196,3196,9102,13623,16069,16069,13623,9102,3196,-3196,-9102,-13623,-16069,-16069,-13623,-9102,-3196};
            System.out.println(sine_f(values, 16000));

            if (args.length == 1)
                System.exit(0);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (WavFileException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    private static void sample2e() {
        // 2e Downsampling
        for (int i = 0; i < samples; i++) {
            // ********* ToDo ***************
        }
    }
    private static void sample3b() {
        // 3b Bitreduzierung
        int reduced_bits = 1;
        for (int i = 0; i < samples; i++) {
            // ********* ToDo ***************
        }
    }
    private static void sample3e() {
        // 3e Bitreduzierung Differenz
        int reduced_bits = 1;
        for (int i = 0; i < samples; i++) {
            // ********* ToDo ***************
        }
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

            sample2e();
            sample3b();
            sample3e();

            WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
