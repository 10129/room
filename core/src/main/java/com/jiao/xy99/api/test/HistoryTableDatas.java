package com.jiao.xy99.api.test;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class HistoryTableDatas {
    //正式
    private static Logger log = LoggerFactory.getLogger(HistoryTableDatas.class);
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
     * 更新序列表
     */
    public static int makeTableSeq(String tableName ,int seqNum){
        //序列
        String sequence ="CREATE SEQUENCE "+tableName+"_s Start with "+seqNum;
        //表
        Connection conn=null;
        Statement stat2=null;
        int res =0;
        try{
            conn =getConnection(DBDRIVER2,DBURL2,DBUSER2,DBPASS2);
            stat2=conn.createStatement();
            stat2.addBatch(sequence);
            //执行批量处理SQL
            res =stat2.executeBatch().length;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  res;
    }
    /**
     * 执行数据插入
     */
    public static int insertAll(String tableName , List<Map<String,String>> datas ,int num)throws SQLException{
        //影响行数
        int affectRowConut=-1;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            //获取连接
            connection=getConnection(DBDRIVER2,DBURL2,DBUSER2,DBPASS2);
            //获取第一行数据，得到列名
            Map<String,String> valueMap=datas.get(0);
            //获取数据库插入MAP的对应值
            Set<String> keySet=valueMap.keySet();
            Iterator<String> iterator=keySet.iterator();
            //要插入的SQL其实就是用key拼接起来
            StringBuilder columnSql=new StringBuilder();
            //要插入的字段，其实就是？
            StringBuilder unknownMarlSql =new StringBuilder();
            Object[] keys=new Object[valueMap.size()];
            int i=0;
            while (iterator.hasNext()){
                String key=iterator.next();
                keys[i]=key;
                columnSql.append(i==0?"":",");
                columnSql.append(key);

                unknownMarlSql.append(i==0?"":",");
                unknownMarlSql.append("?");
                i++;
            }
            //开始插入的sql语句
            StringBuilder sql=new StringBuilder();
            sql.append("INSERT INTO ");
            sql.append(tableName);
            sql.append(" (");
            sql.append(columnSql);
            sql.append(" ) VALUES(");
            sql.append(unknownMarlSql);
            sql.append(" )");
            //设置不自动提交，以便于在出现异常的时候数据库回滚
            connection.setAutoCommit(false);
            //预编译
            preparedStatement =connection.prepareStatement(sql.toString());
            System.out.println(sql.toString());
            int n2;
            for(int a=0;a<datas.size();a=a+num){
                n2=a+num;
                if(datas.size()<=n2){
                    n2=datas.size();
                }
                for(int j=a;j<n2;j++){
                    for(int k=0;k<keys.length;k++){
                        preparedStatement.setObject(k+1,datas.get(j).get(keys[k]));
                    }
                    preparedStatement.addBatch();
                }
                int[] arr=preparedStatement.executeBatch();
                connection.commit();
                affectRowConut=arr.length;
            }
        }catch (Exception e){
            if (connection !=null){
                connection.rollback();
            }
            log.debug("数据批量插入失败了。。");
            e.printStackTrace();
        }finally {
            if(preparedStatement!=null){
                preparedStatement.close();
            }
            if(connection !=null){
                connection.close();
            }
        }
        return affectRowConut;
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
