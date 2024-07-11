package com.argmax.client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.OutputStream;

import com.argmax.model.UserImageBlob;
import com.argmax.model.UserRecognizedObjects;

public class InferenceClient {
    public InferenceClient() {

    }

    public List<UserRecognizedObjects> retrieveInferenceResults(List<UserImageBlob> userImages) {
        List<UserRecognizedObjects> ret = new ArrayList<>();

        try {
            URL inference_url = new URL("http://localhost:5000/run_inference");
            for (UserImageBlob blob: userImages) {
                if (blob.getImageBlob() != null) {
                    HttpURLConnection con = (HttpURLConnection) inference_url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/raw");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoOutput(true);

                    WritableRaster imageRaster = ((BufferedImage) blob.getImageBlob()).getRaster();
                    DataBufferByte data = (DataBufferByte) imageRaster.getDataBuffer();

                    OutputStream stream = con.getOutputStream();
                    stream.write(data.getData());

                    System.out.println(con.getResponseCode());
                    con.disconnect();
                }
            }    
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
