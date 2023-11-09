package mwf.engine;

import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.POST;
import mwf.annotations.Path;
import mwf.enums.MethodType;

import java.lang.reflect.Method;
import java.util.*;

public class RouteEngine {
    private List<Class<?>> classList;
    private final List<Class<? extends Controller>> controllerList;
    private final Map<String, Route> routeMap;

    public RouteEngine(List<Class<?>> classList) throws Exception {
        this.classList = classList;
        this.controllerList = EngineUtils.filterClassesByAnnotation(classList, Controller.class);
        routeMap = new HashMap<>();

        mapRoutes();
    }

    private void mapRoutes() throws Exception {
        for (Class<? extends Controller> controller : controllerList) {
            for (Method method : controller.getDeclaredMethods()) {
                Path path = method.getAnnotation(Path.class);
                if (path == null) continue;

                Route route = getRoute(method, controller);
                String key = "/" + controller.getAnnotation(Controller.class).path() + "/" + path.path();
                routeMap.put(key.toLowerCase(), route);
            }
        }
    }

    private static Route getRoute(Method method, Class<?> classIter) throws Exception {
        GET get = method.getAnnotation(GET.class);
        POST post = method.getAnnotation(POST.class);
        if (get != null && post != null) {
            throw new Exception("Method cannot be both GET and POST: " + method.getName());
        }
        if (get == null && post == null) {
            throw new Exception("Method has to either be GET or POST: " + method.getName());
        }

        MethodType methodType;
        if (get != null) methodType = MethodType.GET;
        else methodType = MethodType.POST;
        return new Route(methodType, method);
    }

    public List<Class<? extends Controller>> getControllerList() {
        return controllerList;
    }

    public Map<String, Route> getRouteMap() {
        return routeMap;
    }
}
