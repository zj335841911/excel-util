-- Create table
create table T_CAS_TC_SUP_PRM_AST_PAY_TYP
(
  leg_per_cod         VARCHAR2(40 CHAR),
  orig_from           VARCHAR2(20 CHAR),
  cur_prm_pay_typ     VARCHAR2(4 CHAR),
  prm_desc            VARCHAR2(120 CHAR),
  cur_ast_pay_typ     VARCHAR2(6 CHAR),
  ast_desc            VARCHAR2(120 CHAR),
  prp_rcv_itr_flg     VARCHAR2(2 CHAR),
  prp_rcv_itr_cal_typ VARCHAR2(4 CHAR),
  prn_pay_way         VARCHAR2(2 CHAR),
  acc_typ             VARCHAR2(6 CHAR),
  amz_typ             VARCHAR2(4 CHAR),
  version             VARCHAR2(64 CHAR),
  etl_dt              DATE
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
comment on table T_CAS_TC_SUP_PRM_AST_PAY_TYP
  is '还款方式控制参数表';
-- Add comments to the columns 
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.leg_per_cod
  is '法人代码';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.orig_from
  is '交易发起渠道';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.cur_prm_pay_typ
  is '主还款方式';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.prm_desc
  is '主还款方式名称';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.cur_ast_pay_typ
  is '子还款方式';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.ast_desc
  is '子还款方式名称';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.prp_rcv_itr_flg
  is '预收利息标志';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.prp_rcv_itr_cal_typ
  is '预收利息计算方式';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.prn_pay_way
  is '放款金额支付方式';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.acc_typ
  is '预收利息存放的账户类型';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.amz_typ
  is '摊销方式';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.version
  is '版本号';
comment on column T_CAS_TC_SUP_PRM_AST_PAY_TYP.etl_dt
  is '数据日期';
-- Grant/Revoke object privileges 
grant select on T_CAS_TC_SUP_PRM_AST_PAY_TYP to IML;
grant select on T_CAS_TC_SUP_PRM_AST_PAY_TYP to IOL;
