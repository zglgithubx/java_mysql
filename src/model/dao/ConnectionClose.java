package model.dao;
import java.sql.*;
public class ConnectionClose {
    private static final String DBDRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DBURL="jdbc:mysql://localhost:3306/bjpowrenode?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
    private static final String DBUSER="root";
    private static final String DBPASS="54188";
    static{
        try {
            Class.forName(DBDRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConn(){
        Connection conn=null;
        try {
            conn= DriverManager.getConnection(DBURL,DBUSER,DBPASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public void close(PreparedStatement ps,Connection conn){
        close(null,ps,conn);
    }
    public static void close(ResultSet result, PreparedStatement ps, Connection conn){
        if(result!=null){
            try {
               result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//   数据库增删改操作封装
    public void insertDeleteUpdate(String sql,Object[] objects){
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn=getConn();
            ps=conn.prepareStatement(sql);
            for(int i=0;i<objects.length;i++){
                ps.setObject(i+1,objects[i]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps,conn);
        }
    }
}
