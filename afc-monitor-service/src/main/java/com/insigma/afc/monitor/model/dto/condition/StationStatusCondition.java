package com.insigma.afc.monitor.model.dto.condition;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-30:20:41
 */
public class StationStatusCondition {

    public StationStatusCondition(){

    }

    public StationStatusCondition(List<Integer> stationIds){
        this.stationIds = stationIds;
    }

    private List<Integer> stationIds;

    public List<Integer> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<Integer> stationIds) {
        this.stationIds = stationIds;
    }
}
