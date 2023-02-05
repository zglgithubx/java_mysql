package view;
import controller.PublicController;
import controller.RegVerify;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 管理员注册页面
 */
public class ManRegister {
    JFrame jfr;
    JButton Mbtn;
    JComboBox jcb;
    JTextField tname,answer;
    JPasswordField tpassword;
    RegVerify rg=new RegVerify();
    Font fon=new Font("细明体",Font.PLAIN,16);
   public void ManRegui(int in){
       if(in==1){
           jfr=new JFrame("管理员注册");
       }
       if(in==2){
           jfr=new JFrame("Boss注册");
       }
       jfr.setSize(500,600);
       jfr.setLayout(null);
       jfr.setDefaultCloseOperation(3);
        //输入框
       JLabel Name=new JLabel("账号：");
       Name.setFont(fon);
       Name.setBounds(60,115,80,50);
       jfr.add(Name);
       tname=new JTextField();
       tname.setFont(fon);
       tname.setPreferredSize(new Dimension(300,50));
       tname.setBounds(140,115,300,50);
       jfr.add(tname);
       //输入框
       JLabel Name1=new JLabel("密码：");
       Name1.setFont(fon);
       Name1.setBounds(60,195,80,50);
       jfr.add(Name1);
       tpassword=new JPasswordField();
       tpassword.setFont(fon);
       tpassword.setPreferredSize(new Dimension(300,50));
       tpassword.setBounds(140,195,300,50);
       jfr.add(tpassword);
       if(in==2){
           JLabel Mb=new JLabel("密保：");
           String str[]={"你的真实姓名?","设置PIN"};
           jcb=new JComboBox(str);
           Mb.setFont(fon);
           jcb.setFont(fon);
           Mb.setBounds(60,275,80,50);
           jcb.setBounds(140,275,200,50);
           JLabel Da=new JLabel("答案：");
           Da.setBounds(60,355,80,50);
           answer=new JTextField();
           answer.setFont(fon);
           answer.setPreferredSize(new Dimension(300,50));
           answer.setBounds(140,355,300,50);
           jfr.add(answer);
           jfr.add(Da);
           jfr.add(Mb);
           jfr.add(jcb);
       }
       //按钮
       Mbtn = new JButton("完成注册");
       Mbtn.setBounds(200, 435, 100, 50);
       Mbtn.setBorderPainted(false);
       Mbtn.setFont(fon);
       jfr.add(Mbtn);
       Mbtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(e.getSource()==Mbtn){
                   String m[]=new String[5];
                   if(in==1){
                       Boolean bo[]=new Boolean[2];
                       bo[0]=rg.pa(tname.getText());
                       bo[1]=rg.pa(new String(tpassword.getPassword()));
                       if(bo[0]&&bo[1]){
                           jfr.dispose();
                           m[0]=tname.getText();
                           m[1]=new String(tpassword.getPassword());
                           m[2]="0";
                           m[3]=null;
                           m[4]=null;
                           new Login().initUI();
                           new PublicController().stureg(in,m);
                       }else{
                           tname.setText(null);
                           tpassword.setText(null);
                       }
                   }
                    if(in==2){
                       Boolean bo[]=new Boolean[3];
                       bo[0]=rg.pa(tname.getText());
                       bo[1]=rg.pa(new String(tpassword.getPassword()));
                       bo[2]=rg.na(answer.getText());
                       if(bo[0]&&bo[1]&&bo[2]){
                           m[0]=tname.getText();
                           m[1]=new String(tpassword.getPassword());
                           m[2]="1";
                           m[3]=(String)jcb.getSelectedItem();
                           m[4]=answer.getText();
                           jfr.dispose();
                           new Login().initUI();
                           new PublicController().stureg(in,m);
                       }else{
                           tname.setText(null);
                           tpassword.setText(null);
                           answer.setText(null);
                       }
                   }
               }
           }
       });
       jfr.setLocationRelativeTo(null);
       jfr.setResizable(false);
       jfr.setVisible(true);
    }
}
