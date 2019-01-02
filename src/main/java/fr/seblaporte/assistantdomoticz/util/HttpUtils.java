package fr.seblaporte.assistantdomoticz.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.Map;

public class HttpUtils {

    public static HttpEntity httpRequest(String url, String token, StringEntity params, String type) throws IOException {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Authorization", "Bearer " + token);

        // Request parameters and other properties.
        httppost.addHeader("content-type", type);
        httppost.setEntity(params);

        // Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        return entity;
    }

    public static StringBuilder readResponse(HttpEntity entity) throws IOException {

        InputStream instream = entity.getContent();
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();

        try {
            Reader in = new InputStreamReader(instream, "UTF-8");
            while (true) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        } finally {
            instream.close();
        }
        return out;
    }

    public static String formEncode(Map<String, String> m) {
        String s = "";
        for (String key : m.keySet()) {
            if (s.length() > 0)
                s += "&";
            s += key + "=" + m.get(key);
        }
        return s;
    }
}
