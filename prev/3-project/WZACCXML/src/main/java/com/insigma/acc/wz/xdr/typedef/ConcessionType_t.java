package com.insigma.acc.wz.xdr.typedef;

/*
 *;
 */
public interface ConcessionType_t {
	// 不优惠 ;
	public final static int CTYPE_NoConcession = 0;
	// 重复优惠 ;
	public final static int CTYPE_RepeatConcession = 1;
	// 联乘优惠 ;
	public final static int CTYPE_JoinConcession = 2;
	// 累积优惠 ;
	public final static int CTYPE_PileConcession = 3;
}
