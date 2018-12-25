package com.insigma.acc.workbench.monitor.dao;

import com.insigma.acc.workbench.monitor.model.entity.MetroStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetroStationDao extends JpaRepository<MetroStation,Short> {
}
