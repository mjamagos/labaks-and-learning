public class floatingPoint {
    public static void main (String args[]){
        
        String input = "11001101000000011001010111000000100";

        // List of Precisions
        String HalfPrecision = input.substring(0,16);
        String SinglePrecision = input.substring(0,32);
        //String DoublePrecision = input.substring(0,64);
        //String QuadruplePrecision = input.substring(0,128);

        /*Formula for getting the s(x) "sOfx", and computing it with the constant number -1
        1. Get the first bit "s" of the binary string using input.substring(0,1)
        2. If s == 1, then -> (-1)^1 = -1;
            Else if s ==0, then -> (-1)^0 = 0;
        3. Store the value inside the variable "signField"
        */

        System.out.print("Step-by-step process of getting the value of s(x)\n");
        int signField = 0;
        String sOfx = input.substring(0,1);
        for(int i = 0; i < sOfx.length(); i++){
            int bit = sOfx.charAt(i == '1') ? -1 : 1; 
            System.out.println("s(x) = (-1)^" + sOfx + " = " + signField);
        }

        /*Formula for getting the e(x) "eOfx", and computing it with the constant number 2
        1. Get the second - sixth bit of the binary string using input.substring(1,6);
        2. If e == '1', then -> power = 1^(exponent)
            Else if e =='0', then power = 0^(exponent)
        3. Store the sum of the power in "expoField"
        4. Get the power of the second segment "s" by -> expoField - 15 (bias)
        Errors: 
        1. expoField += 1 * Math.pow(eOfx.charAt(a), a); doesn't work because char follows ASCII code
        2. Create another variable for the power wherepower = eOfx.length() - 1; so it starts at 4; and don't use decrementation in loop
        3. Just do power-- to go from 4 to 0*/

        System.out.println("\nStep-by-step process of getting the value of e(x)");
        int expoField = 0;
        String eOfx = input.substring(1,6);
        int exponent = eOfx.length() - 1;

        for(int a = 0; a< eOfx.length() ;a ++){
            if(eOfx.charAt(a) == '1'){
                expoField += 1 * Math.pow(2, exponent);
                System.out.println (eOfx.charAt(a)+ " -> "+ "2^"+ exponent + " = " + (1 * (int)Math.pow(2,exponent)));
            } else if (eOfx.charAt(a) == '0'){
                expoField += 0 * Math.pow(2, exponent);
                System.out.println (eOfx.charAt(a)+ " -> "+ "2^" +exponent + " = " + (0 * (int)Math.pow(2,exponent)));
            } exponent --;
        } System.out.println("e(x) = " + eOfx + " = " + expoField);
        

        int bias = 15;
        int e = expoField - bias;
        double expoSum = Math.pow(2,e);
        System.out.println("\n2^(" + expoField + "-" + bias + ") = " + expoSum);

        /*Formula for getting the f(x) or 1.f "fOfx", and computing it with the constant number 2
        1. List down the decimals of the third segment
        2. Use the same method from the e(x), but this time, the starting integer is -1
         */
        System.out.println("\nStep-by-step process of getting the value of f(x)");
        double fractionField = 0;
        String fOfx = input.substring(6,16);
        int startExpo = -1;
        
        for(int j = 0; j < fOfx.length(); j++){
            if(fOfx.charAt(j) == '1'){
                fractionField += 1 * (Math.pow(2, startExpo));
                System.out.println (fOfx.charAt(j)+ " -> "+ "2^"+ startExpo + " = " + (1 * (double)Math.pow(2,startExpo)));
            } else if (fOfx.charAt(j) == '0'){
                fractionField += 0 * (Math.pow(2, startExpo));
                System.out.println (fOfx.charAt(j)+ " -> "+ "2^"+ startExpo + " = " + (0 * (double)Math.pow(2,startExpo)));
            }startExpo --;
        } 
        System.out.println("f(x) = " + fOfx + " = " + fractionField);

        double fractionSum = 1 + fractionField;
        System.out.println("1.f = 1 + " + fractionField + " = " + fractionSum);

        double valueOfX = signField * expoSum * fractionSum;

        System.out.println("\n" + "value(x) = " + "(-1)^" + sOfx + " * 2^(" + expoField + "-" + bias + ") * (1." + fOfx + ")");
        System.out.println("value(x) = " + signField + " * 2^" + e + " * 1+" + fractionField);
        System.out.println("value(x) = " + signField + " * " + expoSum + " * " + fractionSum);
        System.out.println("value(x) = " + valueOfX);

        
    }
}

