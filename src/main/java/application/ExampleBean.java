package application;

import mwf.annotations.Bean;
import mwf.enums.Scope;

@Bean(scope = Scope.PROTOTYPE)
public class ExampleBean {
    public ExampleBean() {
        System.out.println("Example Bean works!");
    }
}
