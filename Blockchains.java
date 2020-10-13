import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blockchains {

    public static void main(String[] args) {
        try {
            byte[] hashId = hash_id("church", "alonzo");
            StringBuilder sb = new StringBuilder();
            for (byte b : hashId) {
                sb.append(String.format("%02x", b));
            }
            System.out.println("Test Church:Alonzo  =  " + sb.toString());

            byte[] hashValue = hash_value(hashId, 123);
            StringBuilder sb2 = new StringBuilder();
            for (byte b : hashValue) {
                sb2.append(String.format("%02x", b));
            }
            System.out.println("Test hash_value = " + sb2.toString());


            System.out.println("Test is_valide = "+is_valid("church","alonzo", 13015, 15));

            System.out.println("Test mine = "+mine("church", "alonzo", 15));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Structure des identifiants: question 1
    public static byte[] hash_id(String nom, String prenom) throws NoSuchAlgorithmException {
        String id = nom.toLowerCase() + ":" + prenom.toLowerCase();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(id.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] bytes_of_hex(String hex) {
        int size = hex.length();
        byte[] data = new byte[size / 2];
        for (int i = 0; i < size; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    private static byte[] encode(int n) {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(n);
        return b.array();
    }

    // Structure des identifiants: question 2
    public static byte[] hash_value(byte[] bytes, int nonce) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] b1 = encode(nonce);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] bytes2 = new byte[8];
        System.arraycopy(bytes, 0, bytes2, 0, 8);
        outputStream.write(bytes2);
        outputStream.write(b1);
        byte[] res = outputStream.toByteArray();
        return digest.digest(res);
    }

    // Minage: question 1
    public static int count_zero_prefix(String s) {
        int res = 0;
        for (char c : s.toCharArray()) {
            if (c == '0')
                res++;
            else
                break;
        }
        return res;
    }

    // Minage: question 2
    public static boolean is_valid(String nom, String prenom, int nonce, int n)
            throws NoSuchAlgorithmException, IOException {
        byte[] value = hash_value(hash_id(nom, prenom), nonce);
        StringBuilder sb = new StringBuilder();
        for (byte b1 : value) {
            sb.append(String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0'));
        }
        int nb = count_zero_prefix(sb.toString());
        return nb >= n;
    }

    // Minage: question 3
    public static int mine(String nom, String prenom, int n) throws NoSuchAlgorithmException, IOException {
        int nonce = 0;
        while (!is_valid(nom, prenom, nonce, n)) {
            nonce++;
        }
        return nonce;
    }

}