package cn.lzt.tmall.util;

import java.sql.*;

/**
 * @author lzt
 * @date 2019/11/25 15:36
 */
public class DataUtils {

    public static void main(String args[]) {
        //准备分类测试数据：
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall_ssm?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&useSSL=false", "root", "root"); Statement s = c.createStatement();) {
            for (int i = 1; i <= 10; i++) {
                String sqlFormat = "insert into user values (null, '测试用户%d','123456')";
                String sql = String.format(sqlFormat, i);
                s.execute(sql);
            }

            System.out.println("已经成功创建10条分类测试数据");

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
