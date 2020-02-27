-- Create table
create table T_NIB_TRAN_SEQ
(
  uuid         VARCHAR2(72 CHAR),
  tran_seq     VARCHAR2(100 CHAR),
  datetime     VARCHAR2(34 CHAR),
  tx_code_new  VARCHAR2(40 CHAR),
  brch_no      VARCHAR2(20 CHAR),
  oper         VARCHAR2(20 CHAR),
  acctno       VARCHAR2(64 CHAR),
  totamt       VARCHAR2(80 CHAR),
  svrseqno     VARCHAR2(100 CHAR),
  idchkseqno   VARCHAR2(64 CHAR),
  idchkrst     VARCHAR2(160 CHAR),
  agtidseqno   VARCHAR2(64 CHAR),
  agtidrst     VARCHAR2(160 CHAR),
  imgseqno     VARCHAR2(120 CHAR),
  imgrst       VARCHAR2(160 CHAR),
  sealchkseqno VARCHAR2(64 CHAR),
  sealchkrst   VARCHAR2(160 CHAR),
  authid       VARCHAR2(64 CHAR),
  authrst      VARCHAR2(160 CHAR),
  authoper     VARCHAR2(20 CHAR),
  svrdate      VARCHAR2(16 CHAR),
  svrtime      VARCHAR2(64 CHAR),
  txstatus     VARCHAR2(100 CHAR),
  faultcode    VARCHAR2(40 CHAR),
  faultstring  VARCHAR2(400 CHAR),
  termno       VARCHAR2(64 CHAR),
  sessionid    VARCHAR2(100 CHAR),
  extend1      VARCHAR2(510 CHAR),
  extend2      VARCHAR2(510 CHAR),
  extend3      VARCHAR2(510 CHAR),
  dac          VARCHAR2(40 CHAR),
  etl_dt       DATE
)
partition by list (ETL_DT)
(
  partition P_19000101 values (TO_DATE(' 1900-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255,
  partition P_20180724 values (TO_DATE(' 2018-07-24 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
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
-- Add comments to the columns 
comment on column T_NIB_TRAN_SEQ.uuid
  is '流水号';
comment on column T_NIB_TRAN_SEQ.datetime
  is '时间戳';
comment on column T_NIB_TRAN_SEQ.tx_code_new
  is '新交易码';
comment on column T_NIB_TRAN_SEQ.brch_no
  is '机构号';
comment on column T_NIB_TRAN_SEQ.oper
  is '柜员号';
comment on column T_NIB_TRAN_SEQ.acctno
  is '帐号';
comment on column T_NIB_TRAN_SEQ.totamt
  is '交易金额(总计)';
comment on column T_NIB_TRAN_SEQ.svrseqno
  is '核心流水号';
comment on column T_NIB_TRAN_SEQ.idchkseqno
  is '身份证核查流水号';
comment on column T_NIB_TRAN_SEQ.idchkrst
  is '身份证核查结果';
comment on column T_NIB_TRAN_SEQ.agtidseqno
  is '代理人身份证流水号';
comment on column T_NIB_TRAN_SEQ.agtidrst
  is '代理人身份证核查结果';
comment on column T_NIB_TRAN_SEQ.imgseqno
  is '影像流水号';
comment on column T_NIB_TRAN_SEQ.imgrst
  is '影像结果';
comment on column T_NIB_TRAN_SEQ.sealchkseqno
  is '验印流水号';
comment on column T_NIB_TRAN_SEQ.sealchkrst
  is '验印结果';
comment on column T_NIB_TRAN_SEQ.authid
  is '授权流水号';
comment on column T_NIB_TRAN_SEQ.authrst
  is '授权结果';
comment on column T_NIB_TRAN_SEQ.authoper
  is '授权员工号';
comment on column T_NIB_TRAN_SEQ.svrdate
  is '核心日期';
comment on column T_NIB_TRAN_SEQ.svrtime
  is '服务时间';
comment on column T_NIB_TRAN_SEQ.txstatus
  is '交易状态';
comment on column T_NIB_TRAN_SEQ.faultcode
  is '错误码';
comment on column T_NIB_TRAN_SEQ.faultstring
  is '错误信息';
comment on column T_NIB_TRAN_SEQ.termno
  is '终端号';
comment on column T_NIB_TRAN_SEQ.sessionid
  is '回话id';
comment on column T_NIB_TRAN_SEQ.extend1
  is '备用字段1';
comment on column T_NIB_TRAN_SEQ.extend2
  is '备用字段2';
comment on column T_NIB_TRAN_SEQ.extend3
  is '备用字段3';
comment on column T_NIB_TRAN_SEQ.dac
  is '保留字段';
comment on column T_NIB_TRAN_SEQ.etl_dt
  is '数据日期';
-- Grant/Revoke object privileges 
grant select on T_NIB_TRAN_SEQ to IML;
grant select on T_NIB_TRAN_SEQ to IOL;
