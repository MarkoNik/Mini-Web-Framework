package mwf.framework.request;

import mwf.enums.MethodType;

import java.util.HashMap;

public class Request {

    private final MethodType method;
    private final String location;
    private final Header header;
    private final HashMap<String, String> parameters;

    public Request() {
        this(MethodType.GET, "/");
    }

    public Request(MethodType method, String location) {
        this(method, location, new Header(), new HashMap<String, String>());
    }

    public Request(MethodType method, String location, Header header, HashMap<String, String> parameters) {
        this.method = method;
        this.location = location;
        this.header = header;
        this.parameters = parameters;
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public HashMap<String, String> getParameters() {
        return new HashMap<String, String>(this.parameters);
    }

    public boolean isMethod(MethodType method) {
        return this.getMethod().equals(method);
    }

    public MethodType getMethod() {
        return method;
    }

    public String getLocation() {
        return location;
    }

    public Header getHeader() {
        return header;
    }
}
