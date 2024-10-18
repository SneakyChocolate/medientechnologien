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
                // praat nachgcuekn, durch 5 und durch 3.2 teilen
                System.out.println(readWavFile.sound[i]);
            }

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
