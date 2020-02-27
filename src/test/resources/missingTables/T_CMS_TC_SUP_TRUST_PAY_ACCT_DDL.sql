-- Create table
create table T_CMS_TC_SUP_TRUST_PAY_ACCT
(
  rcv_date    VARCHAR2(16 CHAR),
  leg_per_cod VARCHAR2(40 CHAR),
  prv_cod     VARCHAR2(22 CHAR),
  opn_dep     VARCHAR2(22 CHAR),
  tal_dep     VARCHAR2(22 CHAR),
  due_num     VARCHAR2(60 CHAR),
  pay_acct    VARCHAR2(64 CHAR),
  pay_name    VARCHAR2(200 CHAR),
  pay_amt     NUMBER(17,2),
  create_time TIMESTAMP(6),
  etl_dt      DATE
)
partition by list (ETL_DT)
(
  partition P_19000101 values (TO_DATE(' 1900-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace TBS_ITL
    pctfree 0
    initrans 1
    maxtrans 255
);
-- Add comments to the table 
comment on table T_CMS_TC_SUP_TRUST_PAY_ACCT
  is '受托支付账户信息表';
-- Add comments to the columns 
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.rcv_date
  is '登记日期';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.leg_per_cod
  is '法人代码';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.prv_cod
  is '区域代码';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.opn_dep
  is '开户机构';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.tal_dep
  is '核算机构';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.due_num
  is '借据编号';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.pay_acct
  is '受托支付账号';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.pay_name
  is '受托支付账户名称';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.pay_amt
  is '受托支付金额';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.create_time
  is '创建时间';
comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.etl_dt
  is '数据日期';
-- Grant/Revoke object privileges 
grant select on T_CMS_TC_SUP_TRUST_PAY_ACCT to IML;
grant select on T_CMS_TC_SUP_TRUST_PAY_ACCT to IOL;
