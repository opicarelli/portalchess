package br.com.portal.chess.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtils {

    private final static String SECRET_KEY = "P0rT4lxX";
    private final static String HEX = "0123456789ABCDEF";
    private final static String ALGORITHM = "DES";

    /**
     * Encrypt data
     *
     * @param data
     *            - data to encrypt
     * @return Encrypted data
     * @throws Exception
     */
    public static String cipher(String data) throws Exception {
        // Key has to be of length 8
        if (SECRET_KEY == null || SECRET_KEY.length() != 8)
            throw new Exception("Invalid key length - 8 bytes key needed!");

        SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return toHex(cipher.doFinal(data.getBytes()));
    }

    /**
     * Decrypt data
     *
     * @param data
     *            - data to decrypt
     * @return Decrypted data
     * @throws Exception
     */
    public static String decipher(String data) throws Exception {
        // Key has to be of length 8
        if (SECRET_KEY == null || SECRET_KEY.length() != 8)
            throw new Exception("Invalid key length - 8 bytes key needed!");

        SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        return new String(cipher.doFinal(toByte(data)));
    }

    // Helper methods

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;

        byte[] result = new byte[len];

        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] stringBytes) {
        StringBuffer result = new StringBuffer(2 * stringBytes.length);

        for (int i = 0; i < stringBytes.length; i++) {
            result.append(HEX.charAt((stringBytes[i] >> 4) & 0x0f)).append(HEX.charAt(stringBytes[i] & 0x0f));
        }

        return result.toString();
    }

}