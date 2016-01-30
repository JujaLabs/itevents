package org.itevents.service.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.codehaus.jackson.map.ObjectMapper;
import org.itevents.service.CryptTokenService;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ramax on 1/15/16.
 */
@Component
public class AESCryptTokenService implements CryptTokenService {

    @Value("${aes.init.vector.hex}")
    private String initVectorHex;

    @Value("${aes.key.hex}")
    private String keyHex;

    private ObjectMapper mapper = new ObjectMapper();

    private String encryptAES(String str) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(initVectorHex.toCharArray()));
        SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(keyHex.toCharArray()), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(str.getBytes());

        return Base64.encodeBase64String(encrypted);
    }

    private String decryptAES(String str) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(initVectorHex.toCharArray()));
        SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(keyHex.toCharArray()), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decodeBase64(str));

        return new String(original);
    }

    @Override
    public String encrypt(Token token) {
        try {
            String strToken = mapper.writeValueAsString(token);
            return encryptAES(strToken);
        } catch (Exception e) {
            throw new AESCryptTokenException("Error encrypt token", e);
        }
    }

    @Override
    public Token decrypt(String token) {
        try {
            String strToken = decryptAES(token);
            return mapper.readValue(strToken, Token.class);
        } catch (Exception e) {
            throw new AESCryptTokenException("Error decrypt token", e);
        }
    }
}
