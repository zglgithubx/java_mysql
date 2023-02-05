package view;
import controller.*;
import model.domain.Students;
import model.service.ClassesService;
import model.service.DormsService;
import model.service.StuService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
public class MangerClient
{
    String strFind;
    Object[][] Date;
    JLabel[] jLabel;
    JComboBox[] jComboBox;
    String[] strJlabel,acdemyString,title;
    String[][] jcomboxString;
    String[][][] acdemySpeciaty;
    JTextField find,pageJtextfield;
    //    查询数据的总数
    String[] strJcombox=new String[3];
    //  解决班级名和宿舍名修改的数据同步
    Object oldClass,oldDorm;
    int selectedIndex,acadmyX,specialtY,x,y,total,totalPage;
    JTable student,dormtable,classtable,resulTable;
    JScrollPane jscroll,dormjscroll,classjscroll,resultJscroll, mangerJscroll;
    DefaultTableModel studentModel,dormmodel,classmodel,resultModel;
    JButton but1,but,nextJbutton,lastJbutton,findJbutton;
    DefaultTableCellRenderer r=new DefaultTableCellRenderer();
    JFrame frame=new JFrame();
    JTabbedPane tabbedPane=new JTabbedPane();
    Font fon=new Font("细明体",Font.PLAIN,16);
    RegVerify reg=new RegVerify();
    StuService stuser=new StuService();
    DormsService dormsService=new DormsService();
    ClassesService classesService=new ClassesService();
    MangerController mangerController=new MangerController();
    public  MangerClient(){
        frame.setTitle("管理员");
        frame.setSize(900,900);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setBorder(new EmptyBorder(5,5,5,5));
//        班级管理
        classtable=new JTable();
        classjscroll=new JScrollPane(classtable);
        classtable.addMouseListener(clickTable);
        classtable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        classjscroll.setBorder(new EmptyBorder(5,5,100,5));
//        宿舍管理
        dormtable=new JTable();
        dormjscroll=new JScrollPane(dormtable);
        dormtable.addMouseListener(clickTable);
        dormtable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        dormjscroll.setBorder(new EmptyBorder(5,5,100,5));
//        学生管理
        student=new JTable();
        jscroll=new JScrollPane(student);
        studentsManger();
        student.addMouseListener(clickTable);
        student.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jscroll.setBorder(new EmptyBorder(5,5,100,5));
        but=new JButton("删除");
        but.setBounds(325,750,100,40);
        but.addActionListener(lastNextFindListener);
        but1=new JButton("保存修改");
        but1.setBounds(475,750,100,40);
        but1.addActionListener(lastNextFindListener);
        jscroll.add(but);
        jscroll.add(but1);
//        查询信息
        selectManger();
//      修改管理员个人信息
        mangerJscroll=new JScrollPane();
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                selectedIndex=tabbedPane.getSelectedIndex();
                if(selectedIndex==0){
                    classesManger();
                }else if(selectedIndex==1){
                    dormsManger();
                }else if(selectedIndex==3){
                    selectStart();
                }
            }
        });
        tabbedPane.addTab("班级管理",null,classjscroll,"查看班级");
        tabbedPane.addTab("宿舍管理",null,dormjscroll,"查看宿舍");
        tabbedPane.addTab("学生管理",null,jscroll,"查看学生");
        tabbedPane.addTab("查询信息",null,resultJscroll,"查询");
        tabbedPane.setFont(fon);
        frame.add(tabbedPane);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
