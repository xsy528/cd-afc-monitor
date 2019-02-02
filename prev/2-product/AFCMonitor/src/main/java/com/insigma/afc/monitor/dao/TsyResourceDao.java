package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.AFCResource;
import com.insigma.afc.monitor.model.entity.AFCResourcePK;
import com.insigma.afc.monitor.model.entity.TsyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-25:17:51
 */
@Repository
public interface TsyResourceDao extends JpaRepository<TsyResource, AFCResourcePK> {

    @Query("SELECT DISTINCT nameSpace FROM TsyResource ORDER BY nameSpace")
    List<String> selectAllNamespace();

    Optional<TsyResource> findFirstByNameSpaceAndName(String name, String namespace);

    @Query("SELECT new com.insigma.afc.monitor.model.entity.AFCResource(name,nameSpace,md5,remark) FROM TsyResource " +
            "WHERE nameSpace=:namespace")
    List<AFCResource> findByNameSpace(@Param("namespace") String namespace);

    @Query("SELECT new com.insigma.afc.monitor.model.entity.AFCResource(name,nameSpace,md5,remark) FROM TsyResource")
    List<AFCResource> findAllAFCResource();
}
