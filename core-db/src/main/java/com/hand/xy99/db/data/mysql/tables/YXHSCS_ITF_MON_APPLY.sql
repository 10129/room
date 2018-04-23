Create Table YXHSCS_ITF_MON_APPLY
(
 APPLY_PRI_ID bigint Not Null auto_increment primary key,
 ACCOUNTING_STATUS Varchar(200),
 ACCOUNTING_DATE DateTime,
 ACCOUNTING_REMARK Varchar(255),
 UNIQUE_CODE Varchar(200),
 APPLY_NUM Varchar(200),
 APPLY_AMOUNT Decimal(20,6),
 CURRENCY Varchar(200),
 APPLY_DATE DateTime,
 APPLY_PERIOD Varchar(200),
 RCPT_TYPE Varchar(200),
 RCPT_NUM Varchar(200),
 RCPT_PARTY Varchar(200),
 RCPT_COM Varchar(200),
 RCPT_INTERCOM Varchar(200),
 BATCH_NUM Varchar(200),
 PROGRAM_ID BigInt,
 REQUEST_ID BigInt,
 OBJECT_VERSION_NUMBER BigInt Not Null Default 1,
 CREATION_DATE  datetime Not Null Default CURRENT_TIMESTAMP,
 CREATED_BY BigInt Not Null Default -1,
 LAST_UPDATED_BY BigInt Not Null Default -1,
 LAST_UPDATE_DATE datetime Not Null Default CURRENT_TIMESTAMP,
 LAST_UPDATE_LOGIN BigInt Default -1,
 ATTRIBUTE_CATEGORY VARCHAR(30),
 ATTRIBUTE1 VARCHAR(150),
 ATTRIBUTE2 VARCHAR(150),
 ATTRIBUTE3 VARCHAR(150),
 ATTRIBUTE4 VARCHAR(150),
 ATTRIBUTE5 VARCHAR(150),
 ATTRIBUTE6 VARCHAR(150),
 ATTRIBUTE7 VARCHAR(150),
 ATTRIBUTE8 VARCHAR(150),
 ATTRIBUTE9 VARCHAR(150),
 ATTRIBUTE10 VARCHAR(150),
 ATTRIBUTE11 VARCHAR(150),
 ATTRIBUTE12 VARCHAR(150),
 ATTRIBUTE13 VARCHAR(150),
 ATTRIBUTE14 VARCHAR(150),
 ATTRIBUTE15 VARCHAR(150)
);
ALTER TABLE YXHSCS_ITF_MON_APPLY COMMENT '贷后系统月供核销接口目标表';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `APPLY_PRI_ID` bigint Not Null auto_increment Comment '表ID，主键，供其他表做外键';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `ACCOUNTING_STATUS` Varchar(200) Comment '过账状态';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `ACCOUNTING_DATE` DateTime Comment '过账日期';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `ACCOUNTING_REMARK` Varchar(255) Comment '过账标志';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `UNIQUE_CODE` Varchar(200) Comment '唯一标识';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `APPLY_NUM` Varchar(200) Comment '申请号';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `APPLY_AMOUNT` Decimal(20,6) Comment '核销/撤销核销金额';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `CURRENCY` Varchar(200) Comment '币种';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `APPLY_DATE` DateTime Comment '核销/撤销核销日期';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `APPLY_PERIOD` Varchar(200) Comment '核销/撤销核销还款期数';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `RCPT_TYPE` Varchar(200) Comment '收款类型';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `RCPT_NUM` Varchar(200) Comment '收款编号';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `RCPT_PARTY` Varchar(200) Comment '收款方';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `RCPT_COM` Varchar(200) Comment '收款方公司段';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `RCPT_INTERCOM` Varchar(200) Comment '收款方关联方段';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `BATCH_NUM` Varchar(200) Comment '批次号';
ALTER TABLE YXHSCS_ITF_MON_APPLY MODIFY `OBJECT_VERSION_NUMBER` BigInt Not Null Default 1 Comment '行版本号，用来处理锁';

ALTER TABLE `YXHSCS_ITF_MON_APPLY` ADD UNIQUE (`APPLY_PRI_ID`);
ALTER TABLE `YXHSCS_ITF_MON_APPLY` ADD INDEX YXHSCS_ITF_MON_APPLY.YXHSCS_ITF_MON_APPLY_N1(`APPLY_NUM`,`APPLY_PERIOD`,`RCPT_NUM`);