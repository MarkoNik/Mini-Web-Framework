package application.exPackage;

import mwf.annotations.Autowired;
import mwf.annotations.Bean;
import mwf.enums.Scope;

@Bean(scope = Scope.PROTOTYPE)
public class ExClass1 {

    @Autowired(verbose = true)
    public ExClass2 exClass2;

    public ExClass2 exClass3;

    public ExClass1() {
    }

    public String exDI() {
        return exClass2.exampleDI();
    }
}
