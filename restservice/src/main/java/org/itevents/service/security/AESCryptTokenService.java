package org.itevents.service.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.service.CryptTokenService;
import org.itevents.service.exception.CryptTokenServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

/**
 * Created by ramax on 1/15/16.
 */
@Service
public class AESCryptTokenService implements CryptTokenService {

    @Value("${aes.init.vector.hex}")
    private String INIT_VECTOR_HEX;
    @Value("${aes.key.hex}")
    private String KEY_HEX;

    @Inject
    private ObjectMapper objectMapper;

    public AESCryptTokenService(String initVectorHex, String keyHex) {
        this.INIT_VECTOR_HEX = initVectorHex;
        this.KEY_HEX = keyHex;
    }

    public AESCryptTokenService() {
    }

    @Override
    public String encrypt(Token token) {
        try {
            String jsonToken = objectMapper.writeValueAsString(token);
            return encryptAES(jsonToken);
        } catch (Exception e) {
            throw new CryptTokenServiceException("Error encrypt token");
        }
    }

    @Override
    public Token decrypt(String token) {
        try {
            String jsonToken = decryptAES(token);
            return objectMapper.readValue(jsonToken, Token.class);
        } catch (Exception e) {
            throw new CryptTokenServiceException("Error decrypt token");
        }
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
}
