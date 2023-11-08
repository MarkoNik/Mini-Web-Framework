package mwf.engine;

import mwf.annotations.Autowired;
import mwf.annotations.Bean;
import mwf.annotations.Controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIEngine {
    private List<Class<?>> classList;
    private List<Class<? extends Controller>> controllerList;
    private Map<Class<? extends Controller>, Object> controllerObjectMap;
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

    private Object instantiateClass(Class<?> controller) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object instance = controller.getDeclaredConstructor().newInstance();
        for (Field field : controller.getDeclaredFields()) {
            Object fieldInstance;
            if (field.getAnnotation(Autowired.class) != null) {
                fieldInstance = instantiateClass(field.getType());
            }
            else {
                fieldInstance = field.getDeclaringClass().getDeclaredConstructor().newInstance();
            }
            field.set(instance, fieldInstance);
        }
        return instance;
    }

    public Map<Class<? extends Controller>, Object> getControllerObjectMap() {
        return controllerObjectMap;
    }
}
