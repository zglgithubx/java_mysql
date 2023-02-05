package model.service;
import model.dao.MangersDao;
import model.domain.Managers;
import java.util.List;

/**
 * 管理员表的业务逻辑层
 */
public class ManService {
    MangersDao md=new MangersDao();
    public boolean verify(String str,String str1){
        return md.findManagers(str,str1);
    }
    public void manlog(String[] st){
        Managers man=new Managers();
        man.setAccount(st[0]);
        man.setPassword(st[1]);
        if(st[2].equals("1")){
            man.setBoss(true);
        }else{
            man.setBoss(false);
        }
        man.setProblem(st[3]);
        man.setAnswer(st[4]);
        md.insertManagers(man);
    }
    public List<Managers> backManagers(String str){
        return md.ManagerInformation(str);
    }
    public void updateManage(Long id,String str){
        Managers man=new Managers();
        man.setPassword(str);
        man.setMid(id);
        md.updateManager(man);
    }
}
