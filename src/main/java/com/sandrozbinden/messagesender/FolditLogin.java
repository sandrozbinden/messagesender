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
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;

public class FolditLogin {

    public static final String COOKIE_SESSION_NAME = "SESS430194f1f080aa797206c127656837f3";
    private static final String SET_COOKIE = "Set-Cookie";
    private String sessionID;
    private static final Logger logger = LoggerFactory.getLogger(FolditLogin.class);

    public FolditLogin() {

    }

    public void login() {
        try {
            Connection.Response initalResponse = executeInitalRequest();
            String initalSessionID = getInitalSessionID(initalResponse);

            Map<String, String> data = getLoginFormParameter(initalResponse);
            sessionID = executeLoginRequest(data, initalSessionID);
        } catch (IOException e) {
            throw new IllegalStateException("Can't login", e);
        }

    }

    public String getSessionID() {
        return sessionID;
    }

    private Connection.Response executeInitalRequest() throws IOException {
        Connection.Response loginForm = Jsoup.connect(Foldit.BASE_URL).method(Connection.Method.GET).execute();
        return loginForm;
    }

    private Map<String, String> getLoginFormParameter(Connection.Response initalResponse) throws IOException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", Setting.getInstance().getFolditUsername());
        data.put("pass", Setting.getInstance().getFolditPassword());
        data.put("op", "Log in");
        data.put("form_build_id", initalResponse.parse().select("[name=form_build_id]").val());
        data.put("form_id", "user_login_block");
        return data;
    }

    private String getInitalSessionID(Connection.Response response) throws IOException {
        return response.cookies().get(COOKIE_SESSION_NAME);
    }

    private String executeLoginRequest(Map<String, String> data, String initalSessionID) throws IOException {
        URL siteUrl = new URL(Foldit.LOGIN_URL);
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
            logger.info("Successfully logged in to Foldit page sessionKey: " + sessionKey + " value:" + id);
        } else {
            logger.error("Can't login to Foldit page check username and password in file: " + Setting.getInstance().getFile().getCanonicalPath());
            logger.error("Foldit response is:" + response);
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
