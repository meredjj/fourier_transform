import java.io.File;
import java.io.IOException;

/**
 *  Tester for the FourierTransform class. 
 *  Sample input: Knee.pgm
 *  Output: Spectrum.pgm
 *
 *  Uses PGM I/O methods from:
 *  PGMIO - Copyright (c) 2015 Arman Bilge
 *  https://gist.github.com/armanbilge/3276d80030d1caa2ed7c
 *
 */
public class FourierTransformTest{
    public static void main(String args[]){
            try{
                File input = new File("Knee.pgm");
                File output = new File("Spectrum.pgm");
                int[][] image =  PGMIO.read(input);
                image = FourierTransform.dft(image, image.length, image[0].length);
                PGMIO.write(image, output);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }

    }
}