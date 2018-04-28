package com.jiao.xy99.db

import com.jiao.xy99.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"2017-03-16-init-table-migration.groovy"){
    //用户、角色
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_USER") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_USER.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_USER_ROLE") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_USER_ROLE.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_USER_LOGIN_LOG") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_USER_LOGIN_LOG.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_ROLE_B") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_ROLE_B.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_ROLE_TL") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_ROLE_TL.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_ROLE_FUNCTION") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_ROLE_FUNCTION.sql"), encoding: "UTF-8")
    }
    //功能、资源
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_FUNCTION_B") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_FUNCTION_B.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_FUNCTION_TL") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_FUNCTION_TL.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_RESOURCE_B") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_RESOURCE_B.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_RESOURCE_TL") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_RESOURCE_TL.sql"), encoding: "UTF-8")
    }
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_FUNCTION_RESOURCE") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_FUNCTION_RESOURCE.sql"), encoding: "UTF-8")
    }
    //登录次数限制表
    changeSet(author: "shuai.xie", id: "xy99_init_table_SYS_ACCOUNT_RETRIEVE") {
        sqlFile(path: mhi.dataPath("com/jiao/xy99/db/data/" + dbType + "/tables/SYS_ACCOUNT_RETRIEVE.sql"), encoding: "UTF-8")
    }

}