//    查询功能
    public void selectManger(){
        resulTable=new JTable();
        resultJscroll=new JScrollPane(resulTable);
        jLabel=new JLabel[3];
        find=new JTextField();
        jComboBox=new JComboBox[3];
        resulTable.setRowHeight(30);
        resulTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resulTable.setFont(fon);
        resultJscroll.setBorder(new EmptyBorder(160,0,50,0));
        strJlabel=new String[]{"学院","专业","班级"};
        acdemyString=new String[]{"信息工程学院","动物科技学院"};
        jcomboxString=new String[][]{
            {"计算机科学与技术","物联网","大数据","人工智能"},
            {"动物药学","动物检疫","动物医学","动物科学"}
        };
        acdemySpeciaty=new String[][][]{
            {{"计科181","计科182","计科183","计科184"},
            {"物联181","物联182","物联183","物联184"},
            {"数据181","数据182","数据183","数据184"},
            {"人工智能181","人工智能182","人工智能183","人工智能184"}},
            {{"动药181","动药182"},
            {"动检181","动检182"},
            {"动医181","动医182","动医183","动医184"},
            {"动科181","动科182","动科183","动科184"}}
        };
        for(int i=0;i<3;i++){
            jLabel[i]=new JLabel(strJlabel[i]+":");
            jComboBox[i]=new JComboBox();
            jComboBox[i].addItem("请选择");
            jComboBox[i].setFont(fon);
            jComboBox[i].setAutoscrolls(false);
            jLabel[i].setBounds(72+i*273,40,50,30);
            jComboBox[i].setBounds(122+i*273,40,150,30);
            jLabel[i].setFont(fon);
            resultJscroll.add(jLabel[i]);
            resultJscroll.add(jComboBox[i]);
        }
        jComboBox[0].addItemListener(jcomboxItemlistener);
        jComboBox[1].addItemListener(jcomboxItemlistener);
        jComboBox[2].addItemListener(jcomboxItemlistener);
        for(int i=0;i<acdemyString.length;i++){
            jComboBox[0].addItem(acdemyString[i]);
        }
        for(int i=0;i<jcomboxString.length;i++){
            for(int j=0;j<jcomboxString[i].length;j++){
                jComboBox[1].addItem(jcomboxString[i][j]);
            }
        }
        for(int i=0;i<acdemySpeciaty.length;i++){
            for(int j=0;j<acdemySpeciaty[i].length;j++){
                for(int k=0;k<acdemySpeciaty[i][j].length;k++){
                    jComboBox[2].addItem(acdemySpeciaty[i][j][k]);
                }
            }
        }
        JLabel findTitle=new JLabel("姓名：");
        findTitle.setBounds(30,90,50,30);
        find.setPreferredSize(new Dimension(100,30));
        find.setBounds(80,90,150,30);
        resultJscroll.add(findTitle);
        resultJscroll.add(find);
        findJbutton=new JButton("搜索");
        findJbutton.setBounds(250,90,80,30);
        findJbutton.addActionListener(lastNextFindListener);
//        页码
        pageJtextfield=new JTextField();
        pageJtextfield.setPreferredSize(new Dimension(30,30));
        pageJtextfield.setText("0");
        pageJtextfield.setBounds(420,770,30,30);
        pageJtextfield.setEditable(false);
        JLabel page=new JLabel("页");
        page.setBounds(450,770,20,30);
//        上一页
        lastJbutton=new JButton("上一页");
        nextJbutton=new JButton("下一页");
        lastJbutton.setVisible(false);
        nextJbutton.setVisible(false);

        lastJbutton.addActionListener(lastNextFindListener);
        lastJbutton.setBounds(320,770,100,30);
        lastJbutton.setContentAreaFilled(false);
        lastJbutton.setBorder(null);//除去边框
        lastJbutton.setFocusPainted(false);//除去焦点的框

        nextJbutton.addActionListener(lastNextFindListener);
        nextJbutton.setBounds(470,770,100,30);
        nextJbutton.setContentAreaFilled(false);
        nextJbutton.setBorder(null);//除去边框
        nextJbutton.setFocusPainted(false);//除去焦点的框
        JButton jButtons[]={findJbutton,lastJbutton,nextJbutton};
        JLabel jLabels[]={findTitle,page};
        JTextField jTextFields[]={find,pageJtextfield};
        for(int i=0;i<jButtons.length;i++){
            jButtons[i].setFont(fon);
            resultJscroll.add(jButtons[i]);
        }
        for(int i=0;i<jLabels.length;i++){
            jLabels[i].setFont(fon);
            resultJscroll.add(jLabels[i]);
        }
        for(int i=0;i<jTextFields.length;i++){
            jTextFields[i].setFont(fon);
            resultJscroll.add(jTextFields[i]);
        }
    }
