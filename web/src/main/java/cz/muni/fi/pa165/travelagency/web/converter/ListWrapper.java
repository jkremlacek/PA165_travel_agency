package cz.muni.fi.pa165.travelagency.web.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16.12.2016.
 *
 * @author Martin Salata
 */
public class ListWrapper {
    private List<String> functionList;

    public ListWrapper() {
        this.functionList = new ArrayList<String>();
    }

    public List<String> getFunctionList() {
        return functionList;       
    }

    public void setFunctionList(List<String> functionList) {
        this.functionList = functionList;
    }

    public void add(String function) {
        this.functionList.add(function);
    }
}

