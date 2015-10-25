import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by makjdrn on 2015-10-24.
 */
public class HashKey {

    public static String Cryptogram = "00101110 11110000 00000111 01110001 00101111 00100101 11010011 11110111 01000000 10110001 01111110 01001010 00100100 00111110 11000101 00111101 11011010 00110110 10011011 00000011 11110110 10011110 10000101 10110010 11101111 10111101 00101100 00101100 10110100 11110110 00100011 11111011 01110110 00101001 01111001 01111000 10110101 00110101 11101101 01001001 11011011 11111111 11101111 11011000 00011001 01000110 11111010 01000110 11101001 00000110 00111111 00100101 11100101 11111010 01111110 11110001 00011110 01101111 00011101 00010010 01110011 00111001 01011110 01110101";
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String password = "12345678";
        md.update(password.getBytes());
        byte byteData[] = md.digest();


        Cryptogram = Cryptogram.replaceAll("\\s+", "");
        //convert the byte to hex format method 2
        BigInteger op = new BigInteger("4294967280");
        BigInteger ii = new BigInteger("1");

        String s;
        int sbin;
        String zero = "00000000";
        String strsbin;
        String halfkey = "8e9d019d";
        String result;
        String asciiresult = null;
        BigInteger i = new BigInteger("1");
        while(i.compareTo(op) == -1) {
            StringBuffer hex2String = new StringBuffer();
            String nexthex = i.toString(16);

            if (nexthex.length() == 1) hex2String.append('0');
            hex2String.append(nexthex);
            s = hex2String.toString() + halfkey;
            strsbin = new BigInteger(s, 16).toString(2);
            //System.out.println("Hex2 format : " + s+ " " + strsbin);
            //System.out.println("Hex2 format : " + s+ " " + strsbin);
            result = Xor(strsbin, Cryptogram);
            //System.out.println("result = " + result);
            if(result.length() % 8 == 0)
                asciiresult = toAscii(result);
            if(asciiresult != null)
                if(CheckifCorrect(asciiresult))
                    System.out.println(asciiresult);
            i = i.add(ii);
        }
    }

    private static boolean CheckifCorrect(String asciiresult) {
        char c;
        boolean good = true;
        int i = 0;
        int length = asciiresult.length();
        while(good && i < length ) {
            c = asciiresult.charAt(i);
            if(((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == 32 || c == 44 || c == 46))
                good = true;
            else
                good = false;
        }
        return good;
    }

    private static String toAscii(String result) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < result.length(); i+=8)
                sb.append((char) Integer.parseInt(result.substring(i, i + 8), 2));
            String s;
            s = sb.toString();
            return s;
    }

    private static String Xor(String strsbin, String cryptogram) {
        String key;
        String pom;
        StringBuilder stb = new StringBuilder();
        pom = cryptogram.substring(0, strsbin.length());
        for (int l = 0; l < pom.length(); l++) {
            stb.append(pom.charAt(l) ^ strsbin.charAt(l));
        }
        key = stb.toString();
        return key;
    }

}
