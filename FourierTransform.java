import java.lang.Math;

public class FourierTransform{

    /** 
     * Function that uses a fast fourier transform algorithm to output the 
     * fourier spectrum of an input image
     * @param a and b original image dimensions
     *        image input image from pgm file
     */
    public static int[][] dft(int[][] image, int a, int b){

        int M = (2*a-1); // get padded image dimensions M and N
        int N = (2*b-1);

        double[][] padded = new double[M][N];
        double[][] dftReal = new double[M][N];
        double[][] dftImag = new double[M][N];
        int[][] spectrum = new int[M][N];

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

        spectrum = getFourierSpectrum(padded);
        return spectrum;
    }

    private static int[][] getFourierSpectrum(double[][] pad){
        //padded dimensions
        int M = pad.length;
        int N = pad[0].length;

        //image storage for first step of dft calculation
        double[][] r = new double[M][N];
        double[][] im = new double[M][N];

        //image storage for second step of dft calculation
        double[][] real = new double[M][N];
        double[][] imaginary = new double[M][N];

        //spectrum storage
        int[][] spectrum = new int[M][N];

        //Compute the DFT for the rows of the input image
        for(int x = 0; x < M; x++){
            for(int y = 0; y < N; y++){
                r[x][y] = 0;
                im[x][y] = 0;

                for(int u = 0; u < N; u++){
                    r[x][y] += pad[x][u] * Math.cos((2*Math.PI*u*y)/M);
                    im[x][y] += -pad[x][u] * Math.sin((2*Math.PI*u*y)/M);
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

        //Calculate magnitude spectrum of the Fourier Transform
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                //Log multiplied by scalar value to improve visibility of spectrum in output
                spectrum[i][j] = 5*((int) Math.log(Math.pow( (Math.pow(real[i][j], 2) + Math.pow(imaginary[i][j], 2) ), 0.5)));
            }
        }
        return spectrum;
    }
}