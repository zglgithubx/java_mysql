package view;
import controller.MangerController;
import controller.RegVerify;
import model.domain.Managers;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
public class ForgetPassword {
    JButton sumbit;
    JTextField answer;
    List<Managers> information;
    JLabel[] jLabels=new JLabel[13];
    JPasswordField newPassword,newPassword1;
    Font fon=new Font("细明体",Font.PLAIN,16);

    RegVerify regVerify=new RegVerify();
    JFrame jFrame=new JFrame("找回密码");
    MangerController mangerController=new MangerController();
    ForgetPassword(){

        JOptionPane JOptonPane = new JOptionPane();
        JOptonPane.setSize(300,300);
        JOptonPane.setFont(fon);
        String inputValue = JOptonPane.showInputDialog("请输入需要找回的Boss账号");
        information=mangerController.backManager(inputValue);
        if(regVerify.pa(inputValue)&&information.size()>0){
            findPassword(inputValue);
        }else{
            int res=JOptionPane.showConfirmDialog(null, "找不到该账号", "是否继续", JOptionPane.YES_NO_OPTION);
            if(res==JOptionPane.YES_OPTION){
                 new Login().initUI();
            }
        }
    }
    public void findPassword(String str){
        jFrame.setSize(500,600);
        jFrame.setLayout(null);
//       显示账号
        jLabels[0]=new JLabel("账号：");
        jLabels[0].setBounds(110,50,80,50);
        System.out.println(information.get(0).getAccount());
        jLabels[1]=new JLabel(str);
        jLabels[1].setBounds(190,50,200,50);
//      问题
        jLabels[2]=new JLabel("问题：");
        jLabels[2].setBounds(110,140,80,50);
        jLabels[3]=new JLabel(information.get(0).getProblem());
        jLabels[3].setBounds(190,140,200,50);
//      答案
        jLabels[4]=new JLabel("答案：");
        jLabels[4].setBounds(110,230,80,50);
        answer=new JTextField();
        answer.setPreferredSize(new Dimension(200,50));
        answer.setBounds(190,230,200,50);
        answer.addActionListener(Listener);

        jLabels[7]=new JLabel("答案正确");
        jLabels[7].setForeground(Color.green);
        jLabels[7].setBounds(400,230,90,50);
        jLabels[8]=new JLabel("答案错误");
        jLabels[8].setForeground(Color.red);
        jLabels[8].setBounds(400,230,90,50);

//      新密码
        jLabels[5]=new JLabel("新密码：");
        jLabels[5].setBounds(110,320,80,50);
        newPassword=new JPasswordField();
        newPassword.setPreferredSize(new Dimension(200,50));
        newPassword.setBounds(190,320,200,50);
        newPassword.addMouseListener(inputListener);

        jLabels[9]=new JLabel("格式正确");
        jLabels[9].setForeground(Color.green);
        jLabels[9].setBounds(400,320,80,50);
        jLabels[10]=new JLabel("格式错误");
        jLabels[10].setForeground(Color.red);
        jLabels[10].setBounds(400,320,80,50);
        jLabels[6]=new JLabel( "验证密码:");
        jLabels[6].setBounds(110,410,80,50);
        newPassword1=new JPasswordField();
        newPassword1.setPreferredSize(new Dimension(200,50));
        newPassword1.setBounds(190,410,200,50);
        newPassword1.addMouseListener(inputListener);
        newPassword1.addActionListener(Listener);
        jLabels[11]=new JLabel("密码一致");
        jLabels[11].setForeground(Color.green);
        jLabels[11].setBounds(400,410,80,50);
        jLabels[12]=new JLabel("密码不一致");
        jLabels[12].setForeground(Color.red);
        jLabels[12].setBounds(400,410,80,50);
        for(int i=5;i<jLabels.length;i++){
            jLabels[i].setVisible(false);
        }
        newPassword.setVisible(false);
        newPassword1.setVisible(false);
        jFrame.add(answer);
        jFrame.add(newPassword);
        jFrame.add(newPassword1);
        for(int i=0;i<jLabels.length;i++){
            jLabels[i].setFont(fon);
            jFrame.add(jLabels[i]);
        }

        sumbit=new JButton("提交");
        sumbit.setBounds(210,500,80,50);
        sumbit.addActionListener(Listener);
        sumbit.setFont(fon);
        sumbit.setVisible(false);
        jFrame.add(sumbit);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
    MouseListener inputListener=new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource()==newPassword){
                newPassword1.setEditable(true);
            }
           if(e.getSource()==newPassword1){
                if(regVerify.pa(new String(newPassword.getPassword()))){
                    jLabels[9].setVisible(true);
                    jLabels[10].setVisible(false);
                }else{
                    jLabels[9].setVisible(false);
                    jLabels[10].setVisible(true);
                    newPassword.setText("");
                    newPassword1.setEditable(false);
                }
           }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    };
    ActionListener Listener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==answer){
                 if(answer.getText().equals(information.get(0).getAnswer())){
                    jLabels[5].setVisible(true);
                    jLabels[6].setVisible(true);
                    jLabels[7].setVisible(true);
                    jLabels[8].setVisible(false);
                    newPassword.setVisible(true);
                    newPassword1.setVisible(true);
                }else{
                    jLabels[7].setVisible(false);
                    jLabels[8].setVisible(true);
                    answer.setText("");
                }
            }else if(e.getSource()==newPassword1){
                if(new String(newPassword.getPassword()).equals(new String(newPassword1.getPassword()))){
                    jLabels[11].setVisible(true);
                    jLabels[12].setVisible(false);
                    sumbit.setVisible(true);
                }else{
                    jLabels[11].setVisible(false);
                    jLabels[12].setVisible(true);
                    newPassword1.setText("");
                    sumbit.setVisible(false);
                }
            }else if(e.getSource()==sumbit){
                mangerController.updateManagers(information.get(0).getMid(),new String(newPassword.getPassword()));
                jFrame.dispose();
                new Login().initUI();
            }
        }
    };
}
