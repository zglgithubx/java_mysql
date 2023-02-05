package model.dao;
import model.domain.Dorms;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DormsDao {
    ConnectionClose cc=new ConnectionClose();
    public static long did;
    public static int actual,remainBeds;
    Connection conn=null;   //表示数据库的连接对象
    PreparedStatement ps=null;
    ResultSet result=null;  //表示接受数据库的查询结果
    String sql;
//    查询宿舍名字的次数
    public boolean findDormName(String st){
        Dorms dorms;
        Boolean bo=false;
        try {
            conn= cc.getConn();
            //执行SQL语句，查询数据库
            sql="select * from dorms where dorm_id='"+st+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                dorms=new Dorms();
                dorms.setDid(result.getLong("did"));
                dorms.setActual(result.getInt("actual"));
                dorms.setRemaining_beds(result.getInt("remaining_beds"));
                did=dorms.getDid();
                actual=dorms.getActual();
                remainBeds=dorms.getRemaining_beds();
            }else{
                bo=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return bo;
    }
    public List<Dorms> findDorm(String st){
        List<Dorms> listDorms=new ArrayList<Dorms>();
        try {
            conn= cc.getConn();
            //执行SQL语句，查询数据库
            sql="select * from dorms where dorm_id='"+st+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                Dorms dorms=new Dorms();
                dorms.setDid(result.getLong("did"));
                dorms.setActual(result.getInt("actual"));
                dorms.setRemaining_beds(result.getInt("remaining_beds"));
                dorms.setDomotry(result.getLong("domotry"));
                listDorms.add(dorms);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return listDorms;
    }
    public void insertDorms(Dorms dorms){
        try {
            conn=cc.getConn();
            sql="insert into dorms(dorm_id,actual,remaining_beds,domotry)values(?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,dorms.getDorm_id());
            ps.setInt(2,dorms.getActual());
            ps.setInt(3,dorms.getRemaining_beds());
            ps.setLong(4,dorms.getDomotry());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(ps,conn);
        }
    }
    public void updateDorms(Dorms dorms){
        sql="update dorms set actual=?,remaining_beds=?,domotry=? where did=? ";
        Object[] objects=new Object[]{
                dorms.getActual(),dorms.getRemaining_beds(),dorms.getDomotry(),dorms.getDid()
        };
        cc.insertDeleteUpdate(sql,objects);
    }
    public List<Dorms> selectDormsAll(){
        List<Dorms> listDorms=new ArrayList<Dorms>();
        try {
            conn=cc.getConn();
            sql="select*from dorms";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                Dorms dorms=new Dorms();
                dorms.setDorm_id(result.getString("dorm_id"));
                dorms.setActual(result.getInt("actual"));
                dorms.setRemaining_beds(result.getInt("remaining_beds"));
                dorms.setDomotry(result.getLong("domotry"));
                dorms.setDid(result.getLong("did"));
                listDorms.add(dorms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return listDorms;
    }
}
