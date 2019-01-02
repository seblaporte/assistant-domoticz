package fr.seblaporte.assistantdomoticz.service;

import fr.seblaporte.assistantdomoticz.util.HttpUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


@Service
public class GoogleAccountService {

    public String getJwt() throws IOException {

        File file = ResourceUtils.getFile("classpath:assistant-domoticz-report-state.json");
        FileInputStream stream = new FileInputStream(file);
        ServiceAccountCredentials serviceAccount = ServiceAccountCredentials.fromStream(stream);
        JwtBuilder jwts = Jwts.builder();

        // set claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("exp", System.currentTimeMillis() / 1000 + 3600);
        claims.put("iat", System.currentTimeMillis() / 1000);
        claims.put("iss", serviceAccount.getClientEmail());
        claims.put("aud", "https://accounts.google.com/o/oauth2/token");
        claims.put("scope", "https://www.googleapis.com/auth/homegraph");

        jwts.setClaims(claims).signWith(SignatureAlgorithm.RS256, serviceAccount.getPrivateKey());

        return jwts.compact();
    }

    public String getAccessToken(String jwt) throws JSONException, IOException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
        parameters.put("assertion", jwt);

        StringEntity params = new StringEntity(HttpUtils.formEncode(parameters));

        HttpEntity entity = HttpUtils.httpRequest("https://accounts.google.com/o/oauth2/token", jwt, params,
                "application/x-www-form-urlencoded");

        if (entity != null) {
            final StringBuilder out = HttpUtils.readResponse(entity);
            String res = out.toString();
            if (res.indexOf("access_token") > 0) {
                String token = res.split(":")[1];
                token = token.substring(2, token.indexOf(",") - 1);
                return token;
            }
        }
        return "";
    }

}
