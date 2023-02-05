package view;
import controller.PublicController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 新生注册
 */
public class Register extends PublicStudent{
    PublicController publicController=new PublicController();
    public Register() {
        this.publicStudent();
        jfr.setTitle("新生注册");
        btn.setText("完成注册");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn) {
                    Boolean bo[]=new Boolean[9];
                    Boolean bo1=publicController.isUnique(tn[0].getText());
                    bo[0]=rv.st(tn[0].getText());
                    bo[1]=rv.na(tn[1].getText());
                    bo[2]=rv.se(tn[2].getText());
                    bo[3]=rv.ac(tn[3].getText());
                    bo[4]=rv.sp(tn[4].getText());
                    bo[5]=rv.cl(tn[5].getText());
                    bo[6]=rv.ph(tn[6].getText());
                    bo[7]=rv.dr(tn[7].getText());
                    bo[8]=rv.pa(tn[8].getText());
                    int x=0;
                    int z=0;
                    String y[]=new String[9];
                    for(int i=0;i<bo.length;i++){
                        if(bo[i]){
                            x++;
                            reg1[i].setVisible(false);
                            y[i]=tn[i].getText();
                        }else{
                            reg1[i].setVisible(true);
                            tn[i].setText(null);
                        }
                        if(x==9){
                            if(bo1){
                                jfr.dispose();
                                new Login().initUI();
                                new PublicController().stureg(z,y);
                            }
                            else{
                                publicController.alert("学号已存在，请重新填写");
                                tn[0].setText(null);
                            }
                        }
                    }
                }
            }
        });
    }
}
