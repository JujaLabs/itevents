package org.itevents.service.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.map.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

/**
 * Created by ramax on 1/15/16.
 */
public class AESCryptTokenService implements CryptTokenService {

    private final String INIT_VECTOR_HEX;
    private final String KEY_HEX;

    @Inject
    private ObjectMapper objectMapper;

    public AESCryptTokenService(String initVectorHex, String keyHex) {
        this.INIT_VECTOR_HEX = initVectorHex;
        this.KEY_HEX = keyHex;
    }

    private String encryptAES(String str) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(INIT_VECTOR_HEX.toCharArray()));
        SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(KEY_HEX.toCharArray()), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(str.getBytes());

        return Base64.encodeBase64String(encrypted);
    }

    private String decryptAES(String jsonToken) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(INIT_VECTOR_HEX.toCharArray()));
        SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(KEY_HEX.toCharArray()), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decodeBase64(jsonToken));

        return new String(original);
    }

    @Override
    public String encrypt(Token token) {
        try {
            String jsonToken = objectMapper.writeValueAsString(token);
            return encryptAES(jsonToken);
        } catch (Exception e) {
            throw new AESCryptTokenException("Error encrypt token", e);
        }
    }

    @Override
    public Token decrypt(String token) {
        try {
            String jsonToken = decryptAES(token);
            return objectMapper.readValue(jsonToken, Token.class);
        } catch (Exception e) {
            throw new AESCryptTokenException("Error decrypt token", e);
        }
    }

}
