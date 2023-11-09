package application;

import mwf.annotations.Component;

@Component
public class ExComponent {
    private int callCounter = 0;
    public ExComponent() {
    }
    public String getCallCounter() {
        return String.valueOf(++callCounter);
    }
}
