package application;

import mwf.annotations.Autowired;
import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.Path;

@Controller(path = "exampleController")
public class ExampleController {

    @GET
    @Path(path = "exampleMethod")
    public String exampleMethod() {
        return "example method works!";
    }

    public ExampleController() {
    }
}