//    查询界面的初始化
    public void selectStart(){
        find.setText(null);
        jComboBox[0].setSelectedIndex(0);
        jComboBox[1].setSelectedIndex(0);
        jComboBox[2].setSelectedIndex(0);
        resultModel=(DefaultTableModel) resulTable.getModel();
        resultModel.setRowCount(0);
        title= new String[]{"学号", "姓名", "性别", "学院", "专业", "班级", "手机号", "宿舍号", "注册时间","职位"};
        resultModel.setColumnIdentifiers(title);
        pageJtextfield.setText("0");
        nextJbutton.setVisible(false);
        lastJbutton.setVisible(false);
    }
//    按钮监听
    ActionListener lastNextFindListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==findJbutton){
                boolean boJcombox0=("请选择").equals(jComboBox[0].getSelectedItem());
                boolean boJcombox1=("请选择").equals(jComboBox[1].getSelectedItem());
                boolean boJcombox2=("请选择").equals(jComboBox[2].getSelectedItem());
                boolean booleanFind=reg.na(find.getText());
                if(!boJcombox0){
                    strJcombox[0]=jComboBox[0].getSelectedItem().toString();
                }else{
                    strJcombox[0]=null;
                }
                if(!boJcombox1){
                    strJcombox[0]=acdemyString[acadmyX];
                    strJcombox[1]=jComboBox[1].getSelectedItem().toString();
                }else{
                    strJcombox[1]=null;
                }
                if(!boJcombox2){
                    strJcombox[0]=acdemyString[acadmyX];
                    strJcombox[1]=jcomboxString[acadmyX][specialtY];
                    strJcombox[2]=jComboBox[2].getSelectedItem().toString();
                }else{
                    strJcombox[2]=null;
                }
                if(booleanFind){
                    strFind=find.getText();
                }else{
                    find.setText(null);
                    strFind=null;
                }
                if(!boJcombox0||!boJcombox1||!boJcombox2||booleanFind){
                    total=mangerController.cotrollSelectTotal(strJcombox,strFind);
                    if(total==0){
                        totalPage=0;
                        pageJtextfield.setText("0");
                        nextJbutton.setVisible(false);
                        lastJbutton.setVisible(false);
                    }
                    else if(total>0&&total<=10){
                        totalPage=1;
                    }
                    else if(total>10) {
                        totalPage=total/10+1;
                    }
                    if(totalPage==1){
                        pageJtextfield.setText("1");
                        nextJbutton.setVisible(false);
                    }else if(totalPage>1){
                        pageJtextfield.setText("1");
                        nextJbutton.setVisible(true);
                    }
                    else{
                        pageJtextfield.setText("0");
                        nextJbutton.setVisible(false);
                        lastJbutton.setVisible(false);
                    }
                    selecetRomance(strJcombox,strFind,0);
                }else{
                    selectStart();
                    new PublicController().alert("查询内容格式有误");
                }
            }else if(e.getSource()==nextJbutton){
                int page=Integer.parseInt(pageJtextfield.getText());
                int pagetoal=totalPage;
                if(page<pagetoal-1){
                    selecetRomance(strJcombox,strFind,page);
                    pageJtextfield.setText(String.valueOf(page+1));
                    lastJbutton.setVisible(true);
                }else if(page==pagetoal-1){
                    selecetRomance(strJcombox,strFind,page);
                    pageJtextfield.setText(String.valueOf(page+1));
                    nextJbutton.setVisible(false);
                }
            }else if(e.getSource()==lastJbutton){
                int page=Integer.parseInt(pageJtextfield.getText());
                if(page>2){
                    nextJbutton.setVisible(true);
                    pageJtextfield.setText(String.valueOf(page-1));
                    selecetRomance(strJcombox,strFind,page-2);
                }else if(page==2){
                    lastJbutton.setVisible(false);
                    pageJtextfield.setText(String.valueOf(page-1));
                    selecetRomance(strJcombox,strFind,page-2);
                }
            }else if(e.getSource()==but){
                if(student.getSelectedRows().length>0) {
                    do_button_actionPerformed(e);
                }
            }else if(e.getSource()==but1){
                if(student.isEditing()){
                    student.getCellEditor().stopCellEditing();
                    amend();
                }
                do_button1_actionPerformed(e);
            }
        }
    };
