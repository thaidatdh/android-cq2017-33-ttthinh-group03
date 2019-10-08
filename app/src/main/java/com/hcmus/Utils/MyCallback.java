package com.hcmus.Utils;

import java.util.*;

public interface MyCallback {
    void onComplete(List<List<HashMap<String, String>>> routes, List<Integer> distances);
}
