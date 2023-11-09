package application.exPackage;

import mwf.annotations.Bean;
import mwf.enums.Scope;

@Bean(scope = Scope.PROTOTYPE)
public class ExClass2 {
    public ExClass2() {
    }

    public String exampleDI() {
        return "DI works!";
    }
}
