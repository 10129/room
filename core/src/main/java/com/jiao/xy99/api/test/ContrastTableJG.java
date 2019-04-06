package com.jiao.xy99.api.test;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class ContrastTableJG {
        //正式
        private static Logger log = LoggerFactory.getLogger(com.jiao.xy99.api.test.HistoryTableDatas.class);
        public static final String DBDRIVER1 = "oracle.jdbc.OracleDriver";
        public static final String DBURL1 = "jdbc:oracle:thin:@172.26.16.53:1521/orcl";
        public static final String DBUSER1 = "root";
        public static final String DBPASS1 = "root";
        //测试
        public static final String DBDRIVER2 = "oracle.jdbc.OracleDriver";
        public static final String DBURL2 = "jdbc:oracle:thin:@172.26.16.53:1521/orcl";
        public static final String DBUSER2 = "root";
        public static final String DBPASS2 = "root";
        /**
         * 查询SQL
         */
        public static final String SELECT_HANA_TABLE_02 = "SELECT SCHEMA_NAME,TABLE_NAME FROM TABLES WHERE SCHEMA_NAME= ?";

        public static void main(String[] args)throws Exception{
            contrastColumns();
        }
        /**
         * 数据库，表结构对比
         */
        public  static  void  contrastColumns(){
            Map<String,String> map1=selectAlltable(DBDRIVER1,DBURL1,DBUSER1,DBPASS1);
            Map<String,String> map2=selectAlltable(DBDRIVER2,DBURL2,DBUSER2,DBPASS2);
            for(Entry<String,String> entry :map1.entrySet()){
                String tableName =entry.getKey();
                if(StringUtils.isBlank(map2.get(tableName))){
                    System.out.println("1库有2库没有的表 tablename**"+tableName);
                }else {
                    //其他
                }
            }
        }
        /**
         * 查询表
         */
        public static Map<String,String> selectAlltable(String dbdriver,String dburl,String dbuser,String dbpass ){
            Map<String,String> map=new HashMap<>();
            Connection conn=null;
            PreparedStatement statement=null;
            ResultSet rs=null;
            try {
                conn=getConnection(dbdriver,dburl,dbuser,dbpass);
                statement=getStatement(conn,SELECT_HANA_TABLE_02);
                rs=statement.executeQuery();
                while (rs.next()){
                    map.put(rs.getString("TABLE_NAME"),rs.getString("SCHEMA_NAME"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
            }
            return map;
        }
        /**
         * 替换SQL参数
         */
        public static PreparedStatement getStatement(Connection conn,String sql,Object... params) throws SQLException {
            PreparedStatement statement=null;
            statement=conn.prepareStatement(sql);
            for (int i=0;i<params.length;i++){
                statement.setObject((i+1),params[i]);
            }
            return statement;
        }
        /**
         * 建立数据库连接
         */
        public static Connection getConnection(String dbdriver,String dburl,String dbuser,String dbpass ){
            Connection conn=null;
            try{
                Class.forName(dbdriver);
                conn= DriverManager.getConnection(dburl,dbuser,dbpass);
            }catch (Exception e){
                e.printStackTrace();
                log.debug("连接失败!");
                System.out.print("连接失败！");
            }
            return conn;
        }

    }

