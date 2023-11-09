package application.exPackage;

import mwf.annotations.Autowired;
import mwf.annotations.Controller;
import mwf.annotations.GET;
import mwf.annotations.Path;

@Controller(path = "exControllerDI")
public class ExController {
    @Autowired(verbose = true)
    public ExClass exClass;

    @GET
    @Path(path = "exMethodDI")
    public String exMethodDI() {
        return exClass.exDI();
    }

    public ExController() {
    }
}
