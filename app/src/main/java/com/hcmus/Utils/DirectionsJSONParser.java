package com.hcmus.Utils;

import com.google.android.gms.maps.model.LatLng;

import org.json.*;

import java.lang.reflect.Array;
import java.util.*;

public class DirectionsJSONParser {
    public List<List<HashMap<String, String>>> parseRoutes(JSONObject jObject){
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jObject.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++){
                jLegs = ((JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String, String>>();

                for (int j = 0; j < jLegs.length(); j++){
                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");
                    String startAddress = (String)((JSONObject)jLegs.get(j)).get("start_address");
                    String endAddress = (String)((JSONObject)jLegs.get(j)).get("end_address");
                    HashMap<String, String> hm = null;
                    for (int k = 0; k < jSteps.length(); k++){
                        String polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List list = decodePoly(polyline);

                        for (int l = 0; l < list.size(); l++){
                            hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude));
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude));
                            if (l == 0 && k == 0)
                                hm.put("address", startAddress);
                            path.add(hm);
                        }
                    }
                    if (hm != null && j == jLegs.length() - 1){
                        hm.put("address", endAddress);
                    }
                    routes.add(path);
                }
            }

        } catch(JSONException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        return routes;
    }
    public List<Integer> parseDistances(JSONObject jObject){
        List<Integer> distances = new ArrayList<Integer>();
        JSONArray jRoutes;
        JSONArray jLegs;

        try {
            jRoutes = jObject.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++){
                jLegs = ((JSONObject)jRoutes.get(i)).getJSONArray("legs");
                Integer distance = 0;

                for (int j = 0; j < jLegs.length(); j++){
                    distance +=  (Integer)((JSONObject)((JSONObject)jLegs.get(j)).get("distance")).get("value");

                }
                distances.add(distance);
            }

        } catch(JSONException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        return distances;
    }
    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List decodePoly(String encoded) {
        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
