package model.service;
import controller.EncryptPassword;
import model.dao.StudentsDao;
import model.domain.Students;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Vector;
/**
 * 学生的业务逻辑层
 */
public class StuService {
    StudentsDao stuDao=new StudentsDao();
    Students stu=new Students();
    EncryptPassword encryptPassword=new EncryptPassword();
    public boolean verify(String str,String str1){
        return stuDao.findStudents(str,str1);
    }
    public void publicStu(String[] st) {
        stu.setStudent_id(new Long(st[0]));
        stu.setName(st[1]);
        if(st[2].equals("男")){
            stu.setSex(true);
        }else{
            stu.setSex(false);
        }
        stu.setAcademy(st[3]);
        stu.setSpecialty(st[4]);
        stu.setClss(st[5]);
        stu.setPhone_number(st[6]);
        stu.setDorm_id(st[7]);
        stu.setPassword(encryptPassword.encript(st[8],st[0]));
    }
    public void stulog(String[] st){
        publicStu(st);
        stu.setLogindate(new Timestamp(new Date().getTime()));
        stuDao.insertStudents(stu);
    }
    public void stuUpdate(String[] st) {
        publicStu(st);
        //设置id属性
        stu.setSid(StudentsDao.SID);
        //修改数据
        stuDao.updateStudents(stu);
    }
    public void stuUpdate(Object id, Vector st){
        stu.setStudent_id(new Long(st.get(0).toString()));
        stu.setName(st.get(1).toString());
        if(st.get(2).equals("男")){
            stu.setSex(true);
        }else{
            stu.setSex(false);
        }
        stu.setAcademy(st.get(3).toString());
        stu.setSpecialty(st.get(4).toString());
        stu.setClss(st.get(5).toString());
        stu.setPhone_number(st.get(6).toString());
        stu.setDorm_id(st.get(7).toString());
        stu.setLogindate(new Timestamp(new Date().getTime()));
        //获取数据据
        stu.setSid(new Long(id.toString()));
        //修改数据
        stuDao.updateStudents(stu);
    }
    public List<Students> selectAllName(String str){
        return stuDao.selectNameAll(str);
    }
    public List<Students> stuSelectAll(){
        return stuDao.selectStudentsAll();
    }
    public String backName(Long id){
        return stuDao.backName(id);
    }
    public Boolean isExist(String str,Long id){
        return stuDao.backName(str,id);
    }
    public Boolean isClassExist(String str,Long id){
        return stuDao.backClassName(str,id);
    }
    public void stuDelecte(Long id){
        stuDao.deleteStudentsById(id);
    }

    public List<Students> stuVagueSelect(String[] st,String str,int page){
        return stuDao.vagueSelect(st[0],st[1],st[2],str,page);
    }
    public int stuVagueSelectTotal(String[] st,String str){
        return stuDao.vagueSelectTotal(st[0],st[1],st[2],str);
    }
//    多表查询之查询学生的职位信息
    public List selectChar(Long id){
        return stuDao.isPosition(id);
    }
    public boolean isUnique(String str){
        return stuDao.account(str);
    }
}
