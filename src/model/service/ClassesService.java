package model.service;
import model.dao.ClassesDao;
import model.domain.Classes;
import java.util.List;
import java.util.Vector;
public class ClassesService {
    Classes classes=new Classes();
    ClassesDao classesDao=new ClassesDao();
    public Boolean classNameTime(String st){
        return classesDao.findClassName(st);
    }

    public List<Classes> classesName(String st){
        return classesDao.findClasses(st);
    }
    public void newClassUpdate(int actual,Long monitor,Long tzs,Long cid){
        classUpdate(actual+1,monitor,tzs,cid);
    }
    public void oldClassUpdate(int actual,Long monitor,Long tzs,Long cid){
        classUpdate(actual-1,monitor,tzs,cid);
    }
    public void classUpdate(int actual,Long monitor,Long tzs,Long cid){
        classes.setActual(actual);
        classes.setMonitor(monitor);
        classes.setTzs(tzs);
        classes.setCid(cid);
        classesDao.updateClasses(classes);
    }
    public void classUpdate(){
        classes.setActual(ClassesDao.actual+1);
        classes.setCid(ClassesDao.cid);
        classesDao.updateClasses(classes);
    }
    public List<Classes> classesSelectAll(){
        return classesDao.selectClassesAll();
    }
//    添加班级职位
    public void setclassChar(Long id, Vector v){
        classes.setCid(id);
        classes.setActual((Integer)v.get(0));
        if((Integer)v.get(1)==2){
            classes.setMonitor(new Long(v.get(2).toString()));
            classes.setTzs(new Long(v.get(4).toString()));
        }else if((Integer)v.get(1)==3){
            classes.setMonitor(new Long(v.get(3).toString()));
            classes.setTzs(new Long(v.get(2).toString()));
        }
        classesDao.updateClasses(classes);
    }
}
