/**
 * 
 */
package com.insigma.afc.monitor.map;

import java.io.InputStream;
import java.io.OutputStream;

import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.application.IService;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public interface IMapSyncService extends IService {

	public MetroNode decode(InputStream in);

	public void encode(MetroNode node, OutputStream out) throws Exception;

}
