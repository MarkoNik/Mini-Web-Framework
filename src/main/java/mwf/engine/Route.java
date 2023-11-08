package mwf.engine;

import mwf.enums.MethodType;

import java.lang.reflect.Method;

public class Route {
    private final MethodType methodType;
    private final Method method;

    private final Object obj;

    public Route(MethodType methodType, Method method, Object obj) {
        this.methodType = methodType;
        this.method = method;
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public Object getObj() {
        return obj;
    }
}