//    下拉菜单监听
    ItemListener jcomboxItemlistener=new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getSource()==jComboBox[0]){
                if(jComboBox[0].getSelectedIndex()!=0){
                    selectJcombox(jComboBox[0].getSelectedIndex());
                }else{
                    jComboBox[1].removeAllItems();
                    jComboBox[1].addItem("请选择");
                    for(int i=0;i<jcomboxString.length;i++){
                        for(int j=0;j<jcomboxString[i].length;j++){
                            jComboBox[1].addItem(jcomboxString[i][j]);
                        }
                    }
                }
            }else if(e.getSource()==jComboBox[1]){
                if(jComboBox[1].getSelectedIndex()!=0){
                    for(int i=0;i<jcomboxString.length;i++){
                        for(int j=0;j<jcomboxString[i].length;j++){
                            if(jComboBox[1].getSelectedItem()==jcomboxString[i][j]){
                                acadmyX=i;
                                specialtY=j;
                            }
                        }
                    }
                    selectClass(acadmyX,specialtY);
                }else{
                    if(jComboBox[0].getSelectedIndex()!=0){
                        x=jComboBox[0].getSelectedIndex()-1;
                        selectClass(x);
                    }else{
                        jComboBox[2].removeAllItems();
                        jComboBox[2].addItem("请选择");
                        for(int i=0;i<acdemySpeciaty.length;i++){
                            for(int j=0;j<acdemySpeciaty[i].length;j++){
                                for(int k=0;k<acdemySpeciaty[i][j].length;k++){
                                    jComboBox[2].addItem(acdemySpeciaty[i][j][k]);
                                }
                            }
                        }
                    }
                }
            }else if(e.getSource()==jComboBox[2]){
                for(int i=0;i<acdemySpeciaty.length;i++){
                    for(int j=0;j<acdemySpeciaty[i].length;j++){
                        for(int k=0;k<acdemySpeciaty[i][j].length;k++){
                            if(jComboBox[2].getSelectedItem()==acdemySpeciaty[i][j][k]){
                                acadmyX=i;
                                specialtY=j;
                            }
                        }
                    }
                }
            }
        }
    };

    public void selectJcombox(int in){
        jComboBox[1].removeAllItems();
        jComboBox[1].addItem("请选择");
        for(int j=0;j<jcomboxString[in-1].length;j++){
            jComboBox[1].addItem(jcomboxString[in-1][j]);
        }
    }
    public void selectClass(int m,int n){
        jComboBox[2].removeAllItems();
        jComboBox[2].addItem("请选择");
        for(int j=0;j<acdemySpeciaty[m][n].length;j++){
            jComboBox[2].addItem(acdemySpeciaty[m][n][j]);
        }
    }
    public void selectClass(int m){
        jComboBox[2].removeAllItems();
        jComboBox[2].addItem("请选择");
        for(int j=0;j<acdemySpeciaty[m].length;j++){
           for(int k=0;k<acdemySpeciaty[m][j].length;k++){
               jComboBox[2].addItem(acdemySpeciaty[m][j][k]);
           }
        }
    }
    public void selecetRomance (String[] st,String str,int in){
        title= new String[]{"学号", "姓名", "性别", "学院", "专业", "班级", "手机号", "宿舍号", "注册时间","职位"};
        String[] studentChar=new String[]{"宿舍长","班长","团支书"};
        x=mangerController.controllSelect(st,str,in).size();
        y=title.length;
        Date=new Object[x][y];
        r.setHorizontalAlignment(JLabel.CENTER);
        resulTable.setDefaultRenderer(Object.class,r);
        resultModel=(DefaultTableModel) resulTable.getModel();
        resultModel.setRowCount(0);
        if(x>0){
            for(int i=0;i<x;i++){
                Date[i][0]=mangerController.controllSelect(st,str,in).get(i).getStudent_id();
                Date[i][1]=mangerController.controllSelect(st,str,in).get(i).getName();
                if(mangerController.controllSelect(st,str,in).get(i).getSex()){
                    Date[i][2]="男";
                }else{
                    Date[i][2]="女";
                }
                Date[i][3]=mangerController.controllSelect(st,str,in).get(i).getAcademy();
                Date[i][4]=mangerController.controllSelect(st,str,in).get(i).getSpecialty();
                Date[i][5]=mangerController.controllSelect(st,str,in).get(i).getClss();
                Date[i][6]=mangerController.controllSelect(st,str,in).get(i).getPhone_number();
                Date[i][7]=mangerController.controllSelect(st,str,in).get(i).getDorm_id();
                Date[i][8]=mangerController.controllSelect(st,str,in).get(i).getLogindate();
                Long id=mangerController.controllSelect(st,str,in).get(i).getStudent_id();
                int size=mangerController.selectIsChar(id).size();
                switch (size){
                    case 0:
                        Date[i][9]="无";
                        break;
                    case 1:
                        Date[i][9]=studentChar[new Integer(mangerController.selectIsChar(id).get(0).toString())];
                        break;
                    case 2:
                        Date[i][9]=studentChar[new Integer(mangerController.selectIsChar(id).get(0).toString())]+","+studentChar[new Integer(mangerController.selectIsChar(id).get(1).toString())];
                        break;
                    case 3:
                        Date[i][9]=studentChar[new Integer(mangerController.selectIsChar(id).get(0).toString())]+","+studentChar[new Integer(mangerController.selectIsChar(id).get(1).toString())]+","+studentChar[new Integer(mangerController.selectIsChar(id).get(2).toString())];
                        break;
                }
                resultModel.addRow(Date[i]);
            }
            resulTable.setModel(resultModel);
        }
    }
