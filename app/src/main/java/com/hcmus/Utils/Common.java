package com.hcmus.Utils;

import com.hcmus.Models.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Common {
    public static void sortTaskListAsc(final List<Task> tasks){
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                HashMap<String, String> distance1 = task1.getDistance();
                HashMap<String, String> distance2 = task2.getDistance();
                String dis1, dis2;
                if (distance1 != null && (dis1 = distance1.get("value")) != null
                        && distance2 != null && (dis2 = distance2.get("value")) != null ){
                    return Integer.parseInt(dis1) - Integer.parseInt(dis2);
                }

                return 0;
            }
        });
    }
}
