package mwf.engine;

import mwf.annotations.Controller;

import java.util.List;
import java.util.Map;

public class EngineCoordinator {
    private static final EngineCoordinator instance;
    private final RouteEngine routeEngine;
    private final DIEngine diEngine;

    private final List<Class<?>> classList;
    private Map<String, Route> routeMap;

    private Map<Class<? extends Controller>, Object> controllerObjectMap;

    static {
        try {
            instance = new EngineCoordinator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EngineCoordinator() throws Exception {
        this.classList = EngineUtils.findClasses();
        routeEngine = new RouteEngine(classList);
        diEngine = new DIEngine(classList);

        routeMap = routeEngine.getRouteMap();
        controllerObjectMap = diEngine.getControllerObjectMap();
        instantiateRoutes();
    }

    private void instantiateRoutes() {
        for(Route route : routeMap.values()) {
            route.setObject(controllerObjectMap.get(route.getMethod().getDeclaringClass()));
        }
    }

    public Map<String, Route> getRouteMap() {
        return routeMap;
    }

    public static EngineCoordinator getInstance() {
        return instance;
    }
}
