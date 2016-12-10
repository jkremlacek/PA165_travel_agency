package cz.muni.fi.pa165.travelagency.sampledata;

import java.io.IOException;

/**
 * Populates database with sample data.
 * 
 * @author Jakub Kremláček
 */
public interface SampleDataLoadingFacade {
    void loadData() throws IOException;
}
