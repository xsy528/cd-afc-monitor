package com.insigma.afc.monitor.model.dto.condition;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-30:20:41
 */
public class StationStatusCondition {

    private List<Integer> stationIds;

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
    }
}
