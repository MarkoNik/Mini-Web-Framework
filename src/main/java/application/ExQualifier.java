package application;

import mwf.annotations.Qualifier;

@Qualifier(value = "QF1")
public class ExQualifier implements ExInterface {
    public ExQualifier() {
    }
    public String exInterfaceFunction() {
        return "QF1 function invoked!";
    }
}
