package models.dto.intern.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StreetsResponce {
    public StreetsResponce(Collection<models.geo.Street> streets) {
        for (models.geo.Street s : streets) {
            this.streets.add(new Street(s));
        }
    }

    boolean success = true;
    List<Street> streets = new ArrayList<Street>();
}
