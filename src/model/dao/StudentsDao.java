package model.dao;
import controller.EncryptPassword;
import controller.RegVerify;
import model.domain.Classes;
import model.domain.Dorms;
import model.domain.Students;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 访问学生表的类
 */
public class StudentsDao {
    public static long SID;
    public static Object[] obj=new Object[9];
    ConnectionClose cc=new ConnectionClose();
    EncryptPassword encryptPassword=new EncryptPassword();
    PreparedStatement ps=null;
    ResultSet result=null;
    Connection conn=null;
    String sql;
    RegVerify regVerify=new RegVerify();
//    添加数据
    public void insertStudents(Students stu){
        sql="insert into students(student_id,name,sex,academy,specialty,clss,phone_number,dorm_id,password,logindate)values(?,?,?,?,?,?,?,?,?,?)";
        Object[] objects=new Object[]{
                stu.getStudent_id(),stu.getName(),stu.getSex(),stu.getAcademy(),stu.getSpecialty(),
                stu.getClss(), stu.getPhone_number(),stu.getDorm_id(),stu.getPassword(),stu.getLogindate()
        };
        cc.insertDeleteUpdate(sql,objects);
    }
    //    修改学生信息
    public void updateStudents(Students stu){
        sql="update students set student_id=?,name=?,sex=?,academy=?,specialty=?,clss=?,phone_number=?,dorm_id=?,logindate=? where sid=? ";//3.定义SQL容器，并装在我们的SQL语句
        Object[] objects=new Object[]{
                stu.getStudent_id(),stu.getName(),stu.getSex(),stu.getAcademy(),stu.getSpecialty(),
                stu.getClss(), stu.getPhone_number(),stu.getDorm_id(),stu.getLogindate(),stu.getSid()
        };
        cc.insertDeleteUpdate(sql,objects);
    }
    public void deleteStudentsById(Long id){
        String clss=null;
        String dorm_id=null;
        int classActual=0;
        int dormActual=0;
        int dormRemain_bed=0;
        try {
            conn=cc.getConn();
            conn.setAutoCommit(false);
//            查询被删除人员的班级名和宿舍名
            sql="select*from students where sid='"+id+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()) {
                Students stu = new Students();
                stu.setClss(result.getString("clss"));
                stu.setDorm_id(result.getString("dorm_id"));
                clss=stu.getClss();
                dorm_id=stu.getDorm_id();
            }
//          删除成员
            sql="delete from students where sid=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,id);
            ps.execute();
//          查询宿舍和班级实到人数
            sql="select*from dorms where dorm_id='"+dorm_id+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()) {
                Dorms dorms1=new Dorms();
                dorms1.setActual(result.getInt("actual"));
                dorms1.setRemaining_beds(result.getInt("remaining_beds"));
                dormActual=dorms1.getActual();
                dormRemain_bed=dorms1.getRemaining_beds();
            }

            sql="select*from classes where clss='"+clss+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()) {
                Classes classes=new Classes();
                classes.setActual(result.getInt("actual"));
                classActual=classes.getActual();
            }
//          修改宿舍表的实到人数
            sql="update dorms set actual=?,remaining_beds=? where dorm_id=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,dormActual-1);
            ps.setInt(2,dormRemain_bed+1);
            ps.setString(3,dorm_id);
            ps.execute();

