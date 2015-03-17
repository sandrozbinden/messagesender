package com.sandrozbinden.messagesender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;

public class EternaLogin {

    private String sessionID;
    public static final String COOKIE_SESSION_NAME = "SESSe9a857e9c6ca7c5e20d14a1815f2230f";
    private static final Logger logger = LoggerFactory.getLogger(EternaLogin.class);
    private static final String SET_COOKIE = "Set-Cookie";

    public void login() {
        try {
            Connection.Response initalResponse = executeInitalRequest();
            String initalSessionID = getInitalSessionID(initalResponse);

            Map<String, String> data = getLoginFormParameter();
            sessionID = executeLoginRequest(data, initalSessionID);
        } catch (IOException e) {
            throw new IllegalStateException("Can't login", e);
        }

    }

    private Map<String, String> getLoginFormParameter() throws IOException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", Setting.getInstance().getEternaUsername());
        data.put("pass", Setting.getInstance().getEternaPassword());
        data.put("type", "login");
        return data;
    }

    private String getInitalSessionID(Response initalResponse) {
        return initalResponse.cookie("SESSe9a857e9c6ca7c5e20d14a1815f2230f");
    }

    public String getSessionID() {
        return sessionID;
    }

    private Connection.Response executeInitalRequest() throws IOException {
        Connection.Response loginForm = Jsoup.connect(Eterna.BASE_URL).method(Connection.Method.GET).execute();
        logger.debug(loginForm.parse().toString());
        return loginForm;
    }

    private String executeLoginRequest(Map<String, String> data, String initalSessionID) throws IOException {
        URL siteUrl = new URL(Eterna.LOGIN_URL);
        HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36");
        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Cookie", COOKIE_SESSION_NAME + "=" + initalSessionID);
        conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setUseCaches(true);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(20000);
        conn.setInstanceFollowRedirects(false);

        try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
            String dataAsContent = getContent(data);
            out.writeBytes(dataAsContent);
            out.flush();
        }

        String response = CharStreams.toString(new InputStreamReader(conn.getInputStream()));
        logger.debug("Eterna login response: " + response);

        String headerName = null;
        String id = null;
        String sessionKey = null;
        for (int i = 1; (headerName = conn.getHeaderFieldKey(i)) != null; i++) {
            if (headerName.equalsIgnoreCase(SET_COOKIE)) {
                logger.debug(conn.getHeaderField(i));
                sessionKey = conn.getHeaderField(i).split(";")[0].split("=")[0];
                id = conn.getHeaderField(i).split(";")[0].split("=")[1];
            }
        }
        if (sessionKey != null && id != null) {
            logger.info("Successfully logged in to Etera page sessionKey: " + sessionKey + " value:" + id);
        } else {
            logger.error("Can't login to eterna page check username and password in file: " + Setting.getInstance().getFile().getCanonicalPath());
            logger.error("Eterna response is:" + response);
            throw new IllegalStateException("Can't login to eterna page see response: " + response);
        }
        return id;

    }

    private String getContent(Map<String, String> data) throws UnsupportedEncodingException {
        Set<String> keys = data.keySet();
        Iterator<String> keyIter = keys.iterator();
        String content = "";
        for (int i = 0; keyIter.hasNext(); i++) {
            Object key = keyIter.next();
            if (i != 0) {
                content += "&";
            }
            content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
        }
        return content;
    }

}
