package view;
import controller.RegVerify;
import javax.swing.*;
import java.awt.*;

/**
 *公共的学生信息面板
 */
public class PublicStudent {
    JFrame jfr=new JFrame("");
    JButton btn;
    RegVerify rv;
    JTextField tn[];
    JLabel reg1[];
    public void publicStudent(){
        Font fon=new Font("细明体",Font.PLAIN,16);
        rv=new RegVerify();
        btn = new JButton("");
        tn=new JTextField[9];
        reg1=new JLabel[9];
        String name[] = {"学号:", "姓名:", "性别:", "院系:", "专业:", "班级:", "手机号:", "宿舍号:", "密码:"};
        jfr.setSize(600, 800);
        jfr.setLayout(null);
        jfr.setDefaultCloseOperation(3);
        for (int i = 0; i < 9; i++) {
            JLabel reg = new JLabel(name[i]);
            reg.setFont(fon);
            reg1[i]=new JLabel(name[i]+"有误");
            reg1[i].setForeground(Color.red);
            reg.setBounds(120, 105 + i * 60, 60, 40);
            reg1[i].setBounds(480,105+i*60,100,40);
            reg1[i].setVisible(false);
            jfr.add(reg);
            jfr.add(reg1[i]);
            tn[i]= new JTextField();
            tn[i].setFont(fon);
            tn[i].setPreferredSize(new Dimension(300, 40));
            tn[i].setBounds(180, 105 + i * 60, 300, 40);
            jfr.add(tn[i]);
        }
        btn.setBounds(250, 645, 100, 40);
        btn.setBorderPainted(false);
        jfr.add(btn);
        jfr.setLocationRelativeTo(null);
        jfr.setResizable(false);
        jfr.setVisible(true);
    }
}
