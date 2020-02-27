-- Create table
create table T_CMS_TC_SUP_LOAN_INFO
(
  rcv_date               VARCHAR2(160 CHAR),
  leg_per_cod            VARCHAR2(400 CHAR),
  dep_cod                VARCHAR2(220 CHAR),
  trn_dep                VARCHAR2(220 CHAR),
  opn_dep                VARCHAR2(220 CHAR),
  orig_from              VARCHAR2(200 CHAR),
  prod_cod               VARCHAR2(200 CHAR),
  crd_cent               VARCHAR2(20 CHAR),
  cert_type              VARCHAR2(120 CHAR),
  cert_num               VARCHAR2(800 CHAR),
  cus_no                 VARCHAR2(600 CHAR),
  con_no                 VARCHAR2(600 CHAR),
  tel_no                 VARCHAR2(600 CHAR),
  due_num                VARCHAR2(600 CHAR),
  due_num_un             VARCHAR2(600 CHAR),
  due_type               VARCHAR2(20 CHAR),
  brw_name               VARCHAR2(4000 CHAR),
  amt                    NUMBER(17,2),
  beg_date               VARCHAR2(160 CHAR),
  end_date               VARCHAR2(160 CHAR),
  beg_itr_date           VARCHAR2(160 CHAR),
  pre_pay_itr_date       VARCHAR2(160 CHAR),
  cls_flg                VARCHAR2(20 CHAR),
  curr_cod               VARCHAR2(60 CHAR),
  prm_cls                VARCHAR2(40 CHAR),
  ast_cls                VARCHAR2(60 CHAR),
  bus_cod                VARCHAR2(200 CHAR),
  prm_pay_typ            VARCHAR2(40 CHAR),
  ast_pay_typ            VARCHAR2(40 CHAR),
  cur_prm_pay_typ        VARCHAR2(40 CHAR),
  cur_ast_pay_typ        VARCHAR2(40 CHAR),
  fix_rate_flag          VARCHAR2(20 CHAR),
  itr_rate_way           VARCHAR2(20 CHAR),
  nor_itr_rate           NUMBER(8,6),
  del_itr_rate           NUMBER(8,6),
  cpd_itr_rate           NUMBER(8,6),
  rel_itr_rate           NUMBER(8,6),
  cus_pay_plan_type      VARCHAR2(20 CHAR),
  caspan                 VARCHAR2(20 CHAR),
  cal_days               NUMBER(3),
  pay_date               VARCHAR2(80 CHAR),
  dist_days              NUMBER(6),
  itr_cal_rule           VARCHAR2(20 CHAR),
  after_caspan           VARCHAR2(20 CHAR),
  after_pay_date         VARCHAR2(80 CHAR),
  disc_flag              VARCHAR2(20 CHAR),
  itr_fre_flg            VARCHAR2(20 CHAR),
  itr_fre_cyl            NUMBER(8),
  free_proc_type         VARCHAR2(40 CHAR),
  ceas_disc_flag         VARCHAR2(20 CHAR),
  end_disc_flag          VARCHAR2(20 CHAR),
  hld_flg                VARCHAR2(40 CHAR),
  hld_prn_way            VARCHAR2(40 CHAR),
  hld_itr_way            VARCHAR2(40 CHAR),
  gra_perd_flg           VARCHAR2(40 CHAR),
  gra_prd_typ            VARCHAR2(40 CHAR),
  gra_prn_days           NUMBER(5),
  gra_itr_days           NUMBER(5),
  gra_prd_prn_way        VARCHAR2(40 CHAR),
  gra_prd_itr_way        VARCHAR2(40 CHAR),
  cal_itr_flag           VARCHAR2(260 CHAR),
  acr_itr_flg            VARCHAR2(200 CHAR),
  acr_itr_typ            VARCHAR2(40 CHAR),
  ceas_imp_flag          VARCHAR2(20 CHAR),
  itr_settle_type        VARCHAR2(20 CHAR),
  sett_pns_type          VARCHAR2(200 CHAR),
  ipr_pvs_base_rule      VARCHAR2(20 CHAR),
  itr_chg_flg            VARCHAR2(40 CHAR),
  pay_order              VARCHAR2(160 CHAR),
  bat_flg                VARCHAR2(20 CHAR),
  bat_type               VARCHAR2(20 CHAR),
  stud_perd              VARCHAR2(160 CHAR),
  clear_flg              VARCHAR2(20 CHAR),
  trus_to_pay_flg        VARCHAR2(20 CHAR),
  fir_prov_flg           VARCHAR2(20 CHAR),
  stop_pay_flg           VARCHAR2(20 CHAR),
  by_new_old_flg         VARCHAR2(20 CHAR),
  ast_due_num            VARCHAR2(600 CHAR),
  fee_flag               VARCHAR2(20 CHAR),
  oft_prn_itr_typ        VARCHAR2(20 CHAR),
  deal_flg               VARCHAR2(20 CHAR),
  end_term_num_days_betn NUMBER(8),
  etl_dt                 DATE
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
comment on table T_CMS_TC_SUP_LOAN_INFO
  is '临时主协议表';
-- Add comments to the columns 
comment on column T_CMS_TC_SUP_LOAN_INFO.rcv_date
  is '出账日期';
comment on column T_CMS_TC_SUP_LOAN_INFO.leg_per_cod
  is '法人代码';
comment on column T_CMS_TC_SUP_LOAN_INFO.dep_cod
  is '部门代码';
comment on column T_CMS_TC_SUP_LOAN_INFO.trn_dep
  is '放款机构';
comment on column T_CMS_TC_SUP_LOAN_INFO.opn_dep
  is '开户机构';
comment on column T_CMS_TC_SUP_LOAN_INFO.orig_from
  is '交易发起渠道';
comment on column T_CMS_TC_SUP_LOAN_INFO.prod_cod
  is '产品代码';
comment on column T_CMS_TC_SUP_LOAN_INFO.crd_cent
  is '是否放贷中心发放';
comment on column T_CMS_TC_SUP_LOAN_INFO.cert_type
  is '证件类型（同主档信息中证件类型）';
comment on column T_CMS_TC_SUP_LOAN_INFO.cert_num
  is '证件号码（同主档信息中证件号码）';
comment on column T_CMS_TC_SUP_LOAN_INFO.cus_no
  is '客户编号';
comment on column T_CMS_TC_SUP_LOAN_INFO.con_no
  is '合同编号';
comment on column T_CMS_TC_SUP_LOAN_INFO.tel_no
  is '通知书编号';
comment on column T_CMS_TC_SUP_LOAN_INFO.due_num
  is '借据编号';
comment on column T_CMS_TC_SUP_LOAN_INFO.due_num_un
  is '关联借据编号';
comment on column T_CMS_TC_SUP_LOAN_INFO.due_type
  is '借据类型';
comment on column T_CMS_TC_SUP_LOAN_INFO.brw_name
  is '借款人名称';
comment on column T_CMS_TC_SUP_LOAN_INFO.amt
  is '放款金额';
comment on column T_CMS_TC_SUP_LOAN_INFO.beg_date
  is '发放日期';
comment on column T_CMS_TC_SUP_LOAN_INFO.end_date
  is '到期日期';
comment on column T_CMS_TC_SUP_LOAN_INFO.beg_itr_date
  is '起息日期';
comment on column T_CMS_TC_SUP_LOAN_INFO.pre_pay_itr_date
  is '预收利息的止期';
comment on column T_CMS_TC_SUP_LOAN_INFO.cls_flg
  is '五级分类标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.curr_cod
  is '币    种';
comment on column T_CMS_TC_SUP_LOAN_INFO.prm_cls
  is '主类别';
comment on column T_CMS_TC_SUP_LOAN_INFO.ast_cls
  is '子类别';
comment on column T_CMS_TC_SUP_LOAN_INFO.bus_cod
  is '业务别';
comment on column T_CMS_TC_SUP_LOAN_INFO.prm_pay_typ
  is '主还款方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.ast_pay_typ
  is '子还款方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.cur_prm_pay_typ
  is '当前主还款方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.cur_ast_pay_typ
  is '当前子还款方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.fix_rate_flag
  is '固定利率标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.itr_rate_way
  is '日利率依据方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.nor_itr_rate
  is '正常利率';
comment on column T_CMS_TC_SUP_LOAN_INFO.del_itr_rate
  is '罚息利率';
comment on column T_CMS_TC_SUP_LOAN_INFO.cpd_itr_rate
  is '复利利率';
comment on column T_CMS_TC_SUP_LOAN_INFO.rel_itr_rate
  is '实际利率';
comment on column T_CMS_TC_SUP_LOAN_INFO.cus_pay_plan_type
  is '客户指定还息计划标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.caspan
  is '结息周期-到期前';
comment on column T_CMS_TC_SUP_LOAN_INFO.cal_days
  is '自定义结息天数';
comment on column T_CMS_TC_SUP_LOAN_INFO.pay_date
  is '指定结息日-到期前';
comment on column T_CMS_TC_SUP_LOAN_INFO.dist_days
  is '首次结息日隔天数';
comment on column T_CMS_TC_SUP_LOAN_INFO.itr_cal_rule
  is '下次结息日计算规则';
comment on column T_CMS_TC_SUP_LOAN_INFO.after_caspan
  is '结息周期 - 整笔到期后';
comment on column T_CMS_TC_SUP_LOAN_INFO.after_pay_date
  is '指定结息日 -整笔到期后';
comment on column T_CMS_TC_SUP_LOAN_INFO.disc_flag
  is '贴息标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.itr_fre_flg
  is '免息标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.itr_fre_cyl
  is '免息周期';
comment on column T_CMS_TC_SUP_LOAN_INFO.free_proc_type
  is '免息处理方式-提前归还当前期';
comment on column T_CMS_TC_SUP_LOAN_INFO.ceas_disc_flag
  is '继续贴息标志-停息后';
comment on column T_CMS_TC_SUP_LOAN_INFO.end_disc_flag
  is '继续贴息标志 -终止停息后';
comment on column T_CMS_TC_SUP_LOAN_INFO.hld_flg
  is '节假日标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.hld_prn_way
  is '节假日本金处理方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.hld_itr_way
  is '节假日利息处理方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.gra_perd_flg
  is '是否使用宽限期';
comment on column T_CMS_TC_SUP_LOAN_INFO.gra_prd_typ
  is '宽限期类型';
comment on column T_CMS_TC_SUP_LOAN_INFO.gra_prn_days
  is '本金宽限期天数';
comment on column T_CMS_TC_SUP_LOAN_INFO.gra_itr_days
  is '利息宽限期天数';
comment on column T_CMS_TC_SUP_LOAN_INFO.gra_prd_prn_way
  is '宽限期本金处理方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.gra_prd_itr_way
  is '宽限期利息处理方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.cal_itr_flag
  is '是否累计利息标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.acr_itr_flg
  is '计提利息标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.acr_itr_typ
  is '计提利息方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.ceas_imp_flag
  is '计提利息 -停息后';
comment on column T_CMS_TC_SUP_LOAN_INFO.itr_settle_type
  is '未结计正常利息计算标准';
comment on column T_CMS_TC_SUP_LOAN_INFO.sett_pns_type
  is '结计利息方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.ipr_pvs_base_rule
  is '减值基数计算规则';
comment on column T_CMS_TC_SUP_LOAN_INFO.itr_chg_flg
  is '利息转换标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.pay_order
  is '还款顺序';
comment on column T_CMS_TC_SUP_LOAN_INFO.bat_flg
  is '批量扣款标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.bat_type
  is '批量扣款方式';
comment on column T_CMS_TC_SUP_LOAN_INFO.stud_perd
  is '助学贷款宽限日期';
comment on column T_CMS_TC_SUP_LOAN_INFO.clear_flg
  is '到期前是否允许结清规则';
comment on column T_CMS_TC_SUP_LOAN_INFO.trus_to_pay_flg
  is '受托支付标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.fir_prov_flg
  is '首次结息日是否夸月标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.stop_pay_flg
  is '止付标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.by_new_old_flg
  is '借新还旧标志';
comment on column T_CMS_TC_SUP_LOAN_INFO.ast_due_num
  is '关联借据编号';
comment on column T_CMS_TC_SUP_LOAN_INFO.fee_flag
  is '费用收取标识(0:不收取,1:收取)';
comment on column T_CMS_TC_SUP_LOAN_INFO.oft_prn_itr_typ
  is '核销本金累计利息使用利率类型(0=正常利率;1=罚息利率)';
comment on column T_CMS_TC_SUP_LOAN_INFO.deal_flg
  is '处理状态';
comment on column T_CMS_TC_SUP_LOAN_INFO.end_term_num_days_betn
  is '尾期是否同上期合并的间隔天数';
comment on column T_CMS_TC_SUP_LOAN_INFO.etl_dt
  is '数据日期';
-- Grant/Revoke object privileges 
grant select on T_CMS_TC_SUP_LOAN_INFO to IML;
grant select on T_CMS_TC_SUP_LOAN_INFO to IOL;
