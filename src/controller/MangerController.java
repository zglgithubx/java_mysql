package controller;
import model.domain.Managers;
import model.domain.Students;
import model.service.ClassesService;
import model.service.DormsService;
import model.service.ManService;
import model.service.StuService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Vector;

/**
 * 管理员控制器
 */
public class MangerController {
    StuService stuService=new StuService();
    ManService manService=new ManService();
//    分页查询
    public List<Students> controllSelect(String st[],String str,int page){
        return stuService.stuVagueSelect(st,str,page);
    }
//    返回结果的数量
    public int cotrollSelectTotal(String st[],String str){
        return stuService.stuVagueSelectTotal(st,str);
    }
//    返回管理员表的数据集合
    public List<Managers> backManager(String str){
        return manService.backManagers(str);
    }
//    更新管理员表的信息
    public void updateManagers(Long id,String str){
        manService.updateManage(id,str);
    }
//  根据班名或宿舍名返回所有成员的姓名和主键
    public List<Students> FindName(String st){
        return stuService.selectAllName(st);
    }
//  删除学生表的数据
    public void manDelet(int index,Object id){
        switch (index){
            case 2:
                new StuService().stuDelecte(new Long(id.toString()));
                break;
        }
    }
//  学生个人信息修改
    public void stuRev(int in,String[] st) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        if(in==0){
            new StuService().stuUpdate(st);
        }
    }
//    管理员修改信息
    public void manRev(int index, Object id, Vector vec){
        switch (index){
            case 0:
                new ClassesService().setclassChar(new Long(id.toString()),vec);
                break;
            case 1:
                new DormsService().domotryUpdate(new Long(id.toString()),vec);
                break;
            case 2:
                new StuService().stuUpdate(id,vec);
                break;
        }
    }
    public List selectIsChar(Long id){
        return stuService.selectChar(id);
    }
}
