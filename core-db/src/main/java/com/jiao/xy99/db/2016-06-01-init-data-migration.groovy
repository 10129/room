package com.jiao.xy99.db

import com.jiao.xy99.db.excel.ExcelDataLoader
import com.jiao.xy99.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()

databaseChangeLog(logicalFilePath:"xy99520/core/db/2018-04-28-init-data-migration.groovy"){

    //Milestone , excel data, runAlways=true
        changeSet(author: "shuai.xie", id: "20180428-init-data-xlsx", runAlways:"true"){
        customChange(class:ExcelDataLoader.class.name){
            param(name:"filePath",value:MigrationHelper.getInstance().dataPath("com/jiao/xy99/db/data/2018-04-28-init-data.xlsx"))
        }
    }
}
