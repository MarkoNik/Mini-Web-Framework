package mwf.engine;

import java.util.HashMap;
import java.util.Map;

public class GlobalRouteMapping {
    private static final GlobalRouteMapping instance = new GlobalRouteMapping();
    private Map<String, String> routeMap;

    private GlobalRouteMapping() {
        routeMap = new HashMap<>();
    }

    public static GlobalRouteMapping getInstance() {
        return instance;
    }

    public Map<String, String> getRouteMap() {
        return routeMap;
    }

    public void insertRoute(String key, String value) {
        routeMap.put(key, value);
    }
}
