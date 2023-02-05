package controller;
import model.service.ClassesService;
import model.service.DormsService;
import model.service.ManService;
import model.service.StuService;
import javax.swing.*;
/**
 * 公共控制器
 */
public class PublicController {
    public boolean isUnique(String str){
        return new StuService().isUnique(str);
    }
//  登录验证账号和密码
    public boolean status(int in,String str,String str1) {
        if(in==0){
            return new StuService().verify(str,str1);
        }
        else{
            return new ManService().verify(str,str1);
        }
    }
//  增添学生表的数据和修改班级和宿舍表的数据
    public void stureg(int in,String[] st) {
        if(in==0){
    //            向学生表添加数据
            new StuService().stulog(st);
    //            向宿舍表添加数据或修改数据
            DormsService dormsService=new DormsService();
            if(!dormsService.dormNameTime(st[7])){
                dormsService.dormsUpdate();
            }
    //            向班级表添加数据或修改数据
            ClassesService classesService=new ClassesService();
            if(!classesService.classNameTime(st[5])){
                classesService.classUpdate();
            }
        }
        else{
            new ManService().manlog(st);
        }
    }
//    警告弹出框
    public void alert(String str){
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(null, str, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    }
}
