package com.hcmus.Utils;

import java.util.*;

public interface MyCallback {
    void onCompleteDirection(List<List<HashMap<String, String>>> routes, List<Integer> distances);
    void onCompleteDistanceMatrix(List<HashMap<String, HashMap<String, String>>> results);
}
