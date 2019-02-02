package com.insigma.afc.monitor.model.properties;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-24:18:59
 */
public class AFCProperties {

    private String deviceIdFormat = "%08x";

    private String stationlineIdFormat = "%04x";

    private String lineIdFormat = "%02x";

    private String stationIdFormat = "%02x";

    private String deviceTypeFormat = "%02x";

    public String getDeviceIdFormat() {
        return deviceIdFormat;
    }

    public void setDeviceIdFormat(String deviceIdFormat) {
        this.deviceIdFormat = deviceIdFormat;
    }

    public String getStationlineIdFormat() {
        return stationlineIdFormat;
    }

    public void setStationlineIdFormat(String stationlineIdFormat) {
        this.stationlineIdFormat = stationlineIdFormat;
    }

    public String getLineIdFormat() {
        return lineIdFormat;
    }

    public void setLineIdFormat(String lineIdFormat) {
        this.lineIdFormat = lineIdFormat;
    }

    public String getStationIdFormat() {
        return stationIdFormat;
    }

    public void setStationIdFormat(String stationIdFormat) {
        this.stationIdFormat = stationIdFormat;
    }

    public String getDeviceTypeFormat() {
        return deviceTypeFormat;
    }

    public void setDeviceTypeFormat(String deviceTypeFormat) {
        this.deviceTypeFormat = deviceTypeFormat;
    }
}
