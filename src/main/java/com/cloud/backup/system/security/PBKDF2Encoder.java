package com.cloud.backup.system.security;


import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RequestScoped
public class PBKDF2Encoder {


    @ConfigProperty(name = "com.cloud.backup.system.password.secret")  private String secret;
    @ConfigProperty(name = "com.cloud.backup.system.password.iteration")  private Integer iteration;
    @ConfigProperty(name = "com.cloud.backup.system.password.keylength")  private Integer keylength;

    /**
     *
     * @param cs password
     * @return encoded password
     */
    public String encode(CharSequence cs) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }
}
