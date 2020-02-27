-- Create table
create table T_TST_PCPED
(
  faredm VARCHAR2(6 CHAR),
  prodcd VARCHAR2(15 CHAR),
  qudaoo VARCHAR2(5 CHAR),
  acterm VARCHAR2(9 CHAR),
  prodcu VARCHAR2(3 CHAR),
  jigouh VARCHAR2(6 CHAR),
  zonged NUMBER(17,2),
  weiyed NUMBER(17,2),
  yiyoed NUMBER(17,2),
  keyned NUMBER(17,2),
  eduzht VARCHAR2(2 CHAR),
  shfobz VARCHAR2(2 CHAR),
  beiyje NUMBER(17,2),
  byye01 NUMBER(17,2),
  byye02 NUMBER(17,2),
  byzd01 VARCHAR2(300 CHAR),
  byzd02 VARCHAR2(300 CHAR),
  byzd03 VARCHAR2(300 CHAR),
  byrq01 VARCHAR2(12 CHAR),
  byrq02 VARCHAR2(12 CHAR),
  edbhao VARCHAR2(45 CHAR),
  weihgy VARCHAR2(12 CHAR),
  weihjg VARCHAR2(6 CHAR),
  weihrq VARCHAR2(12 CHAR),
  weihsj NUMBER(20),
  shjnch NUMBER(20),
  recsta VARCHAR2(2 CHAR),
  etl_dt DATE
)
partition by list (ETL_DT)
(
  partition P_19000101 values (TO_DATE(' 1900-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255,
  partition P_20190127 values (TO_DATE(' 2019-01-27 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190128 values (TO_DATE(' 2019-01-28 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190112 values (TO_DATE(' 2019-01-12 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190129 values (TO_DATE(' 2019-01-29 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190130 values (TO_DATE(' 2019-01-30 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190131 values (TO_DATE(' 2019-01-31 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190201 values (TO_DATE(' 2019-02-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190202 values (TO_DATE(' 2019-02-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190203 values (TO_DATE(' 2019-02-03 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190204 values (TO_DATE(' 2019-02-04 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190205 values (TO_DATE(' 2019-02-05 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190206 values (TO_DATE(' 2019-02-06 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190207 values (TO_DATE(' 2019-02-07 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190208 values (TO_DATE(' 2019-02-08 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190209 values (TO_DATE(' 2019-02-09 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190210 values (TO_DATE(' 2019-02-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190211 values (TO_DATE(' 2019-02-11 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190212 values (TO_DATE(' 2019-02-12 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190213 values (TO_DATE(' 2019-02-13 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190214 values (TO_DATE(' 2019-02-14 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190215 values (TO_DATE(' 2019-02-15 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190216 values (TO_DATE(' 2019-02-16 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190217 values (TO_DATE(' 2019-02-17 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190218 values (TO_DATE(' 2019-02-18 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190219 values (TO_DATE(' 2019-02-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190220 values (TO_DATE(' 2019-02-20 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190221 values (TO_DATE(' 2019-02-21 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190222 values (TO_DATE(' 2019-02-22 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190223 values (TO_DATE(' 2019-02-23 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190224 values (TO_DATE(' 2019-02-24 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190225 values (TO_DATE(' 2019-02-25 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190226 values (TO_DATE(' 2019-02-26 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190227 values (TO_DATE(' 2019-02-27 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190228 values (TO_DATE(' 2019-02-28 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190301 values (TO_DATE(' 2019-03-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190302 values (TO_DATE(' 2019-03-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition P_20190303 values (TO_DATE(' 2019-03-03 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
    storage
    (
      initial 8M
      next 1M
      minextents 1
      maxextents unlimited
    )
);
-- Add comments to the table 
comment on table T_TST_PCPED
  is '产品额度控制';
-- Add comments to the columns 
comment on column T_TST_PCPED.faredm
  is '法人代码';
comment on column T_TST_PCPED.prodcd
  is '产品代号';
comment on column T_TST_PCPED.qudaoo
  is '渠道';
comment on column T_TST_PCPED.acterm
  is '存期';
comment on column T_TST_PCPED.prodcu
  is '币种';
comment on column T_TST_PCPED.jigouh
  is '机构号';
comment on column T_TST_PCPED.zonged
  is '总额度';
comment on column T_TST_PCPED.weiyed
  is '未用额度';
comment on column T_TST_PCPED.yiyoed
  is '已实际发售额度';
comment on column T_TST_PCPED.keyned
  is '已下拨额度';
comment on column T_TST_PCPED.eduzht
  is '额度状态';
comment on column T_TST_PCPED.shfobz
  is '是否可以上级额度';
comment on column T_TST_PCPED.beiyje
  is '备用金额';
comment on column T_TST_PCPED.byye01
  is '备用余额1';
comment on column T_TST_PCPED.byye02
  is '备用余额2';
comment on column T_TST_PCPED.byzd01
  is '备用字段1';
comment on column T_TST_PCPED.byzd02
  is '备用字段2';
comment on column T_TST_PCPED.byzd03
  is '备用字段3';
comment on column T_TST_PCPED.byrq01
  is '备用日期1';
comment on column T_TST_PCPED.byrq02
  is '备用日期2';
comment on column T_TST_PCPED.edbhao
  is '额度编号id';
comment on column T_TST_PCPED.weihgy
  is '维护柜员';
comment on column T_TST_PCPED.weihjg
  is '维护机构';
comment on column T_TST_PCPED.weihrq
  is '维护日期';
comment on column T_TST_PCPED.weihsj
  is '维护时间';
comment on column T_TST_PCPED.shjnch
  is '时间戳';
comment on column T_TST_PCPED.recsta
  is '记录状态';
comment on column T_TST_PCPED.etl_dt
  is '数据日期';
-- Grant/Revoke object privileges 
grant select on T_TST_PCPED to IML;
grant select on T_TST_PCPED to IOL;
