package mwf.engine;

import mwf.annotations.*;
import mwf.enums.Scope;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIEngine {
    private List<Class<?>> classList;
    private List<Class<? extends Controller>> controllerList;
    private final Map<Class<? extends Controller>, Object> controllerObjectMap;
    private Map<Class<? extends Bean>, Object> singletonBeanCache;
    private Map<String, Class<?>> dependencyContainer;

    public DIEngine(List<Class<?>> classList) throws Exception {
        controllerObjectMap = new HashMap<>();
        singletonBeanCache = new HashMap<>();

        this.classList = classList;
        initializeDependencyContainer();

        controllerList = EngineUtils.filterClassesByAnnotation(classList, Controller.class);
        for (Class <? extends Controller> controller : this.controllerList) {
            controllerObjectMap.put(controller, instantiateClass(controller));
        }
    }

    private void initializeDependencyContainer() {
        for (Class <?> clazz : this.classList) {
            Qualifier qualifier = clazz.getAnnotation(Qualifier.class);
            if (qualifier != null) {
                dependencyContainer.put(qualifier.value(), clazz);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Object instantiateClass(Class<?> clazz) throws Exception {
        boolean shouldUseBeanCache = isSingletonBean(clazz);
        // if class is a Bean singleton or Service, try extracting object from cache
        if (shouldUseBeanCache && singletonBeanCache.containsKey(clazz)) {
            return singletonBeanCache.get(clazz);
        }

        Object instance = clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            Class <?> fieldClass = field.getType();
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                // if autowired field is not a bean, service or component
                // throw an exception
                if (!fieldClass.isAnnotationPresent(Bean.class)
                    && !fieldClass.isAnnotationPresent(Service.class)
                    && !fieldClass.isAnnotationPresent(Component.class)) {
                    throw new Exception("Autowired field is not a Bean, Service or Component: "
                            + fieldClass.getSimpleName());
                }

                // if autowired field is an interface,
                // and it isn't annotated with a qualifier
                // throw an exception
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (fieldClass.isInterface() && qualifier == null) {
                    throw new Exception("Autowired field is an interface without a qualifier: "
                            + fieldClass.getSimpleName());
                }

                Object fieldInstance;
                if (fieldClass.isInterface()) {
                    fieldInstance = instantiateClass(dependencyContainer.get(qualifier.value()));
                }
                else {
                    fieldInstance = instantiateClass(fieldClass);
                }

                // if verbose log object creation
                field.set(instance, fieldInstance);

                if (autowired.verbose()) {
                    System.out.println(
                            "Initialized "
                                    + fieldInstance.getClass().getSimpleName()
                                    + " "
                                    + field.getName()
                                    + " in "
                                    + clazz.getSimpleName()
                                    + " on "
                                    + LocalDateTime.now()
                                    + " with "
                                    + fieldInstance.hashCode()
                    );
                }
            }
        }
        // cache the Bean
        if (shouldUseBeanCache) {
            singletonBeanCache.put((Class<? extends Bean>) clazz, instance);
        }
        return instance;
    }

    private boolean isSingletonBean(Class<?> controller) {
        Bean bean = controller.getAnnotation(Bean.class);
        return (bean != null && bean.scope() == Scope.SINGLETON)  // is Bean with singleton scope
                || controller.isAnnotationPresent(Service.class); // is a Service
    }

    public Map<Class<? extends Controller>, Object> getControllerObjectMap() {
        return controllerObjectMap;
    }
}
