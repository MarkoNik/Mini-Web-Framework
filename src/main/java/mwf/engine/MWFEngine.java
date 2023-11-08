package mwf.engine;

import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.POST;
import mwf.annotations.Path;
import mwf.enums.MethodType;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

public class MWFEngine {
    private static MWFEngine instance;

    private final String applicationPackage = "application";
    private final List<Class<?>> classList;
    private final Map<String, Route> routeMap;
    private MWFEngine() throws Exception {
        classList = new ArrayList<>();
        routeMap = new HashMap<>();

        initializeClasses();
        mapRoutes();
    }

    private void initializeClasses() throws ClassNotFoundException {
        String classPath = System.getProperty("java.class.path");
        String[] classPathEntries = classPath.split(File.pathSeparator);

        // We are only looking for classes within the currently running app,
        // hence we use only the first element of CLASSPATH.
        String applicationClassPath = classPathEntries[0] + "/" + applicationPackage;
        File classRootDir = new File(applicationClassPath);
        findClasses(classRootDir, applicationPackage);
    }

    private void findClasses(File dir, String currentPackage) throws ClassNotFoundException {
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                findClasses(file, currentPackage + '.' + file.getName());
            }
            else if (file.getName().endsWith(".class")) {
                String className = currentPackage + '.' + file.getName().substring(0, file.getName().length() - 6);
                classList.add(Class.forName(className));
            }
        }
    }

    private void mapRoutes() throws Exception {
        for (Class<?> classIter : classList) {
            Controller controller = classIter.getAnnotation(Controller.class);
            if (controller == null) continue;
            for (Method method : classIter.getMethods()) {
                Path path = method.getAnnotation(Path.class);
                if (path == null) continue;

                Route route = getRoute(method, classIter);
                String key = "/" + controller.path() + "/" + path.path();
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
        return new Route(methodType, method, classIter.getDeclaredConstructor().newInstance());
    }

    public Map<String, Route> getRouteMap() {
        return routeMap;
    }


    public static MWFEngine getInstance() throws Exception {
        if (instance == null) {
            instance = new MWFEngine();
        }
        return instance;
    }
}
