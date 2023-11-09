package application.exPackage;

import mwf.annotations.Autowired;
import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.Path;

@Controller(path = "exControllerDI")
public class ExController {
    @Autowired(verbose = true)
    public ExClass1 exClass1;

    @GET
    @Path(path = "exMethodDI")
    public String exMethodDI() {
        return exClass1.exDI();
    }

    public ExController() {
    }
}
