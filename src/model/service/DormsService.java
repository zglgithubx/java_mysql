package model.service;
import model.dao.DormsDao;
import model.domain.Dorms;
import java.util.List;
import java.util.Vector;
public class DormsService {
//判断出现的次数,0次为真，多次为假
    DormsDao dormsDao=new DormsDao();
    Dorms dorms=new Dorms();
    public Boolean dormNameTime(String st){
        return dormsDao.findDormName(st);
    }
    public List<Dorms> dormsName(String st){
        return dormsDao.findDorm(st);
    }
    public void oldDormUpdate(int i,int t,Long did,Long domo){
        dormUpdate(i-1,t+1,did,domo);
    }
    public void newDormUpdate(int i,int t,Long did,Long domo){
        dormUpdate(i+1,t-1,did,domo);
    }
    public void dormUpdate(int m,int n,Long id,Long domotry){
        dorms.setActual(m);
        dorms.setRemaining_beds(n);
        dorms.setDomotry(domotry);
        dorms.setDid(id);
        dormsDao.updateDorms(dorms);
    }
//    修改实到人数数据
    public void dormsUpdate(){
        dorms.setActual(DormsDao.actual+1);
        dorms.setRemaining_beds(DormsDao.remainBeds-1);
        dorms.setDid(DormsDao.did);
        dormsDao.updateDorms(dorms);
    }
//    添加宿舍职位
    public void domotryUpdate(Long id, Vector v){
        dorms.setDid(id);
        dorms.setDomotry(new Long(v.get(0).toString()));
        dorms.setActual((Integer)v.get(1));
        dorms.setRemaining_beds((Integer)v.get(2));
        dormsDao.updateDorms(dorms);
    }

    public List<Dorms> dormSelectAll(){
        return dormsDao.selectDormsAll();
    }
}
