package model.dao;
import controller.RegVerify;
import model.domain.Managers;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 访问管理员表的类
 */
public class MangersDao {
    public static long ID;
    public static Object[] obj=new Object[2];
    ConnectionClose cc=new ConnectionClose();
    Connection conn=null;   //表示数据库的连接对象
    PreparedStatement ps=null;
    ResultSet result=null;  //表示接受数据库的查询结果
    String sql;
    public void insertManagers(Managers man){
        sql="insert into managers(account,password,boss,problem,answer)values(?,?,?,?,?)";
        Object[] objects=new Object[]{
                man.getAccount(),man.getPassword(),man.getBoss(),man.getProblem(),man.getAnswer()
        };
        cc.insertDeleteUpdate(sql,objects);
    }
    //    验证密码
    public boolean findManagers(String sr,String sr1){
        Managers man=new Managers();
        Boolean bo=false;
        try {
            conn= cc.getConn();
            sql="select * from managers where account=? and password=? ";
            ps=conn.prepareStatement(sql);
            ps.setString(1,sr);
            ps.setString(2,sr1);
            result=ps.executeQuery();
            if(result.next()){
                man.setMid(result.getLong("Mid"));
                man.setAccount(result.getString("account"));
                man.setPassword(result.getString("password"));
                ID=man.getMid();
                obj[0]=man.getAccount();
                obj[1]=man.getPassword();
                bo=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return bo;
    }
    public List<Managers> ManagerInformation(String str){
        List<Managers> list=new ArrayList<Managers>();
        String isBoss="1";
        try {
            conn=cc.getConn();
            sql="select*from managers where account='"+str+"' and boss='"+isBoss+"' ";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                Managers managers=new Managers();
                managers.setMid(result.getLong("mid"));
                managers.setAccount(result.getString("account"));
                managers.setProblem(result.getString("problem"));
                managers.setAnswer(result.getString("answer"));
                list.add(managers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return list;
    }
    public void updateManager(Managers managers){
        sql="update managers set password=? where mid=?";
        Object[] objects=new Object[]{
                managers.getPassword(),managers.getMid()
        };
        cc.insertDeleteUpdate(sql,objects);
    }
}
