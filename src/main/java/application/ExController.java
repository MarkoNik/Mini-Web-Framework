package application;

import mwf.annotations.*;

@Controller(path = "exController")
public class ExController {

    @Autowired(verbose = true)
    public ExBeanPrototype exBeanPrototype1;
    @Autowired(verbose = true)
    public ExBeanPrototype exBeanPrototype2;

    @Autowired(verbose = true)
    public ExBeanSingleton exBeanSingleton1;
    @Autowired(verbose = true)
    public ExBeanSingleton exBeanSingleton2;

    @Autowired(verbose = true)
    public ExService exService1;
    @Autowired(verbose = true)
    public ExService exService2;

    @Autowired(verbose = true)
    public ExComponent exComponent1;
    @Autowired(verbose = true)
    public ExComponent exComponent2;

    @Qualifier("QF1")
    @Autowired(verbose = true)
    public ExInterface exInterface1;
    @Qualifier("QF2")
    @Autowired(verbose = true)
    public ExInterface exInterface2;

    @GET
    @Path(path = "exMethod")
    public String exampleMethod() {
        return "controller works!";
    }

    @GET
    @Path(path = "exBeanSingleton")
    public String exBeanSingleton() {
        return "exBeanSingleton1 call: "
                + exBeanSingleton1.getCallCounter()
                + "; exBeanSingleton2 call: "
                + exBeanSingleton2.getCallCounter();
    }

    @GET
    @Path(path = "exBeanPrototype")
    public String exBeanPrototype() {
        return "exBeanPrototype1 call: "
                + exBeanPrototype1.getCallCounter()
                + "; exBeanPrototype2 call: "
                + exBeanPrototype2.getCallCounter();
    }

    @GET
    @Path(path = "exService")
    public String exService() {
        return "exService1 call: "
                + exService1.getCallCounter()
                + "; exService2 call: "
                + exService2.getCallCounter();
    }

    @GET
    @Path(path = "exComponent")
    public String exComponent() {
        return "exComponent1 call: "
                + exComponent1.getCallCounter()
                + "; exComponent2 call: "
                + exComponent2.getCallCounter();
    }

    @GET
    @Path(path = "exInterfaceQF1")
    public String exInterfaceQF1() {
        return exInterface1.exInterfaceFunction();
    }

    @GET
    @Path(path = "exInterfaceQF2")
    public String exInterfaceQF2() {
        return exInterface2.exInterfaceFunction();
    }

    public ExController() {
    }
}
