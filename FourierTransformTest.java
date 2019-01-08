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
                System.out.println("Processing input image...");

                //Create input and output files
                File input = new File("Knee.pgm");
                File output = new File("Spectrum.pgm");

                //Create input image as 2D array from PGM read
                int[][] image =  PGMIO.read(input);
                
                //Run DFT algorithm on input image
                image = FourierTransform.dft(image, image.length, image[0].length);

                //Write output image
                PGMIO.write(image, output);

                System.out.println("Done.");

            }catch(IOException e){
                System.out.println(e.getMessage());
            }

    }
}