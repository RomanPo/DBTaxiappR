package ua.artcode.taxi.model;

import java.util.logging.Logger;

public class ClientAccessToken {

   private static final Logger log = Logger.getLogger(String.valueOf(ClientAccessToken.class));
   public static String accessToken;

   public static String getAccessToken() {
      log.info("Get access token is in use");
       return accessToken;
   }

   public static void setAccessToken(String accessToken) {
       log.info("Setting the access token");
       ClientAccessToken.accessToken = accessToken;
   }
}
