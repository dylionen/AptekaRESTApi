package apteka.functionality;

import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class CodersClass {
    public static String MD5Code(String word) {
        MessageDigest messageDigest;
        byte[] digest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            digest = messageDigest.digest(word.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(digest);
        } catch (Exception e) {
            return "error";
        }
    }

    public static String authTokenGenerator() {
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static Date changeFormatDate(String dateToChange) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sdf.parse(dateToChange);
            d.setHours(23);
            d.setMinutes(59);
        } catch (ParseException e) {
            d = new Date();
        }
        return d;
    }


    @Test
    public void MDChecker() {
        System.out.println(changeFormatDate("2020-01-30"));
    }
}


