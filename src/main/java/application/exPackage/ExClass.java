package application.exPackage;

import mwf.annotations.Autowired;

public class ExClass {

    @Autowired(verbose = true)
    public ExClass2 exClass2;

    public ExClass2 exClass3;

    public ExClass() {
    }

    public String exDI() {
        return exClass2.exampleDI();
    }
}
