package com.argmax.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import com.argmax.model.UserProfile;

public class StackOverflowClient {
    public StackOverflowClient() {

    }

    public JSONObject retrieveUserData() {
        URL url;
        try {
            // !TODO: Actually import from config
            url = new URL("https://api.stackexchange.com/2.2/users?site=stackoverflow");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            // !TODO: Make configurable 
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            Reader streamReader = null;

            int status;
            status = con.getResponseCode();

            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }

            BufferedReader in = new BufferedReader(streamReader);
        
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            return new JSONObject(content.toString());

        } 
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public List<UserProfile> retrieveUserProfiles() {
        JSONObject userData = this.retrieveUserData();
        JSONArray userDataArray = userData.getJSONArray("items"); 
        
        ArrayList<UserProfile> ret = new ArrayList<>();

        // !TODO: Configurable top N
        int topN = userDataArray.length() > 10 ? 10 : userDataArray.length();
        for (int i = 0; i < topN; i++) {
            UserProfile profile = new UserProfile();
            profile.setUserId(userDataArray.getJSONObject(i).getInt("user_id"));

            try {
                profile.setPfpURL(new URL(userDataArray.getJSONObject(i).getString("profile_image")));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            ret.add(profile);
        } 

        return ret;
    }
}
