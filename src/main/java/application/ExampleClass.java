package application;

import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.Path;

@Controller(path = "exampleController")
public class ExampleClass {
    @GET
    @Path(path = "exampleMethod")
    public String exampleMethod() {
        return "example method works!";
    }
}
