package application;

import mwf.annotations.Bean;
import mwf.enums.Scope;

@Bean(scope = Scope.PROTOTYPE)
public class ExBeanPrototype {
    private int callCounter = 0;
    public ExBeanPrototype() {
    }
    public String getCallCounter() {
        return String.valueOf(++callCounter);
    }
}
