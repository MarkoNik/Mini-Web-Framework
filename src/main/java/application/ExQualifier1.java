package application;

import mwf.annotations.Component;
import mwf.annotations.Qualifier;

@Component
@Qualifier(value = "QF1")
public class ExQualifier1 implements ExInterface {
    public ExQualifier1() {
    }
    public String exInterfaceFunction() {
        return "QF1 function invoked!";
    }
}
