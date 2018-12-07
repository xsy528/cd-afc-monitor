/**
 * 
 */
package com.insigma.afc.topology;

import java.util.Comparator;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class MetroNodeComparator implements Comparator<MetroNode> {

	@Override
	public int compare(MetroNode o1, MetroNode o2) {
		return (int) (o1.id() - o2.id());
	}

	public final static MetroNodeComparator comparator = new MetroNodeComparator();
}