//班级管理
    public void classesManger(){
        title= new String[]{"班级", "实到人数", "班长", "团支书"};
        x=classesService.classesSelectAll().size();
        y=title.length;
        Date=new Object[x][y];
        classtable.setFont(fon);
        r.setHorizontalAlignment(JLabel.CENTER);
        classtable.setDefaultRenderer(Object.class,r);
        classmodel=(DefaultTableModel) classtable.getModel();
        classmodel=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int column){
                if(column==0||column==1){
                    return false;
                }
                return true;
            }
        };
        classmodel.setRowCount(0);
        classmodel.setColumnIdentifiers(title);
        //在渲染班级表
        if(x>0){
            for(int i=0;i<x;i++){
                Date[i][0]=classesService.classesSelectAll().get(i).getClss();
                Date[i][1]=classesService.classesSelectAll().get(i).getActual();
                long monitor=classesService.classesSelectAll().get(i).getMonitor();
                long tzs=classesService.classesSelectAll().get(i).getTzs();
                if(monitor==0||stuser.backName(monitor)==null||!stuser.isClassExist(Date[i][0].toString(),monitor)){
                    Date[i][2]="请任命";
                }else{
                    Date[i][2]=stuser.backName(monitor);
                }
                if(tzs==0||stuser.backName(tzs)==null||!stuser.isClassExist(Date[i][0].toString(),tzs)){
                    Date[i][3]="请任命";
                }else{
                    Date[i][3]=stuser.backName(tzs);
                }
                classmodel.addRow(Date[i]);
            }
            classtable.setRowHeight(30);
            classtable.setModel(classmodel);
        }
    }
