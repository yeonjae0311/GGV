package GGVpack;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FindPassword extends JPanel{
    JPanel fp;
    GGV origin;

    public int editedidx=-1;
    public int currentidx;

    public JLabel idLabelfp;
    public JLabel nameLabelfp;
    public JLabel phoneLabelfp;
    public JLabel mailLabelfp;

    public JTextField idTextFieldfp;
    public JTextField nameTextFieldfp;
    public JTextField phoneTextFieldfp;
    public JTextField mailTextFieldfp;
    public JButton findPW_btn;
    public JDialog popUpAskingPW;
    JPanel titlePanel;

    JLabel pwInfoMessage;
    JLabel findPasswordTitle;
    JPasswordField pwTextfieldindialog;
    JPasswordField pwcTextfieldindialog;
    JButton changePWConfirm_btn;
    JLabel backSpace;

    JLabel warningLabelinDialog;
    Font fontRegister = new Font("굴림", Font.PLAIN, 15);

    Map<Integer,String> userInfoUpdateMap = new HashMap<>();
    public FindPassword(JPanel fp, GGV origin){
        this.fp=fp;
        this.origin=origin;
        buildGUI();
        setEvent();
    }

    public void setEditedidx(String str){
        int idxbar = str.indexOf("|");
        String idstr = str.substring(1,idxbar);
        if(idstr.equals(idTextFieldfp.getText())){
            editedidx=currentidx;
            System.out.println("e:"+editedidx);
        }
    }

    ActionListener findPW_btnListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                File f = new File("src/db/UsersInfo.txt");
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String readstr ="";
                currentidx=0;
                outer:while((readstr=br.readLine())!=null){
                    System.out.println(currentidx+" "+readstr);
                    userInfoUpdateMap.put(currentidx,readstr);
                    //해당 아이디에 해당하는 idx를 찾음 불린을 얻어내어서 맞으면 edited에 대입
                    if(editedidx==-1){
                        System.out.println(editedidx);
                        setEditedidx(readstr);
                    }
                    currentidx++;
                }

                if(editedidx==-1){
                    JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다.");
                }else{
                    parse(userInfoUpdateMap.get(editedidx));
                }
                br.close();
                fr.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    public void parse(String str){ // 각문자열을 가져가서
        if(str.length()<5) return;
        int idx = str.indexOf("|"); //아이디
        String str1 = str.substring(1, idx);
        int idx2 = str.indexOf("|", idx + 1);
        String str2 = str.substring(idx + 1, idx2);//비밀번호

        int idx3 = str.indexOf("|",idx2+1);
        String str3 = str.substring(idx2+1,idx3);//이름

        int idx4 = str.indexOf("|",idx3+1);
        String str4 = str.substring(idx3+1,idx4);//번호
        System.out.println(str4);

//        int idx5 = str.indexOf("|",idx4+ㅍ
        String str5 = str.substring(idx4+1);//메일
        str5 = str5.substring(0,str5.length()-2);
        compareStrings(str3,str4,str5);
    }

    public void compareStrings(String name,String phone,String mail){
        String getNameTextFieldValue = nameTextFieldfp.getText();
        String getPhoneTextFieldValue = phoneTextFieldfp.getText();
        String getMailTextFieldValue = mailTextFieldfp.getText();

        if( (getNameTextFieldValue.equals(name))
                && (getPhoneTextFieldValue.equals(phone))
                && (getMailTextFieldValue.equals(mail)) ) {
            System.out.println("모든 정보를 다맞춤");
            System.out.println("e는 "+editedidx);
            JOptionPane.showMessageDialog(null, "비밀번호 찾기 성공");
            showAskingPopUp();
        }else {
            System.out.println("다른값이 존재함");
            System.out.println(name);
            System.out.println(phone);
            System.out.println(mail);
            System.out.println(getNameTextFieldValue);
            System.out.println(getPhoneTextFieldValue);
            System.out.println(getMailTextFieldValue);
            JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다.");
        }
    };

    public String makeUserInfo(){
        String newUserInfo = "{";
        newUserInfo+=idTextFieldfp.getText();
        newUserInfo+="|";
        newUserInfo+=pwTextfieldindialog.getText();
        newUserInfo+="|";
        newUserInfo+=nameTextFieldfp.getText();
        newUserInfo+="|";
        newUserInfo+=phoneTextFieldfp.getText();
        newUserInfo+="|";
        newUserInfo+=mailTextFieldfp.getText();
        newUserInfo+="},";
        return newUserInfo;
    }

    public void makeNewUserInfotxt(){
        String fullInfo = makeUserInfo();

        try {
            FileWriter fw = new FileWriter("src/db/UsersInfo.txt");
            BufferedWriter br = new BufferedWriter(fw);

//            System.out.println("c:"+currentidx);
//            System.out.println("e:"+editedidx);
            for(int i = 0; i< userInfoUpdateMap.size(); i++){
                if(i!=editedidx){
                    br.write(userInfoUpdateMap.get(i)+"\n");
                }else{
//                    System.out.println("순조로운진행");
                    br.write(fullInfo+"\n");
                }
            }
            br.close();
            fw.close();
            System.out.println("새로운 유저 데이터  db 완료추정");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    DocumentListener pwInDialogTextFieldListenner = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            changed();
        }
        public void changed(){
            if(pwTextfieldindialog.getText().length()<7){
                warningLabelinDialog.setText("비밀번호의 최소 길이는 7자 입니다.");
            }

            if(pwTextfieldindialog.getText().equals(pwcTextfieldindialog.getText()) && pwTextfieldindialog.getText().length()>6){
                changePWConfirm_btn.setEnabled(true);
            }else{
                changePWConfirm_btn.setEnabled(false);
            }
        }
    };

    ActionListener changePWConfirm_btnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pwTextfieldindialog.getText().equals(pwcTextfieldindialog.getText())){
                //그 비밀번호값을 받아와서 갱신이필요
                //비밀번호를 실질적으로 만들고 저장하는 함수필요
                //비번찾는곳의 비번 재정의 하는 함수만들차례
                makeNewUserInfotxt();

                popUpAskingPW.dispose();
                origin.loginedUsername=idTextFieldfp.getText();
                origin.islogined=true;
                MovieList movieList = new MovieList(fp,origin);
                fp.remove(fp.getComponent(0));
                //씬추가
            }
        }
    };

    public void showAskingPopUp(){
        popUpAskingPW = new JDialog(origin.f,"비밀번호 변경",true);
        JPanel popUpAskingPWpanel = new JPanel();
        popUpAskingPW.setSize(280,180);


        popUpAskingPWpanel.setLayout(null);
        popUpAskingPWpanel.setSize(new Dimension(90,60));
        popUpAskingPWpanel.setLocation(0,0);

        JLabel notifyingLabel1 = new JLabel("새로운 비밀번호");
        JLabel notifyingLabel2 = new JLabel("비밀번호 확인");
        //지역변수라서
        pwTextfieldindialog = new JPasswordField(30);
        pwcTextfieldindialog = new JPasswordField(30);

        warningLabelinDialog = new JLabel();
        warningLabelinDialog.setFont(new Font(Font.DIALOG, 0, 10));
        changePWConfirm_btn = new JButton("변경");

        notifyingLabel1.setBounds(20,10,160,30);
        notifyingLabel2.setBounds(20,60,160,30);
        pwTextfieldindialog.setBounds(120,15,120,25);
        pwcTextfieldindialog.setBounds(120,65,120,25);
        warningLabelinDialog.setBounds(55,45,200,10);
        changePWConfirm_btn.setBounds(105,103,60,25);

        //확인 버튼과 두개의 비밀번호 필드의
        changePWConfirm_btn.addActionListener(changePWConfirm_btnListener);
        pwTextfieldindialog.getDocument().addDocumentListener(pwInDialogTextFieldListenner);
        pwcTextfieldindialog.getDocument().addDocumentListener(pwInDialogTextFieldListenner);


        popUpAskingPWpanel.add(notifyingLabel1);
        popUpAskingPWpanel.add(pwTextfieldindialog);
        popUpAskingPWpanel.add(Box.createRigidArea(new Dimension(300,2)));
        popUpAskingPWpanel.add(notifyingLabel2);
        popUpAskingPWpanel.add(pwcTextfieldindialog);
        popUpAskingPWpanel.add(warningLabelinDialog);
        popUpAskingPWpanel.add(changePWConfirm_btn);


        popUpAskingPWpanel.revalidate();
        popUpAskingPWpanel.repaint();

        popUpAskingPW.add(popUpAskingPWpanel);
//        popUpAskingPW.pack();
        popUpAskingPW.setLocationRelativeTo(origin.f);
        popUpAskingPW.setVisible(true);
    }





    public void buildGUI(){

        setSize(origin.size);
        setBackground(Color.LIGHT_GRAY);
        setSize(new Dimension(605, 864));
        setLayout(null);

        int x = 100;
        int y = 180;
        int length = 280;

        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(50,50,605, 80);
        titlePanel.setBackground(Color.lightGray);
        titlePanel.setVisible(true);

        backSpace = new JLabel();
        ImageIcon back = new ImageIcon("src/images/back.png");
        backSpace.setIcon(back);
        backSpace.setBackground(Color.lightGray);
        backSpace.setSize(20, 20);

        findPasswordTitle = new JLabel("              비밀번호 찾기");
        findPasswordTitle.setFont(new Font("나눔스퀘어",Font.BOLD, 30));

        pwInfoMessage = new JLabel("<html>회원가입 시 입력했던 회원정보를 확인하여 일치할 경우<br /> 새로운 비밀번호로 변경할 수 있습니다.</html>");
        pwInfoMessage.setBounds(x, y - 10, 400, 80);
        pwInfoMessage.setFont(new Font(Font.DIALOG, Font.ITALIC, 15));

        idLabelfp = new JLabel("아이디");
        idLabelfp.setBounds(x, y + 70 * 2, 90,40);
        idLabelfp.setFont(fontRegister);
        idTextFieldfp = new JTextField();
        idTextFieldfp.setBounds(x + 100, y + 70 * 2 + 10, length, 30);

        nameLabelfp = new JLabel("이름");
        nameLabelfp.setBounds(x, y + 70 * 3, 90, 40);
        nameLabelfp.setFont(fontRegister);
        nameTextFieldfp = new JTextField();
        nameTextFieldfp.setBounds(x + 100, y + 70 * 3 + 10, length, 30);

        phoneLabelfp = new JLabel("번호");
        phoneLabelfp.setBounds(x, y + 70 * 4, 90, 40);
        phoneLabelfp.setFont(fontRegister);
        phoneTextFieldfp = new JTextField();
        phoneTextFieldfp.setBounds(x + 100, y + 70 * 4 + 10, length, 30);

        mailLabelfp = new JLabel("메일");
        mailLabelfp.setBounds(x, y + 70 * 5, 90, 40);
        mailLabelfp.setFont(fontRegister);
        mailTextFieldfp = new JTextField();
        mailTextFieldfp.setBounds(x + 100, y + 70 * 5 + 10, length, 30);

        findPW_btn = new JButton(" 비밀번호 찾기");
        findPW_btn.setBounds(x + 135, y + 430, 120, 30);

        add(titlePanel);
        titlePanel.add(backSpace, BorderLayout.WEST);
        titlePanel.add(findPasswordTitle);
        add(pwInfoMessage);
        add(idLabelfp);
        add(idTextFieldfp);
        add(nameLabelfp);
        add(nameTextFieldfp);
        add(phoneLabelfp);
        add(phoneTextFieldfp);
        add(mailLabelfp);
        add(mailTextFieldfp);
        add(findPW_btn);
        fp.add(this);
    }

    MouseListener backSpaceLabel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() == backSpace) {
                Login login = new Login(fp, origin);
                fp.remove(fp.getComponent(0));
            }
        }
    };
    public void setEvent(){

        findPW_btn.addActionListener(findPW_btnListener);
        backSpace.addMouseListener(backSpaceLabel);
    }
}
