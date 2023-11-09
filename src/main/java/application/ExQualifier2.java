package application;

import mwf.annotations.Qualifier;

@Qualifier(value = "QF2")
public class ExQualifier2 implements ExInterface {
    public ExQualifier2() {
    }
    public String exInterfaceFunction() {
        return "QF2 function invoked!";
    }
}
