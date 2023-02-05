package model.dao;
import model.domain.Classes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ClassesDao {
    ConnectionClose cc=new ConnectionClose();
    public static long cid;
    public static int actual,oldActual;
    Connection conn=null;   //表示数据库的连接对象
    PreparedStatement ps=null;
    ResultSet result=null;  //表示接受数据库的查询结果
    String sql;
    public boolean findClassName(String st){
        Boolean bo=false;
        Classes classes;
        try {
            conn=cc.getConn();
            sql="select * from classes where clss='"+st+"' ";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                classes=new Classes();
                classes.setCid(result.getLong("cid"));
                classes.setActual(result.getInt("actual"));
                cid=classes.getCid();
                actual=classes.getActual();
            }else{
                bo=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return bo;
    }
//    根据班名返回id
    public Long findClass(String st){
        Long id=null;
        Classes classes;
        try {
            conn=cc.getConn();
            sql="select * from classes where clss='"+st+"' ";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                classes=new Classes();
                classes.setCid(result.getLong("cid"));
                classes.setActual(result.getInt("actual"));
                oldActual=classes.getActual();
                id=classes.getCid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return id;
    }
    public List<Classes> findClasses(String str){
        List<Classes> listDorms=new ArrayList<Classes>();
        try {
            conn= cc.getConn();
            //执行SQL语句，查询数据库
            sql="select * from classes where clss='"+str+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                Classes classes=new Classes();
                classes.setCid(result.getLong("cid"));
                classes.setActual(result.getInt("actual"));
                classes.setMonitor(result.getLong("monitor"));
                classes.setTzs(result.getLong("tzs"));
                listDorms.add(classes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return listDorms;
    }

    public void insertClasses(Classes classes){
        try {
            conn=cc.getConn();
            sql="insert into classes(clss,actual,monitor,tzs)values(?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,classes.getClss());
            ps.setInt(2,classes.getActual());
            ps.setLong(3,classes.getMonitor());
            ps.setLong(4,classes.getTzs());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(ps,conn);
        }
    }

    public void updateClasses(Classes classes){
        sql="update classes set actual=?,monitor=?,tzs=? where cid=? ";
        Object[] objects=new Object[]{
                classes.getActual(),classes.getMonitor(),classes.getTzs(),classes.getCid()
        };
        cc.insertDeleteUpdate(sql,objects);
    }
    public List<Classes> selectClassesAll(){
        List<Classes> list=new ArrayList<Classes>();
        try {
            conn=cc.getConn();
            sql="select*from classes";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                Classes classes=new Classes();
                classes.setCid(result.getLong("cid"));
                classes.setClss(result.getString("clss"));
                classes.setActual(result.getInt("actual"));
                classes.setMonitor(result.getLong("monitor"));
                classes.setTzs(result.getLong("tzs"));
                list.add(classes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return list;
    }
}
