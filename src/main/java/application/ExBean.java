package application;

import mwf.annotations.Bean;
import mwf.enums.Scope;

@Bean(scope = Scope.PROTOTYPE)
public class ExBean {
    public ExBean() {
        System.out.println("Example Bean works!");
    }
}