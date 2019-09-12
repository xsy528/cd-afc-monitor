package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.util.JdbcTemplateDao;
import com.insigma.afc.monitor.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/28 14:26.
 * @Ticket :
 */
@Service
public class StationServiceImpl implements StationService {
    private JdbcTemplateDao templateDao;
    @Autowired
    public void setTemplateDao(JdbcTemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    int lineId = 0;
    @Override
    public Map<Integer, String> getStationNameAndId() {
        String sql = "SELECT t.STATION_ID AS ID,t.STATION_NAME AS NAME FROM TMETRO_STATION t ";

        Map<Integer, String> data = new HashMap<>();

        data.put(4,"MLC-00");
//"成都"+lineId+"号线"
        List<Map<String, Object>> maps = templateDao.queryForMaps(sql, null);

        for(Map<String, Object> map:maps){
            data.put(Integer.valueOf(map.get("ID")+""),map.get("NAME")+"");
        }
        return data;
    }

    @Override
    public String getStationNameById(Integer id) {
        String sql = "SELECT t.STATION_NAME AS NAME FROM TMETRO_STATION t  WHERE 1=1 AND t.STATION_ID="+id;

        String stationName = "未知";
        List<Map<String, Object>> maps = templateDao.queryForMaps(sql, null);

        for(Map<String, Object> map:maps){
            stationName = map.get("NAME")+"";
        }
        return stationName;
    }
}