//    宿舍管理
    public void dormsManger(){
        title= new String[]{"宿舍号", "实到人数", "剩余床位", "宿舍长"};
        x=dormsService.dormSelectAll().size();
        y=title.length;
        Date=new Object[x][y];
        dormtable.setFont(fon);
        r.setHorizontalAlignment(JLabel.CENTER);
        dormtable.setDefaultRenderer(Object.class,r);
        dormmodel=(DefaultTableModel) dormtable.getModel();
        dormmodel=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int column){
                if(column==0||column==1||column==2){
                    return false;
                }
                return true;
            }
        };
        dormmodel.setRowCount(0);
        dormmodel.setColumnIdentifiers(title);
        //在渲染宿舍表
        if(x>0){
            for(int i=0;i<x;i++){
                Date[i][0]=dormsService.dormSelectAll().get(i).getDorm_id();
                Date[i][1]=dormsService.dormSelectAll().get(i).getActual();
                Date[i][2]=dormsService.dormSelectAll().get(i).getRemaining_beds();
                long domotry=dormsService.dormSelectAll().get(i).getDomotry();
                String dorm=Date[i][0].toString();
                if(domotry==0||stuser.backName(domotry)==null||!stuser.isExist(dorm,domotry)){
                    Date[i][3]="请任命";
                }else{
                    Date[i][3]=stuser.backName(dormsService.dormSelectAll().get(i).getDomotry());
                }
                dormmodel.addRow(Date[i]);
            }
            dormtable.setRowHeight(30);
            dormtable.setModel(dormmodel);
        }
    }
//    学生管理
    public void studentsManger(){
        title= new String[]{"学号", "姓名", "性别", "学院", "专业", "班级", "手机号", "宿舍号", "注册时间"};
        x=stuser.stuSelectAll().size();
        y=title.length;
        Date=new Object[x][y];
        student.setFont(fon);
        r.setHorizontalAlignment(JLabel.CENTER);
        student.setDefaultRenderer(Object.class,r);
        studentModel=(DefaultTableModel) student.getModel();
        studentModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
            }
        });
        studentModel.setRowCount(0);
        studentModel.setColumnIdentifiers(title);
        if(x>0){
            for(int i=0;i<x;i++){
                Date[i][0]=stuser.stuSelectAll().get(i).getStudent_id();
                Date[i][1]=stuser.stuSelectAll().get(i).getName();
                if(stuser.stuSelectAll().get(i).getSex()){
                    Date[i][2]="男";
                }else{
                    Date[i][2]="女";
                }
                Date[i][3]=stuser.stuSelectAll().get(i).getAcademy();
                Date[i][4]=stuser.stuSelectAll().get(i).getSpecialty();
                Date[i][5]=stuser.stuSelectAll().get(i).getClss();
                Date[i][6]=stuser.stuSelectAll().get(i).getPhone_number();
                Date[i][7]=stuser.stuSelectAll().get(i).getDorm_id();
                Date[i][8]=stuser.stuSelectAll().get(i).getLogindate();
                studentModel.addRow(Date[i]);
            }
            student.setRowHeight(30);
            student.setModel(studentModel);
        }
    }
