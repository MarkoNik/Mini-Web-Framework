package application.examplePackage;

import mwf.annotations.Autowired;
import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.Path;

@Controller(path = "exampleControllerDI")
public class ExampleController {
    @Autowired(verbose = true)
    public ExampleClass exampleClass;

    @GET
    @Path(path = "exampleMethodDI")
    public String exampleMethodDI() {
        return exampleClass.exampleDI();
    }

    public ExampleController() {
    }
}
