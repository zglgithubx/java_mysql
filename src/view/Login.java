package view;
import controller.PublicController;
import model.dao.StudentsDao;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * 登录界面
 */
public class Login{
    JFrame jf;
    JComboBox jcb;
    JTextField textname;
    JButton btn,btn1,btn2;
    JPasswordField textname1;
    StuRevise sr=new StuRevise();
    ManRegister man=new ManRegister();
    PublicController publicController=new PublicController();
    public static void main(String[] args) {
        Login login=new Login();
        login.initUI();
    }
    public void initUI(){
        jf=new JFrame();
        jf.setTitle("Login");
        jf.setSize(400,500);
        jf.setLayout(null);
        jf.setDefaultCloseOperation(3);
        //下拉菜单
        JLabel status=new JLabel("选择身份：");
        String str[]={"学生","管理员","Boss"};
        jcb=new JComboBox(str);
        status.setBounds(30,100,70,30);
        jcb.setBounds(100,100,100,30);
        jf.add(status);
        jf.add(jcb);
        //输入框
        JLabel logName=new JLabel("账号：");
        logName.setBounds(30,150,40,40);
        jf.add(logName);
        textname=new JTextField();
        textname.setPreferredSize(new Dimension(300,40));
        textname.setBounds(70,150,300,40);
        jf.add(textname);
        //输入框
        JLabel logName1=new JLabel("密码：");
        logName1.setBounds(30,210,40,40);
        jf.add(logName1);
        textname1=new JPasswordField();
        textname1.setPreferredSize(new Dimension(300,40));
        textname1.setBounds(70,210,300,40);
        jf.add(textname1);
        //按钮
        btn=new JButton("登录");
        btn.setBounds(150,270,100,40);
        btn.setBorderPainted(false);
        jf.add(btn);
        btn.addActionListener(act);

        btn1=new JButton("注册");
        btn1.setBounds(150,330,100,40);
        btn1.setBorderPainted(false);
        jf.add(btn1);
        btn1.addActionListener(act);
        btn2=new JButton("忘记密码");
        btn2.setBounds(150,390,100,40);
        btn2.setBorderPainted(false);
        jf.add(btn2);
        btn2.addActionListener(act);
        //窗口显示设置
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setVisible(true);
    }
//    按钮监听
    ActionListener act=new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
//          x为下拉菜单的索引值
            int x=jcb.getSelectedIndex();
            if(e.getSource()==btn){
                String str=textname.getText();
                String str1=new String(textname1.getPassword());
                if(publicController.status(x,str,str1)){
                    jf.dispose();
                    if(x==0){
                        sr.stuRevise(StudentsDao.obj);
                    }
                    else{
                        new MangerClient();
                    }
                }else{
                    publicController.alert("账号或密码有误，请重新输入");
                }
            }
            else if(e.getSource()==btn1){
                jf.dispose();
                if(x==0){
                    new Register();
                }
                else{
                    man.ManRegui(x);
                }
            }else if(e.getSource()==btn2){
                if(x==2){
                    jf.dispose();
                    new ForgetPassword();
                }else{
                    publicController.alert("Boss才能忘记密码");
                }
            }
        }
    };
}
