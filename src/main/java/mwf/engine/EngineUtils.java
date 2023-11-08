package mwf.engine;

import mwf.annotations.Controller;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EngineUtils {

    public static List<Class<?>> findClasses() throws ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String classPath = System.getProperty("java.class.path");
        String[] classPathEntries = classPath.split(File.pathSeparator);
        String applicationPackage = "application";

        // We are only looking for classes within the currently running app,
        // hence we use only the first element of CLASSPATH.
        String applicationClassPath = classPathEntries[0] + "/" + applicationPackage;
        File classRootDir = new File(applicationClassPath);
        findClassesHelper(classRootDir, applicationPackage, classList);
        return classList;
    }

    private static void findClassesHelper(File dir, String currentPackage, List<Class<?>> classList) throws ClassNotFoundException {
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                findClassesHelper(file, currentPackage + '.' + file.getName(), classList);
            }
            else if (file.getName().endsWith(".class")) {
                String className = currentPackage + '.' + file.getName().substring(0, file.getName().length() - 6);
                classList.add(Class.forName(className));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Class<? extends T>> filterClassesByAnnotation(List<Class<?>> classList, Class<T> annotationType) {
        return classList.stream()
                .filter(clazz -> clazz.isAnnotationPresent((Class<? extends Annotation>) annotationType))
                .map(clazz -> (Class<? extends T>) clazz)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Method> filterMethodsByAnnotation(List<Method> methodList, Class<T> annotationType) {
        return methodList.stream()
                .filter(method -> method.isAnnotationPresent((Class<? extends Annotation>) annotationType))
                .collect(Collectors.toList());
    }
}
