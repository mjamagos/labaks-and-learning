import java.util.Scanner;

public class bit{
    public static void main(String[] args) {
        Scanner jo = new Scanner (System.in);

        System.out.print("Type number of bits: ");
        int N = jo.nextInt();

        int MSB = N-1;

        int smax = 0; //2^(n-1) - 1
        int smin = 0; //-2^(n-1)
        long unmax = 0; //2^(n^1)
        long unmin = 0; // 0 lattan

        for(int a = 0; a < N; a++){
            //unsigned max binary = 11111111
            //unsigned min binary = 00000000
            //TCN signed max = 011111111
            //TCN signed min = 10000000

            if (a == MSB){
                smax += 0 * (int)(Math.pow(2,a));
                smin += 1 * (int)-(Math.pow(2,a));
            } else{
                smax += 1 * (int)(Math.pow(2,a));
                smin += 0 * (int)(Math.pow(2,a));
            }

            unmax += (1) * (long)(Math.pow(2, a)); 
            unmin += (0) * (long)(Math.pow(2,a)); 

        } System.out.println("\nSigned Maximum: "+ smax +
                             "\nSigned Minimum: " + smin +
                             "\nUnsigned Maximum: " + unmax + 
                             "\nUnsigned Minimum: " + unmin);
    }
}
