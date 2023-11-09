package application;

import mwf.annotations.Service;

@Service
public class ExService {
    private int callCounter = 0;
    public ExService() {
    }
    public String getCallCounter() {
        return String.valueOf(++callCounter);
    }
}
