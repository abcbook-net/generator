package net.abcbook.framework.generator.utils;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;

/**
 * @author summer
 * @date 2018/6/13 上午10:26
 */
public class MysqlUtils {

    public static List<String> getTablesByPrefix(DataSourceConfig dataSourceConfig, String schema)
            throws ClassNotFoundException, SQLException {

        if(dataSourceConfig == null
                || StringUtils.isEmpty(schema)){
            throw new SQLException("dataSourceConfig is null or schema is null");
        }

        Class.forName(dataSourceConfig.getDriverName());
        //JDBC使用类似URL的数据源描述
        String urlStr = dataSourceConfig.getUrl();
        String userName = dataSourceConfig.getUsername();
        String password = dataSourceConfig.getPassword();

        //打开数据库链接
        Connection connection = DriverManager.getConnection(urlStr,userName,password);
        //创建执行声明
        Statement statement = connection.createStatement();

        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='"+schema+"'";
//        if(!StringUtils.isEmpty(prefix)){
//            sql += "";
//        }
        //executeUpdate可以返回数据库更新的行数
        //        int efactRow = sta.executeUpdate("UPDATE USER SET Permition = 'admin' WHERE username = 'Zing'");
        //executeQuery可以返回一个查询的结果集，这个集合的迭代器略有不同Iterator,没有hasNext方法，初始是，指针在数据前，必须调用next方法才能读取第一行数据
        // select table_name from information_schema.tables where table_schema='tale' and table_type='base table';
        ResultSet resultSet = statement.executeQuery(sql);

        Set<String> tableNames = new HashSet<>();
        while (resultSet.next()){
            tableNames.add(resultSet.getString("table_name"));
        }

        return new ArrayList<>(tableNames);
    }

    /**
     * @author summer
     * @date 2018/6/15 下午8:15
     * @param dataSourceConfig 数据库连接配置
     * @param tablePrefixList 表前缀集合
     * @return java.util.List<java.util.List<java.lang.String>>
     * @description 根据表前缀, 获取对应表前缀的表名集合
     */
    public static Map<String, List<String>> listTableByTablePrefix(DataSourceConfig dataSourceConfig, List<String> tablePrefixList)
            throws ClassNotFoundException, SQLException {

        if(dataSourceConfig == null
                || CollectionUtils.isEmpty(tablePrefixList)
                || StringUtils.isEmpty(dataSourceConfig.getUrl())){
            return null;
        }
        Class.forName(dataSourceConfig.getDriverName());
        //JDBC使用类似URL的数据源描述
        String urlStr = dataSourceConfig.getUrl();
        String userName = dataSourceConfig.getUsername();
        String password = dataSourceConfig.getPassword();

        //打开数据库链接
        Connection connection = DriverManager.getConnection(urlStr,userName,password);
        //创建执行声明
        Statement statement = connection.createStatement();

        String database = MysqlUtils.getDatabaseByUrl(dataSourceConfig.getUrl());
        if(StringUtils.isEmpty(database)){
            return null;
        }

        // 定义返回的数据库表名集合
        Map<String, List<String>> tableNameResultMap = new HashMap<>();
        // 循环获取表名
        for (String tablePrefix : tablePrefixList) {
            if(StringUtils.isEmpty(tablePrefix)){
                continue;
            }

            String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema= '"+ database +"' AND TABLE_NAME LIKE '"+ tablePrefix +"%'";
            // 执行查询
            ResultSet resultSet = statement.executeQuery(sql);

            // 封装查询得出的表名, 并设置到返回的集合中
            Set<String> tableNames = new HashSet<>();
            while (resultSet.next()){
                tableNames.add(resultSet.getString("table_name"));
            }
            // 去除 set 集合中可能为空的数据
            tableNames.remove(null);
            if(!CollectionUtils.isEmpty(tableNames)){
                tableNameResultMap.put(tablePrefix, new ArrayList<>(tableNames));
            }
        }

        return tableNameResultMap;

    }

    public static String getDatabaseByUrl(String url) {
        if(StringUtils.isEmpty(url)){
            return null;
        }
        Integer startIndex = url.lastIndexOf("/");
        Integer endIndex = url.indexOf("?") > 0 ? url.indexOf("?") : url.length();

        if(startIndex <= 0 || endIndex <= 0){
            return null;
        }

        return url.substring(startIndex + 1, endIndex);
    }
}
