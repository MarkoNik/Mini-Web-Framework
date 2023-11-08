package application.exPackage;

import mwf.annotations.Autowired;
import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.Path;

@Controller(path = "exampleControllerDI")
public class ExController {
    @Autowired(verbose = true)
    public ExClass exClass;

    @GET
    @Path(path = "exampleMethodDI")
    public String exampleMethodDI() {
        return exClass.exampleDI();
    }

    public ExController() {
    }
}
