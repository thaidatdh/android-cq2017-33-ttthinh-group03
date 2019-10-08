package com.hcmus.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.hcmus.shipe.R;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;
import java.util.*;

public class MapUtils {
    private Context mContext;
    private static String mKey;
    private String directionAPIUrl;
    private String travelingType;
    public MapUtils(Context context){
        this.mContext = context;
        mKey = context.getString(R.string.google_maps_key);
        directionAPIUrl = "https://maps.googleapis.com/maps/api/directions/";
        this.travelingType = "";
    }

    public void callDirectionAPI(LatLng origin, LatLng destination, HashMap<String, String> params, MyCallback callback){
        String url = createDirectionRequestUrl(origin, destination, params);
        travelingType = "one-one";
        DownloadTask downloadTask = new DownloadTask(callback);
        downloadTask.execute(url);
    }
    public void callDirectionAPIWithWaypoints(LatLng origin, List<LatLng> waypoints, HashMap<String, String> params, MyCallback callback){
        LatLng destination = waypoints.get(waypoints.size() - 1);
        waypoints.remove(waypoints.size() - 1);
        String url = createDirectionWithWaypointsquestUrl(origin, destination, waypoints, params);
        travelingType = "one-many";
        DownloadTask downloadTask = new DownloadTask(callback);
        downloadTask.execute(url);
    }
    private class DownloadTask extends AsyncTask<String, Integer, String> {
        final MyCallback callback;
        private DownloadTask(MyCallback cb) {
            this.callback = cb;
        }

        @Override
        protected String doInBackground(String... url){
            String data = "";
            try {
                data = fetchDataFromUrl(url[0]);
            } catch(Exception e){
                Log.d("Download Task: ", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask(callback);
            parserTask.execute(result);
        }
    }
    private class ParserTask extends AsyncTask<String, Integer, List<Object>> {
        final MyCallback callback;

        private ParserTask(MyCallback cb) {
            this.callback = cb;
        }

        @Override
        protected List<Object> doInBackground(String...  data){
            JSONObject json;
            List<Object> result = new ArrayList<Object>();
            switch (travelingType){
                case "one-one":
                case "one-many":
                    List<List<HashMap<String, String>>> routes = null;
                    List<Integer> distances = null;
                    try {
                        json = new JSONObject(data[0]);
                        DirectionsJSONParser parser = new DirectionsJSONParser();
                        routes = parser.parseRoutes(json);
                        distances = parser.parseDistances(json);

                    }  catch(Exception e){
                        Log.d("Parser Task: ", e.toString());
                    }
                    result.add(routes);
                    result.add(distances);
            }
            return result;
        }
        @Override
        protected void onPostExecute(List<Object> result){
            switch(travelingType){
                case "one-one":
                case "one-many":
                    this.callback.onComplete((List<List<HashMap<String, String>>>)result.get(0), (List<Integer>)result.get(1));
                    break;
            }

        }
    }
    private String createDirectionRequestUrl(LatLng origin, LatLng destination, Map<String, String> params){
        String format = "json";
        String url = directionAPIUrl + format + "?";

        String originStr = "origin=" + origin.latitude + "," + origin.longitude;
        String destinationStr = "destination=" + destination.latitude + "," + destination.longitude;
        String parameters = "";
        for (String key : params.keySet()){
            parameters += "&" + key.toLowerCase() + "=" + params.get(key).toLowerCase();
        }
        parameters += "&key=" + mKey;
        return url + originStr + "&" + destinationStr + parameters;
    }
    private String createDirectionWithWaypointsquestUrl(LatLng origin, LatLng destination, List<LatLng> waypoints, Map<String, String> params){
        String format = "json";
        String url = directionAPIUrl + format + "?";

        String originStr = "origin=" + origin.latitude + "," + origin.longitude;
        String destinationStr = "destination=" + destination.latitude + "," + destination.longitude;
        String parameters = "";
        for (String key : params.keySet()){
            parameters += "&" + key.toLowerCase() + "=" + params.get(key).toLowerCase();
        }
        //Waypoints
        parameters += "&waypoints=";
        for (LatLng waypoint : waypoints){
            parameters += waypoint.latitude + "," + waypoint.longitude + "|";
        }
        parameters = parameters.substring(0, parameters.length() - 1);

        parameters += "&key=" + mKey;
        return url + originStr + "&" + destinationStr + parameters;
    }
    private String fetchDataFromUrl(String requestUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(requestUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e){
            Log.d("Exception: ", e.toString());
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
