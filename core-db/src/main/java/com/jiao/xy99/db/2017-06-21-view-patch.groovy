package com.jiao.xy99.db
import com.jiao.xy99.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()


databaseChangeLog(logicalFilePath: "xy99520/core/db/2017-06-21-view-patch.groovy") {

//    changeSet(author: "shuai.xie", id: "xy99_init_table_cux_mdm_item_report_v") {
//        sqlFile(path: mhi.dataPath("hgxp/core/db/data/" + dbType + "/views/cux_mdm_item_report_v.sql"), encoding: "UTF-8")
//    }

}