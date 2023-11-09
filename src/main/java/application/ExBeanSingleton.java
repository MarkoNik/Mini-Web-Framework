package application;

import mwf.annotations.Bean;
import mwf.enums.Scope;

@Bean(scope = Scope.SINGLETON)
public class ExBeanSingleton {
    private int callCounter = 0;
    public ExBeanSingleton() {
    }
    public String getCallCounter() {
        return String.valueOf(++callCounter);
    }
}
