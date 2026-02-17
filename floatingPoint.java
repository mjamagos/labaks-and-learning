import java.util.Scanner;

public class c {
    public static void main(String args[]) {
        Scanner jo = new Scanner(System.in);

        System.out.println("Select Mode: [1] IEEE to Decimal  [2] Decimal to IEEE");
        int choice = jo.nextInt();

        if (choice == 1) {
            System.out.print("Enter binary string: ");
            String input = jo.next();

            while (input.length() < 128) {
                input += "0";
            }

            for (int p = 1; p <= 4; p++) {
                int totalBits, exponentWidth, bias;
                String precisionName;

                if (p == 1) { 
                    totalBits = 16; exponentWidth = 5; bias = 15; precisionName = "Half"; 
                } else if (p == 2) { 
                    totalBits = 32; exponentWidth = 8; bias = 127; precisionName = "Single"; 
                } else if (p == 3) { 
                    totalBits = 64; exponentWidth = 11; bias = 1023; precisionName = "Double"; 
                } else { 
                    totalBits = 128; exponentWidth = 15; bias = 16383; precisionName = "Quadruple"; 
                }

                String currentPrecision = input.substring(0, totalBits);

                String sOfx = currentPrecision.substring(0, 1);
                int bitS = sOfx.charAt(0) == '1' ? 1 : 0; 
                int signField = (int)Math.pow(-1, bitS); 

                int expoField = 0;
                String eOfx = currentPrecision.substring(1, 1 + exponentWidth);
                int exponent = eOfx.length() - 1;
                String eMath = ""; 
                
                for(int a = 0; a < eOfx.length(); a++){
                    int bit = eOfx.charAt(a) == '1' ? 1:0;
                    int pVal = (int)(bit * Math.pow(2, exponent));
                    expoField += pVal;
                    eMath += bit + "(2^" + exponent + ")=" + pVal + " | ";
                    exponent --;
                } 

                int e = expoField - bias;
                double expoSum = Math.pow(2, e);

                double fractionField = 0;
                String fOfx = currentPrecision.substring(1 + exponentWidth);
                
                String sumStr = (expoSum * 1.0) + " + "; 
                String powStr = "2^" + e + " + ";
                String shiftStr = "";
                String fMath = ""; 
                
                int startExpo = -1;
                for(int j = 0; j < fOfx.length(); j++){
                    int bit = fOfx.charAt(j) == '1' ? 1:0;
                    double pValFrac = bit * (Math.pow(2, startExpo));
                    fractionField += pValFrac;
                    
                    if(bit == 1) {
                        double realVal = pValFrac * expoSum;
                        sumStr += realVal + " + ";
                        powStr += "2^" + (startExpo + e) + " + ";
                        shiftStr += "2^" + (startExpo + e) + "-" + e + " + ";
                        fMath += bit + "(2^" + startExpo + ")=" + pValFrac + " | ";
                    }
                    startExpo --;
                } 

                if (sumStr.endsWith(" + ")) sumStr = sumStr.substring(0, sumStr.length() - 3);
                if (powStr.endsWith(" + ")) powStr = powStr.substring(0, powStr.length() - 3);
                if (shiftStr.endsWith(" + ")) shiftStr = shiftStr.substring(0, shiftStr.length() - 3);

                double finalValueOfX = signField * expoSum * (1 + fractionField);

                System.out.println("\n--- " + precisionName + " Precision Analysis ---");
                System.out.println("S-Bit Process: s(x) = (-1)^" + bitS + " = " + signField);
                System.out.println("E-Bit Process: " + eMath + "e(x) = " + expoField);
                System.out.println("F-Bit Process: " + fMath + "f(x) = " + fractionField);
                
                System.out.println("\nValue for " + totalBits + "-bit: " + finalValueOfX);
                System.out.println("Value(x)= (-1)^" + bitS + " * (" + sumStr + ")");
                System.out.println("Value(x)= (-1)^" + bitS + " * (" + powStr + ")");
                System.out.println("Value(x)= (-1)^" + bitS + " * 2^" + e + " * ( 1 + " + shiftStr + " )");
                System.out.println("Value(x)= (-1)^" + bitS + " * 2^" + e + " * (1." + fOfx + ")");
                System.out.println("Value(x)= (-1)^" + bitS + " * 2^" + expoField + "-" + bias + " * (1." + fOfx + ")");
                System.out.println("s(x) = " + bitS + " \ne(x) = " + expoField + " (" + eOfx + ") \nf(x) = " + fOfx);
                System.out.println("Answer: " + finalValueOfX);
            }

        } else if (choice == 2) {
            System.out.print("Enter Decimal: ");
            double dIn = jo.nextDouble();

            for (int i = 1; i <= 4; i++) {
                int bts, ew, bias;
                String name;

                if (i == 1) { bts = 16; ew = 5; bias = 15; name = "16-bit"; }
                else if (i == 2) { bts = 32; ew = 8; bias = 127; name = "32-bit"; }
                else if (i == 3) { bts = 64; ew = 11; bias = 1023; name = "64-bit"; }
                else { bts = 128; ew = 15; bias = 16383; name = "128-bit"; }

                System.out.println("\nValue for " + name + ": " + dIn);

                int s = (dIn < 0) ? 1 : 0;
                double v = Math.abs(dIn);

                String sumStr = "";
                String powStr = "";
                String shiftStr = "";
                
                long iP = (long) v;
                double fP = v - iP;
                
                String b1 = "";
                long t1 = iP;
                int maxP = -1;
                for (int k = 62; k >= 0; k--) {
                    double pVal = Math.pow(2, k);
                    if (t1 >= pVal) {
                        if (maxP == -1) maxP = k;
                        t1 -= pVal;
                        b1 += "1";
                        sumStr += pVal + " + ";
                        powStr += "2^" + k + " + ";
                    } else if (maxP != -1) { b1 += "0"; }
                }

                String b2 = "";
                double t2 = fP;
                for (int k = 1; k <= bts; k++) {
                    double pVal = Math.pow(2, -k);
                    if (t2 >= pVal) {
                        if (maxP == -1) maxP = -k;
                        t2 -= pVal;
                        b2 += "1";
                        sumStr += pVal + " + ";
                        powStr += "2^-" + k + " + ";
                    } else { b2 += "0"; }
                    if (t2 <= 0 && b2.length() > 0) break;
                }

                if (sumStr.endsWith(" + ")) sumStr = sumStr.substring(0, sumStr.length() - 3);
                if (powStr.endsWith(" + ")) powStr = powStr.substring(0, powStr.length() - 3);

                String fFull = (b1.length() > 0) ? b1.substring(1) + b2 : b2.substring(b2.indexOf("1") + 1);
                String fPadded = fFull;
                int fLen = bts - 1 - ew;
                while (fPadded.length() < fLen) fPadded += "0";
                fPadded = fPadded.substring(0, fLen);

                for (int k = (maxP - 1); k >= (maxP - fLen); k--) {
                    shiftStr += "2^" + k + "-" + maxP + " + ";
                }
                if (shiftStr.endsWith(" + ")) shiftStr = shiftStr.substring(0, shiftStr.length() - 3);

                System.out.println("Value(x)= (-1)^" + s + " * (" + sumStr + ")");
                System.out.println("Value(x)= (-1)^" + s + " * (" + powStr + ")");
                System.out.println("Value(x)= (-1)^" + s + " * 2^" + maxP + " * ( 1 + " + shiftStr + " )");
                System.out.println("Value(x)= (-1)^" + s + " * 2^" + maxP + " * (1." + fPadded + ")");
                
                int eVal = maxP + bias;
                System.out.println("Value(x)= (-1)^" + s + " * 2^" + eVal + "-" + bias + " * (1." + fPadded + ")");
                
                String eBin = "";
                int t3 = eVal;
                for (int z = 0; z < ew; z++) {
                    eBin = (t3 % 2) + eBin;
                    t3 /= 2;
                }

                System.out.println("s(x) = " + s);
                System.out.println("e(x) = " + eVal + " = " + eBin);
                System.out.println("f(x) = " + fPadded);
                System.out.println("Answer: " + s + eBin + fPadded);
            }
        }
    }
}
