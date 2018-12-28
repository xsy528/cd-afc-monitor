-- Create table
create table TMO_OD_FLOW_STATS
(
  LINE_ID             NUMBER(5) not null,
  STATION_ID          NUMBER(11) not null,
  GATHERING_DATE      DATE not null,
  TIME_INTERVAL_ID    NUMBER(20) not null,
  UP_IN_HEAD_COUNT    NUMBER(11) default 0 not null,
  DOWN_IN_HEAD_COUNT  NUMBER(11) default 0 not null,
  UP_OUT_HEAD_COUNT   NUMBER(11) default 0 not null,
  DOWN_OUT_HEAD_COUNT NUMBER(11) default 0 not null,
  TICKET_FAMILY       NUMBER(5) default 0 not null,
  ADD_COUNT           NUMBER(11) default 0 not null,
  SALE_COUNT          NUMBER(11) default 0 not null,
  TOTAL_IN            NUMBER(11) default 0 not null,
  TOTAL_OUT           NUMBER(11) default 0 not null
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table TMO_OD_FLOW_STATS
  is '进出站客流统计表';
-- Add comments to the columns 
comment on column TMO_OD_FLOW_STATS.LINE_ID
  is '线路代码';
comment on column TMO_OD_FLOW_STATS.STATION_ID
  is '车站代码';
comment on column TMO_OD_FLOW_STATS.GATHERING_DATE
  is '发生时间';
comment on column TMO_OD_FLOW_STATS.TIME_INTERVAL_ID
  is '时间段编号';
comment on column TMO_OD_FLOW_STATS.UP_IN_HEAD_COUNT
  is '进站上行数';
comment on column TMO_OD_FLOW_STATS.DOWN_IN_HEAD_COUNT
  is '进站下行数';
comment on column TMO_OD_FLOW_STATS.UP_OUT_HEAD_COUNT
  is '出站上行数 ';
comment on column TMO_OD_FLOW_STATS.DOWN_OUT_HEAD_COUNT
  is '出站下行数';
comment on column TMO_OD_FLOW_STATS.TICKET_FAMILY
  is '票卡归类类型（City Card Family: 1 通卡（一卡通）；Staff PASS Family: 2 员工票；SingleJourney Family: 3 单程票；Exit Token Family: 4 出站票；Multi-ride Family: 5 计次票（多程票）；Limit Period Family: 6   限期票；Stored Value Family: 7 储值票）';
comment on column TMO_OD_FLOW_STATS.ADD_COUNT
  is '充值数';
comment on column TMO_OD_FLOW_STATS.SALE_COUNT
  is '售票数';
comment on column TMO_OD_FLOW_STATS.TOTAL_IN
  is '进站总数';
comment on column TMO_OD_FLOW_STATS.TOTAL_OUT
  is '出站总数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TMO_OD_FLOW_STATS
  add constraint PK_TMO_OD_FLOW_STATS_ primary key (GATHERING_DATE, LINE_ID, STATION_ID, TIME_INTERVAL_ID, TICKET_FAMILY)
  using index 
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
