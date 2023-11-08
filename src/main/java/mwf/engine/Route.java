package mwf.engine;

import mwf.enums.MethodType;

import java.lang.reflect.Method;

public class Route {
    private final MethodType methodType;
    private final Method method;
    private Object object = null;

    public Route(MethodType methodType, Method method) {
        this.methodType = methodType;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
