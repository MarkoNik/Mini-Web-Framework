package mwf.engine;

import mwf.annotations.Autowired;
import mwf.annotations.Bean;
import mwf.annotations.Controller;
import mwf.annotations.Service;
import mwf.enums.Scope;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIEngine {
    private List<Class<?>> classList;
    private List<Class<? extends Controller>> controllerList;
    private final Map<Class<? extends Controller>, Object> controllerObjectMap;
    private Map<Class<? extends Bean>, Object> singletonBeanCache;

    public DIEngine(List<Class<?>> classList) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        controllerObjectMap = new HashMap<>();
        singletonBeanCache = new HashMap<>();
        this.classList = classList;
        this.controllerList = EngineUtils.filterClassesByAnnotation(classList, Controller.class);
        for (Class <? extends Controller> controller : this.controllerList) {
            controllerObjectMap.put(controller, instantiateClass(controller));
        }
    }

    @SuppressWarnings("unchecked")
    private Object instantiateClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        boolean shouldUseBeanCache = isSingletonBean(clazz);
        // if class is a Bean singleton or Service, try extracting object from cache
        if (shouldUseBeanCache && singletonBeanCache.containsKey(clazz)) {
            return singletonBeanCache.get(clazz);
        }

        Object instance = clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {

            // if the field is autowired
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                Object fieldInstance = instantiateClass(field.getType());
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
