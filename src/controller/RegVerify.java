package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证类
 */
public class RegVerify {
    PublicController publicController=new PublicController();
    private int x,y,z;
    public boolean st(String st){
        String st_reg="\\d{11}";
        return st.matches(st_reg);
    }
    public boolean na(String st){
        String tap_reg="\\s+";
        Pattern r=Pattern.compile(tap_reg);
        Matcher m=r.matcher(st);
        if(m.matches()||("").equals(st)){
            return false;
        }else {
            Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
            char c[] = st.toCharArray();
            for (int i = 0; i < c.length; i++) {
                Matcher matcher = pattern.matcher(String.valueOf(c[i]));
                if (!matcher.matches()) {
                    return false;
                }
            }
            return true;
        }
    }
    public boolean se(String st){
        if("男".equals(st)||"女".equals(st)){
            return true;
        }
        return false;
    }
    public boolean ac(String st){
        String ac_reg[]={"信息工程学院","动物科技学院"};
        for(int i=0;i<ac_reg.length;i++){
            if(ac_reg[i].equals(st)){
                x=i;
                return true;
            }
        }
        publicController.alert(st+"不存在");
        return false;
    }
    public boolean sp(String st){
        String ac_reg[][]={{"计算机科学与技术","物联网","大数据","人工智能"},
                {"动物药学","动物检疫","动物医学","动物科学"}};
        for(int i=0;i<ac_reg[x].length;i++){
            if(ac_reg[x][i].equals(st)){
                y=i;
                return true;
            }
        }
        publicController.alert(st+"不存在");
        return false;
    }
    public boolean cl(String st){
        String ac_reg[][][]={
                {{"计科181","计科182","计科183","计科184"},
                {"物联181","物联182","物联183","物联184"},
                {"数据181","数据182","数据183","数据184"},
                {"人工智能181","人工智能182","人工智能183","人工智能184"}},
        {{"动药181","动药182"},
                {"动检181","动检182"},
            {"动医181","动医182","动医183","动医184"},
            {"动科181","动科182"},{"动科183","动科184"}}
        };
        for(int i=0;i<ac_reg[x][y].length;i++){
            if(ac_reg[x][y][i].equals(st)){
                z=i;
                return true;
            }
        }
        publicController.alert(st+"不存在");
        return false;
    }
    public boolean ph(String st){
        Pattern p=null;
        Matcher m=null;
        boolean b=false;
        p=Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        m=p.matcher(st);
        b=m.matches();
        return b;
    }
    public boolean dr(String st){
        String ac_reg[]={"1号楼301","1号楼302","1号楼303","1号楼304","1号楼305",
                "1号楼201","1号楼202","1号楼203","1号楼204","1号楼205",
                "1号楼101","1号楼102","1号楼103","1号楼104","1号楼105",
                "2号楼301","2号楼302","2号楼303","2号楼304","2号楼305",
                "2号楼201","2号楼202","2号楼203","2号楼204","2号楼205",
                "2号楼101","2号楼102","2号楼103","2号楼104","2号楼105"
        };
        for(int i=0;i<ac_reg.length;i++){
            if(ac_reg[i].equals(st)){
                return true;
            }
        }
        return false;
    }
    public boolean pa(String st){
        String pa_reg="(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}";
        Pattern p=Pattern.compile(pa_reg);
        Matcher m=p.matcher(st);
        return m.matches();
    }
}
