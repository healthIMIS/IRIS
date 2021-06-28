package iris.demo.checkin_app.startup;

import iris.demo.checkin_app.datasubmission.exceptions.IRISDataSubmissionException;
import iris.demo.checkin_app.searchindex.SearchIndexClient;
import iris.demo.checkin_app.searchindex.bootstrap.LocationsLoader;
import iris.demo.checkin_app.searchindex.model.LocationsDto;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!inttest")
@Slf4j
public class LocationUpdater {

    @Autowired
    private LocationsLoader locationsLoader;

    @Autowired
    private SearchIndexClient searchIndexClient;


    @PostConstruct
    public void init() {
        LocationsDto locations = locationsLoader.getDemoLocations();

        log.info(String.format("Updating %d locations", locations.getCount()));
        try {
            searchIndexClient.updateLocations(locations);
        } catch (IRISDataSubmissionException e) {
            e.printStackTrace();
        }

        log.info(String.format("Delete location %s", locations.getLocationByIndex(1).getName()));
        try {
            searchIndexClient.deleteLocation(locations.getLocationByIndex(1));
        } catch (IRISDataSubmissionException e) {
            e.printStackTrace();
        }
    }
}
