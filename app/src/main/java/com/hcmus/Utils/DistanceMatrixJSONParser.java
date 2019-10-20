package com.hcmus.Utils;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DistanceMatrixJSONParser {
    public List<HashMap<String, HashMap<String, String>>> parseDistances(JSONObject jObject){
        List<HashMap<String, HashMap<String, String>>> results = new ArrayList<HashMap<String, HashMap<String, String>>>();
        JSONArray jRows;
        JSONArray jElements;

        try {
            jRows = (JSONArray)jObject.getJSONArray("rows");
            jElements = ((JSONObject)jRows.get(0)).getJSONArray("elements");
            for (int i = 0; i < jElements.length(); i++){
                HashMap<String, String> distance = new HashMap<String, String>();
                HashMap<String, String> duration = new HashMap<String, String>();
                HashMap<String, HashMap<String, String>> result = new HashMap<String, HashMap<String, String>>();
                String status = (String)((JSONObject)jElements.get(i)).get("status");
                if (status.equals("OK")){
                    distance.put("text", (String)((JSONObject)((JSONObject)jElements.get(i)).get("distance")).get("text"));
                    distance.put("value", String.valueOf((Integer)((JSONObject)((JSONObject)jElements.get(i)).get("distance")).get("value")));
                    result.put("distance", distance);
                    duration.put("text", (String)((JSONObject)((JSONObject)jElements.get(i)).get("duration")).get("text"));
                    duration.put("value", String.valueOf((Integer)((JSONObject)((JSONObject)jElements.get(i)).get("duration")).get("value")));
                    result.put("duration", duration);
                }
                else {
                    distance.put("text", "-1");
                    distance.put("value", "-1");
                    result.put("distance", distance);
                    duration.put("text", "-1");
                    duration.put("value", "-1");
                    result.put("duration", duration);
                }
                results.add(result);
            }
        } catch(JSONException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        return results;
    }
}
