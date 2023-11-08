package application.exPackage;

import mwf.annotations.Autowired;

public class ExClass {

    @Autowired(verbose = true)
    public ExClass2 exClass2;

    public ExClass() {
    }

    public String exampleDI() {
        return exClass2.exampleDI();
    }
}
