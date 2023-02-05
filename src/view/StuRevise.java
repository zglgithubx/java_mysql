package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 学生修改信息面板
 */
public class StuRevise extends PublicStudent{
    public void stuRevise(Object[] obj){
        this.publicStudent();
        jfr.setTitle("新生信息");
        btn.setText("OK");
        for(int i=0;i<obj.length;i++){
            tn[i].setText(obj[i].toString());
            if(i==2){
                if(obj[i].equals("1")){
                    tn[i].setText("男");
                }else{
                    tn[i].setText("女");
                }
            }
        }
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==btn){
                    jfr.dispose();
                    new Login().initUI();
//                    Boolean bo[]={rv.st(tn[0].getText()),rv.na(tn[1].getText()),rv.se(tn[2].getText()),
//                            rv.ac(tn[3].getText()),rv.sp(tn[4].getText()),rv.cl(tn[5].getText()),rv.ph(tn[6].getText()),rv.dr(tn[7].getText()),rv.pa(tn[8].getText())
//                    };
//                    int x=0;
//                    int z=0;
//                    String y[]=new String[9];
//                    for(int i=0;i<bo.length;i++){
//                        if(bo[i]){
//                            x++;
//                            reg1[i].setVisible(false);
//                            y[i]=tn[i].getText();
//                        }else{
//                            reg1[i].setVisible(true);
//                            tn[i].setText(null);
//                        }
//                        if(x==9){
//                            jfr.dispose();
//                            try {
//                                new MangerController().stuRev(z,y);
//                            } catch (NoSuchAlgorithmException ex) {
//                                ex.printStackTrace();
//                            } catch (InvalidKeyException ex) {
//                                ex.printStackTrace();
//                            } catch (BadPaddingException ex) {
//                                ex.printStackTrace();
//                            } catch (NoSuchPaddingException ex) {
//                                ex.printStackTrace();
//                            } catch (IllegalBlockSizeException ex) {
//                                ex.printStackTrace();
//                            }
//                            new Login().initUI();
//                        }
//                    }
                }
            }
        });
    }
}
