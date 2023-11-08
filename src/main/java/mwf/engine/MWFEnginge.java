package mwf.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MWFEnginge {
    private final List<Class<?>> classList;
    private String applicationPackage;
    public MWFEnginge(String applicationPackage) throws ClassNotFoundException {

        this.applicationPackage = applicationPackage;
        classList = new ArrayList<>();

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
}
