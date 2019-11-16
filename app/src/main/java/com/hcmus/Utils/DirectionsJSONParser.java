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
        JSONArray viaWaypoints;

        List<HashMap<String, String>> addressLatLng = new ArrayList<>();
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
                            path.add(hm);
                        }
                    }
                    //Empty HashMap to seperate
                    HashMap<String, String> emptyHm = new HashMap<>();
                    path.add(emptyHm);

                    //Start Location
                    HashMap<String, String> startHm = new HashMap<>();
                    JSONObject startLocation = (JSONObject)((JSONObject)jLegs.get(j)).get("start_location");
                    startHm.put("lat", startLocation.get("lat").toString());
                    startHm.put("lng", startLocation.get("lng").toString());
                    addressLatLng.add(startHm);

                    viaWaypoints = ((JSONObject)jLegs.get(j)).getJSONArray("via_waypoint");
                    for (int l = 0; l < viaWaypoints.length(); l++){
                        String latStr = ((JSONObject)((JSONObject)viaWaypoints.get(l)).get("location")).get("lat").toString();
                        String lngStr = ((JSONObject)((JSONObject)viaWaypoints.get(l)).get("location")).get("lng").toString();
                        hm = new HashMap<String, String>();
                        hm.put("lat", latStr);
                        hm.put("lng", lngStr);
                        addressLatLng.add(hm);
                    }

                    //End Location
                    HashMap<String, String> endHm = new HashMap<>();
                    JSONObject endLocation = (JSONObject)((JSONObject)jLegs.get(j)).get("end_location");
                    endHm.put("lat", endLocation.get("lat").toString());
                    endHm.put("lng", endLocation.get("lng").toString());
                    addressLatLng.add(endHm);

                    path.addAll(addressLatLng);
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
