package ua.artcode.taxi.model;

public class ClientAccessToken {

   public static String accessToken;

   public static String getAccessToken() {
      return accessToken;
   }

   public static void setAccessToken(String accessToken) {
      ClientAccessToken.accessToken = accessToken;
   }
}
