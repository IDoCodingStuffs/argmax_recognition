package com.argmax.client;

import com.argmax.model.UserProfile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import com.argmax.model.UserImageBlob;

public class ImageRetrievalClient {
    public ImageRetrievalClient() {

    }

    public List<UserImageBlob> retrieveUserImages(List<UserProfile> userProfiles) {
        List<UserImageBlob> ret = new ArrayList<>();

        // !TODO: Use some connection pool instead of sleep
        for (UserProfile profile : userProfiles) {
            UserImageBlob blob = new UserImageBlob();
            blob.setUserId(profile.getUserId());
            
            try {
                URL url = profile.getPfpURL();
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "image/png+jpg");
                // !TODO: More elaborate user agent
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
                con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                
                InputStream in = new BufferedInputStream(con.getInputStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                Image img = ImageIO.read(new ByteArrayInputStream(response));

                blob.setImageBlob(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ret.add(blob);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
