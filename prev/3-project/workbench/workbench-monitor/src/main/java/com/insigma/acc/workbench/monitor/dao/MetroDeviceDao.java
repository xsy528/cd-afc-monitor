package com.insigma.acc.workbench.monitor.dao;

import com.insigma.acc.workbench.monitor.model.entity.MetroDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetroDeviceDao extends JpaRepository<MetroDevice,Short> {
}