//    鼠标点击的监听事件
    MouseListener clickTable=new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(selectedIndex==1){
                int colum=dormtable.getSelectedColumn();
                if(colum==3){
                    setChar();
                }
            }else if(selectedIndex==0){
                int colum=classtable.getSelectedColumn();
                if(colum==2||colum==3){
                    setClassChar();
                }
            }else if(selectedIndex==2){
                int row=student.getSelectedRow();
                oldClass=student.getValueAt(row,5);
                oldDorm=student.getValueAt(row,7);
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
//添加班级职位
    public void setClassChar(){
        int row=classtable.getSelectedRow();
        int column=classtable.getSelectedColumn();
        List<Students> nameID=mangerController.FindName(classtable.getValueAt(row,0).toString());
        if(nameID.size()>0){
            Object[] possibleValues =new Object[nameID.size()];
            for(int i=0;i<nameID.size();i++){
                possibleValues[i]=nameID.get(i).getName();
            }
            Object selectedValue=null;
            if(column==2){
                selectedValue = JOptionPane.showInputDialog(null, "请选择班长", classtable.getValueAt(row,0).toString(), JOptionPane.INFORMATION_MESSAGE, null,  possibleValues, possibleValues[0]);
            }
            if(column==3){
                selectedValue = JOptionPane.showInputDialog(null, "请选择团支书", classtable.getValueAt(row,0).toString(), JOptionPane.INFORMATION_MESSAGE, null,  possibleValues, possibleValues[0]);
            }
            if(selectedValue!=null){
                classtable.setValueAt(selectedValue,row,column);
                for(int j=0;j<possibleValues.length;j++){
                    if(selectedValue==possibleValues[j]){
                        Vector v=new Vector();
                        v.addElement(classtable.getValueAt(row,1));
                        v.addElement(column);
                        v.addElement(nameID.get(j).getSid());
                        v.addElement(classesService.classesSelectAll().get(row).getMonitor());
                        v.addElement(classesService.classesSelectAll().get(row).getTzs());
                        Object id=classesService.classesSelectAll().get(row).getCid();
                        mangerController.manRev(0,id,v);
                    }
                }
            }
        }
    }
//    添加宿舍职位
    public void setChar(){
        int row=dormtable.getSelectedRow();
        int column=dormtable.getSelectedColumn();
        List<Students> nameID=mangerController.FindName(dormtable.getValueAt(row,0).toString());
        if(nameID.size()>0){
            Object[] possibleValues =new Object[nameID.size()];
            for(int i=0;i<nameID.size();i++){
                possibleValues[i]=nameID.get(i).getName();
            }
            Object selectedValue = JOptionPane.showInputDialog(null, "请选择宿舍长", dormtable.getValueAt(row,0).toString(), JOptionPane.INFORMATION_MESSAGE, null,  possibleValues, possibleValues[0]);
            if(selectedValue!=null){
                dormtable.setValueAt(selectedValue,row,column);
                for(int j=0;j<possibleValues.length;j++){
                    if(selectedValue==possibleValues[j]){
                        Vector v=new Vector();
                        v.addElement(nameID.get(j).getSid());
                        v.addElement(dormtable.getValueAt(row,1));
                        v.addElement(dormtable.getValueAt(row,2));
                        Object id=dormsService.dormSelectAll().get(row).getDid();
                        mangerController.manRev(1,id,v);
                    }
                }
            }
        }
    }
//    学生管理删除操作
    protected void do_button_actionPerformed(ActionEvent e){
        int[] selectedRow=student.getSelectedRows();
        for (int i = 0; i < selectedRow.length; i++) {
            selectedRow[i] = student.convertRowIndexToModel(selectedRow[i]);
        }
        DefaultTableModel model=(DefaultTableModel) student.getModel();
        for(int i=selectedRow.length;i>0;i--){
            Object id= stuser.stuSelectAll().get(student.getSelectedRow()).getSid();
            model.removeRow(student.getSelectedRow());
            mangerController.manDelet(selectedIndex,id);
        }
        student.setModel(model);
    }
//    学生表格数据变化
    public void amend(){
        int column=student.getSelectedColumn();
        int row=student.getSelectedRow();
        Object ojb=student.getValueAt(row,column);
        switch (column){
            case 0:
                if(!reg.st(ojb.toString())){
                    but1.enable(false);
                }
                break;
            case 1:
                if(!reg.na(ojb.toString())){
                    but1.enable(false);
                }
                break;
            case 2:
                if(!reg.se(ojb.toString())){
                    but1.enable(false);
                }
                break;
            case 3:
                if(reg.ac(ojb.toString())){
                    if(reg.sp(student.getValueAt(row,4).toString())){
                        if(reg.cl(student.getValueAt(row,5).toString())){
                        }else{
                            but1.enable(false);
                        }
                    }else{
                        but1.enable(false);
                    }
                }
                break;
            case 4:
                reg.ac(student.getValueAt(row,3).toString());
                if(!reg.sp(ojb.toString())){
                    but1.enable(false);
                }
                break;
            case 5:
                reg.ac(student.getValueAt(row,3).toString());
                reg.sp(student.getValueAt(row,4).toString());
                if(!reg.cl(ojb.toString())){
                    but1.enable(false);
                }else{
                    if(!classesService.classNameTime(ojb.toString())){
                        if(!oldClass.toString().equals(ojb.toString())){
                           int newActual=classesService.classesName(ojb.toString()).get(0).getActual();
                           long newMonitor=classesService.classesName(ojb.toString()).get(0).getMonitor();
                           long newTzs=classesService.classesName(ojb.toString()).get(0).getTzs();
                           long newCid=classesService.classesName(ojb.toString()).get(0).getCid();
                           classesService.newClassUpdate(newActual,newMonitor,newTzs,newCid);
                           int oldActual=classesService.classesName(oldClass.toString()).get(0).getActual();
                           long oldMonitor=classesService.classesName(oldClass.toString()).get(0).getMonitor();
                           long oldTzs=classesService.classesName(oldClass.toString()).get(0).getTzs();
                           long oldCid=classesService.classesName(oldClass.toString()).get(0).getCid();
                           classesService.oldClassUpdate(oldActual,oldMonitor,oldTzs,oldCid);
                        }
                    }
                }
                break;
            case 6:
                if(!reg.ph(ojb.toString())){
                    but1.enable(false);
                }
                break;
            case 7:
                if(!reg.dr(ojb.toString())){
                    but1.enable(false);
                }else{
                    if(!dormsService.dormNameTime(ojb.toString())){
                        if(!oldDorm.toString().equals(ojb.toString())){
                            int newActual=dormsService.dormsName(ojb.toString()).get(0).getActual();
                            int newBeds=dormsService.dormsName(ojb.toString()).get(0).getRemaining_beds();
                            long newId=dormsService.dormsName(ojb.toString()).get(0).getDid();
                            long newDomor=dormsService.dormsName(ojb.toString()).get(0).getDomotry();
                            dormsService.newDormUpdate(newActual,newBeds,newId,newDomor);
                            int oldActual=dormsService.dormsName(oldDorm.toString()).get(0).getActual();
                            int oldBeds=dormsService.dormsName(oldDorm.toString()).get(0).getRemaining_beds();
                            long id=dormsService.dormsName(oldDorm.toString()).get(0).getDid();
                            long domor=dormsService.dormsName(oldDorm.toString()).get(0).getDomotry();
                            dormsService.oldDormUpdate(oldActual,oldBeds,id,domor);
                        }
                        else{
                            System.out.println("前后值一样");
                            but1.enable(false);
                        }
                    }
                }
                break;
        }
    }
//    学生管理的保存修改
    protected void do_button1_actionPerformed(ActionEvent e){
       Vector lists= studentModel.getDataVector();
       for(int i=0;i<lists.size();i++){
           Vector list=(Vector)lists.get(i);
           Object id=stuser.stuSelectAll().get(i).getSid();
           mangerController.manRev(selectedIndex,id,list);
       }
    }

}