//          修改班级表的实到人数
            sql="update classes set actual=? where clss=?";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,classActual-1);
            ps.setString(2,clss);
            ps.execute();

            sql="update dorms set domotry=? where domotry=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,0);
            ps.setLong(2,id);
            ps.execute();
            sql="update classes set monitor=? where monitor=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,0);
            ps.setLong(2,id);
            ps.execute();
            sql="update classes set tzs=? where tzs=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,0);
            ps.setLong(2,id);
            ps.execute();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            cc.close(ps,conn);
        }
    }
    /**
     *
     * @param str:为注册的账号
     * @return true:当前账号唯一
     *         false:不唯一
     */
    public boolean account(String str){
        Boolean bo=true;
        try {
            if(regVerify.st(str)) {
                conn = cc.getConn();
                sql = "select * from students where student_id=?";
                ps = conn.prepareStatement(sql);
                ps.setLong(1, new Long(str));
                result = ps.executeQuery();
                if (result.next()) {
                    bo = false;
                }
            }else{
                bo = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return bo;
    }
//    验证账号密码
    public boolean findStudents(String sr,String sr1){
        Boolean bo=false;
        Students stu=null;
        try {
            if(regVerify.st(sr)&&regVerify.pa(sr1)){
                conn= cc.getConn();
                //执行SQL语句，查询数据库
                String password=encryptPassword.encript(sr1,sr);
                sql="select * from students where student_id=? and password=? ";
                ps=conn.prepareStatement(sql);
                ps.setLong(1,new Long(sr));
                ps.setString(2,password);
                result=ps.executeQuery();
                if(result.next()){
                    stu=new Students();
                    bo=true;
                    stu.setSid(result.getLong("sid"));
                    stu.setStudent_id(result.getLong("student_id"));
                    stu.setName(result.getString("name"));
                    stu.setSex(result.getBoolean("sex"));
                    stu.setAcademy(result.getString("academy"));
                    stu.setSpecialty(result.getString("specialty"));
                    stu.setClss(result.getString("clss"));
                    stu.setPhone_number(result.getString("phone_number"));
                    stu.setDorm_id(result.getString("dorm_id"));
                    stu.setPassword(result.getString("password"));
                    SID=stu.getSid();
                    obj[0]=stu.getStudent_id();
                    obj[1]=stu.getName();
                    obj[2]=stu.getSex();
                    obj[3]=stu.getAcademy();
                    obj[4]=stu.getSpecialty();
                    obj[5]=stu.getClss();
                    obj[6]=stu.getPhone_number();
                    obj[7]=stu.getDorm_id();
                    obj[8]=stu.getPassword();
                }
            }
            else{
                bo=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return bo;
    }

//    根据主键 返回姓名
    public String backName(Long id){
        String st=null;
        try {
            conn=cc.getConn();
            sql="select*from students where sid='"+id+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                Students stu= new Students();
                stu.setName(result.getString("name"));
                st=stu.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cc.close(result,ps,conn);
        }
        return st;
    }
    public Boolean backClassName(String str,Long id){
        Boolean bo=false;
        try {
            conn=cc.getConn();
            sql="select*from students where sid='"+id+"' and clss='"+str+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
                bo=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cc.close(result,ps,conn);
        }
        return bo;
    }
    public Boolean backName(String str,Long id){
        Boolean bo=false;
        try {
            conn=cc.getConn();
            sql="select*from students where sid='"+id+"' and dorm_id='"+str+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            if(result.next()){
               bo=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cc.close(result,ps,conn);
        }
        return bo;
    }
//    根据班名/宿舍名返回所有人员的姓名和唯一id
    public List<Students> selectNameAll(String st){
        List<Students> NameId=new ArrayList<Students>();
        try {
            conn=cc.getConn();
            sql="select*from students where dorm_id='"+st+"' or clss='"+st+"'";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                Students stu=new Students();
                stu.setName(result.getString("name"));
                stu.setSid(result.getLong("sid"));
                NameId.add(stu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return NameId;
    }
//    返回多条查询记录
    public List<Students> selectStudentsAll(){
        List<Students> list=new ArrayList<Students>();
        try {
            conn=cc.getConn();
            sql="select*from students";
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                Students stu=new Students();
                stu.setSid(result.getLong("sid"));
                stu.setStudent_id(result.getLong("student_id"));
                stu.setName(result.getString("name"));
                stu.setSex(result.getBoolean("sex"));
                stu.setAcademy(result.getString("academy"));
                stu.setSpecialty(result.getString("specialty"));
                stu.setClss(result.getString("clss"));
                stu.setPhone_number(result.getString("phone_number"));
                stu.setDorm_id(result.getString("dorm_id"));
                stu.setLogindate(result.getTimestamp("logindate"));
                list.add(stu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return list;
    }
//        创建任意类的对象，可以执行任意方法
//        配置文件，反射
//        1.将需要创建的对象的全类名和需要执行的方法定义在配置文件中
//        2.在程序中加载读取配置文件
//        3.使用反射技术来加载类文件进内存
//        4.创建对象
//        5.执行方法
//        1.1创建properties对象
//        Properties properties=new Properties();
//        1.2加载配置文件，转换为一个集合
//        1.2加载配置文件，转换为一个集合
//        1.2.1获取class目录下的配置文件
//        ClassLoader classLoader=StudentsDao.class.getClassLoader();
//        InputStream is=classLoader.getResourceAsStream("pro.properties");
//        properties.load(is);
//        2.获取配置文件中定义的数据
//        String className= properties.getProperty("className");
//        String methodName=properties.getProperty("methodName");
//        3.加载该类进内存
//        Class cls=Class.forName(className);
//        Object obj=cls.newInstance();
//        Method method=cls.getMethod(methodName);
//        method.invoke(obj);
//        Class cls=Students.class;
//        StudentsDao studentsDao=new StudentsDao();
//        String sql="select*from students";
//        List list=studentsDao.publicSelect(cls,sql);
//        System.out.println(list.size());
    /**
     * @param cls 映射类
     * @param sql 数据库查询语句
     * @return 查询结果集合
     */
    public List publicSelect(Class cls,String sql){
        List list=new ArrayList();
        Field[] fields=cls.getDeclaredFields();
        System.out.println(fields.length);
        try {
            conn=cc.getConn();
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                System.out.println("1");
//                Object obj=cls.newInstance();
                Constructor constructor = cls.getConstructor(String.class);
                Object obj = constructor.newInstance();
                for(int i=0;i<fields.length;i++){
                    fields[i].set(obj,result.getObject(fields[i].getName()));
                    list.add(fields[i].get(obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return list;
    }

    public int vagueSelectTotal(String academy,String specialty,String clss,String name){
        int total=0;
        try {
            conn=cc.getConn();
            //       名字查询
            if(academy==null&&specialty==null&&clss==null&&name!=null){
                sql="select*from students where name like '%"+name+"%'";
            }
            //        学院，专业，班级查询
            if(academy!=null&&specialty!=null&&clss!=null&&name==null){
                sql="select*from students where academy='"+academy+"' and specialty='"+specialty+"' and clss='"+clss+"' ";
            }
            //        学院查询

            if(academy!=null&&specialty==null&&clss==null&&name==null){
                sql="select*from students where academy='"+academy+"' ";
            }
            //        学院，专业查询
            if(academy!=null&&specialty!=null&&clss==null&&name==null){
                sql="select*from students where academy='"+academy+"' and specialty='"+specialty+"' ";
            }
            //        学院+姓名查询
            if(academy!=null&&specialty==null&&clss==null&&name!=null){
                sql="select*from students where name like '%"+name+"%' and academy='"+academy+"' ";
            }
            //        学院，专业+姓名查询
            if(academy!=null&&specialty!=null&&clss==null&&name!=null){
                sql="select*from students where name like '%"+name+"%' and academy='"+academy+"' and specialty='"+specialty+"' ";
            }
            //        学院，专业，班级+姓名查询
            if(academy!=null&&specialty!=null&&clss!=null&&name!=null){
                sql="select*from students where name like '%"+name+"%' and academy='"+academy+"' and specialty='"+specialty+"' and clss='"+clss+"' ";
            }
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                total++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return total;
    }
    public List<Students> vagueSelect(String academy,String specialty,String clss,String name,int page){
        List<Students> Name=new ArrayList<Students>();
        try {
            conn=cc.getConn();
    //       名字查询
            if(academy==null&&specialty==null&&clss==null&&name!=null){
                sql="select*from students where name like '%"+name+"%' LIMIT 10 OFFSET "+page*10+"";
            }
    //        学院，专业，班级查询
            if(academy!=null&&specialty!=null&&clss!=null&&name==null){
                sql="select*from students where academy='"+academy+"' and specialty='"+specialty+"' and clss='"+clss+"' LIMIT 10 OFFSET "+page*10+"";
            }
    //        学院查询
            if(academy!=null&&specialty==null&&clss==null&&name==null){
                sql="select*from students where academy='"+academy+"' LIMIT 10 OFFSET "+page*10+"";
            }
    //        学院，专业查询
            if(academy!=null&&specialty!=null&&clss==null&&name==null){
                sql="select*from students where academy='"+academy+"' and specialty='"+specialty+"' LIMIT 10 OFFSET "+page*10+"";
            }
    //        学院+姓名查询
            if(academy!=null&&specialty==null&&clss==null&&name!=null){
                sql="select*from students where name like '%"+name+"%' and academy='"+academy+"' LIMIT 10 OFFSET "+page*10+"";
            }
    //        学院，专业+姓名查询
            if(academy!=null&&specialty!=null&&clss==null&&name!=null){
                sql="select*from students where name like '%"+name+"%' and academy='"+academy+"' and specialty='"+specialty+"' LIMIT 10 OFFSET "+page*10+"";
            }
    //        学院，专业，班级+姓名查询
            if(academy!=null&&specialty!=null&&clss!=null&&name!=null){
                sql="select*from students where name like '%"+name+"%' and academy='"+academy+"' and specialty='"+specialty+"' and clss='"+clss+"' LIMIT 10 OFFSET "+page*10+"";
            }
            ps=conn.prepareStatement(sql);
            result=ps.executeQuery();
            while(result.next()){
                Students stu=new Students();
                stu.setSid(result.getLong("sid"));
                stu.setStudent_id(result.getLong("student_id"));
                stu.setName(result.getString("name"));
                stu.setSex(result.getBoolean("sex"));
                stu.setAcademy(result.getString("academy"));
                stu.setSpecialty(result.getString("specialty"));
                stu.setClss(result.getString("clss"));
                stu.setPhone_number(result.getString("phone_number"));
                stu.setDorm_id(result.getString("dorm_id"));
                stu.setPassword(result.getString("password"));
                stu.setLogindate(result.getTimestamp("logindate"));
                Name.add(stu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return Name;
    }
    public List isPosition(long id){
        List list=new ArrayList();
        try {
            conn=cc.getConn();
            conn.setAutoCommit(false);
            sql="select s.sid=? sid,d.domotry domotry from students s,dorms d where sid=domotry";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,id);
            result=ps.executeQuery();
            while (result.next()){
                list.add("0");
            }
            sql="select s.sid=? sid,c.monitor monitor from students s,classes c where sid=monitor";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,id);
            result=ps.executeQuery();
            while (result.next()){
                list.add("1");
            }
            sql="select s.sid=? sid,c.tzs tzs from students s,classes c where sid=tzs";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,id);
            result=ps.executeQuery();
            while (result.next()){
                list.add("2");
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            cc.close(result,ps,conn);
        }
        return list;
    }
}
