package com.insigma.acc.workbench.monitor.dao;

import com.insigma.acc.workbench.monitor.model.entity.MetroLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetroLineDao extends JpaRepository<MetroLine,Short> {
}
