import java.lang.Math;
import java.util.ArrayList;

public class FourierTransform{

    /** 
     * Function that uses a fast fourier transform algorithm to output the 
     * fourier spectrum of an input image
     * @param a and b original image dimensions
     *        image input image from pgm file
     */

    // Scalar value for spectrum output. The output will be the Log of the magnitude spectrum multiplied by this scalar value.
    private static final int SCALAR = 5;

    public static ArrayList<double[][]> dft(int[][] image, int a, int b){

        int M = (2*a-1); // get padded image dimensions M and N
        int N = (2*b-1);

        double[][] padded = new double[M][N];
        ArrayList<double[][]> out = new ArrayList<double[][]>();

        //image storage for first step of dft calculation
        double[][] r = new double[M][N];
        double[][] im = new double[M][N];

        //image storage for second step of dft calculation
        double[][] real = new double[M][N];
        double[][] imaginary = new double[M][N];

        //Zero padding the input image
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                if((i >= a)||(j >= b)){
                    padded[i][j] = 0;
                }
                else{
                    padded[i][j] = image[i][j];
                }
            }
        }

        //f(x,y)*(-1)^(x+y) to center the transform
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                padded[i][j] = (padded[i][j]*(Math.pow(-1,(i+j))));
            }
        }
        
        //Compute the DFT for the rows of the input image
        for(int x = 0; x < M; x++){
            for(int y = 0; y < N; y++){
                r[x][y] = 0;
                im[x][y] = 0;

                for(int u = 0; u < N; u++){
                    r[x][y] += padded[x][u] * Math.cos((2*Math.PI*u*y)/M);
                    im[x][y] += -padded[x][u] * Math.sin((2*Math.PI*u*y)/M);
                }
            }
        }

        //Compute the DFT for the columns of the input image
        for(int y = 0; y < N; y++){
            for(int x = 0; x < M; x++){
                real[x][y] = 0;
                imaginary[x][y] = 0;

                for(int u = 0; u < M; u++){
                    real[x][y] += r[u][y] * Math.cos((2*Math.PI*u*x)/N);
                    imaginary[x][y] += -im[u][y] * Math.sin((2*Math.PI*u*x)/N);
                }
            }
        }

        out.add(0, real);
        out.add(1, imaginary);

        return out;
    }

    public static int[][] getFourierSpectrum(ArrayList<double[][]> dft){

        //grab real and imaginary arrays from arraylist input
        double[][] real = dft.get(0);
        double[][] imaginary = dft.get(1);

        //padded dimensions
        int M = real.length;
        int N = real[0].length;

        //spectrum storage
        int[][] spectrum = new int[M][N];

        //Calculate magnitude spectrum of the Fourier Transform
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                //Log multiplied by scalar value to improve visibility of spectrum in output
                spectrum[i][j] = SCALAR*((int) Math.log(Math.pow( (Math.pow(real[i][j], 2) + Math.pow(imaginary[i][j], 2) ), 0.5)));
            }
        }
        return spectrum;
    }

    /*
    * Ideal low pass filter for image blurring
    * This filter will only allow signals below a certain frequency to pass and will eliminate others
    * The result is a blurred image
    */
    public static ArrayList<double [][]> ilpf(ArrayList<double [][]> inputDFT, int cutoff){

        int M = inputDFT.get(0).length;
        int N = inputDFT.get(0)[0].length;
        
        double currentDistance;
        for(int i = 0; i < M; i++){
          for(int j = 0; j < N; j++){

            //Get current distance using formula that computes distance from the center of a circle
            currentDistance = Math.pow( (Math.pow((i-M/2),2) + Math.pow((j-N/2),2)) , 0.5);

            //eliminate this pixel value if it is above the frequency cutoff
            if(currentDistance > cutoff){
                inputDFT.get(0)[i][j] = 0;
                inputDFT.get(1)[i][j] = 0;
            }
          }
        }

        return inputDFT;
      }
}