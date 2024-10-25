package com.example.backend.utility;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class KeyUtil {

//    // Tạo khóa đối xứng (HS256)
//    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    public Key getSecretKey() {
//        return SECRET_KEY;
//    }

    // Chuỗi SECRET_KEY Base64
    private static final String secretKeyBase64 = "t2aOpa8RfS60CueihTCA+P+qsAQIC73uE8zMk4kNAAc=";

    // Chuyển đổi chuỗi Base64 thành Key
    private static final Key SECRET_KEY = decodeBase64ToKey(secretKeyBase64);

    private static Key decodeBase64ToKey(String base64Key) {
        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        // Tạo Key từ mảng byte với thuật toán HmacSHA256
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    public static Key getSecretKey() {
        return SECRET_KEY;
    }
}
