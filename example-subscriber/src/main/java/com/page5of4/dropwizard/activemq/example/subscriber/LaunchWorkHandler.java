package com.page5of4.dropwizard.activemq.example.subscriber;

import com.page5of4.codon.AutomaticallySubscribe;
import com.page5of4.codon.Bus;
import com.page5of4.codon.MessageHandler;
import com.page5of4.dropwizard.activemq.example.publisher.LaunchWorkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

@MessageHandler(autoSubscribe = AutomaticallySubscribe.ALWAYS)
public class LaunchWorkHandler {
   private static final Logger logger = LoggerFactory.getLogger(LaunchWorkHandler.class);
   private final Bus bus;

   @Autowired
   public LaunchWorkHandler(Bus bus) {
      super();
      this.bus = bus;
   }

   public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

   @MessageHandler
   public void handle(LaunchWorkMessage message) {
      logger.info("Received {}", message);
      try {
         SecureRandom random = new SecureRandom();
         byte[] salt = new byte[1024];
         random.nextBytes(salt);
         byte[] hash = pbkdf2(message.getId().toString().toCharArray(), salt, message.getWorkSize(), 24);
      }
      catch(Exception e) {
         throw new RuntimeException(e);
      }
   }

   private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
      PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
      SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
      return skf.generateSecret(spec).getEncoded();
   }
}
