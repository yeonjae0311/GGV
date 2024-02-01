package GGVpack;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

// 회원가입 패널
public class Register extends JPanel {

    public JPanel fp;
    public GGV origin;
    public JLabel logoLabelr;
    public JPanel gh;
    public JLabel idLabelr;
    public JLabel pwLabelr;
    public JLabel pwcLabelr;// password confirm 약자
    public JLabel nameLabelr;
    public JLabel phoneLabelr;
    public JLabel mailLabelr;

    public JTextField idTextFieldr;
    public JPasswordField pwTextFieldr;
    public JPasswordField pwcTextFieldr;
    public JTextField nameTextFieldr;
    public JTextField phoneTextFieldr;
    public JTextField mailTextFieldr;

    public JButton idCheck_btn;
    public JLabel idWarning;
    public JLabel pwWarning;
    public JLabel pwcWarning;

    public JButton enroll_btn;
    public boolean isIDexisted = true;
    public boolean isUnder7pw = true;
    public boolean ispwCheck = true;
    public FileWriter fw; // 회원가입 계정 저장용 writer
    public BufferedWriter bw;

    public Register(JPanel fp,GGV origin) {
        this.fp = fp;
        this.origin = origin;
        buildGUI();
        setEvent();
    }

    public void buildGUI() {

        Font fontRegister = new Font("굴림", Font.PLAIN, 15);

        Color registerColor= new Color(120, 200, 200);
        setBackground(new Color(120, 200, 200));
        setSize(new Dimension(605, 864));
        setLayout(null);

        int x = 40;
        int y = 180;
        int length = 280;

        logoLabelr = new JLabel("GGV 회원가입");
        logoLabelr.setFont(new Font("굴림", Font.BOLD, 30));
        logoLabelr.setBounds(185, 80, 270, 40);

        idLabelr = new JLabel("아이디");
        idLabelr.setBounds(x, y, 90,40);
        idLabelr.setFont(fontRegister);
        idTextFieldr = new JTextField();
        idTextFieldr.setBounds(x + 100, y + 10, length, 30);

        pwLabelr = new JLabel("비밀번호");
        pwLabelr.setBounds(x, y + 70, 90, 40);
        pwLabelr.setFont(fontRegister);
        pwTextFieldr = new JPasswordField();
        pwTextFieldr.setBounds(x + 100, y + 70 + 10, length, 30);

        pwcLabelr = new JLabel("비밀번호확인");// password confirm 약자
        pwcLabelr.setBounds(x, y + 70 * 2, 90, 40);
        pwcLabelr.setFont(fontRegister);
        pwcTextFieldr = new JPasswordField();
        pwcTextFieldr.setBounds(x + 100, y + 70 * 2 + 10, length, 30);

        nameLabelr = new JLabel("이름");
        nameLabelr.setBounds(x, y + 70 * 3, 90, 40);
        nameLabelr.setFont(fontRegister);
        nameTextFieldr = new JTextField();
        nameTextFieldr.setBounds(x + 100, y + 70 * 3 + 10, length, 30);

        phoneLabelr = new JLabel("번호");
        phoneLabelr.setBounds(x, y + 70 * 4, 90, 40);
        phoneLabelr.setFont(fontRegister);
        phoneTextFieldr = new JTextField();
        phoneTextFieldr.setBounds(x + 100, y + 70 * 4 + 10, length, 30);

        mailLabelr = new JLabel("메일");
        mailLabelr.setBounds(x, y + 70 * 5, 90, 40);
        mailLabelr.setFont(fontRegister);
        mailTextFieldr = new JTextField();
        mailTextFieldr.setBounds(x + 100, y + 70 * 5 + 10, length, 30);

        idCheck_btn = new JButton("중복확인");
        idCheck_btn.setBounds(x + 410, y + 10, 85, 25);
        idWarning = new JLabel("ID 중복확인이 필요합니다.");
        idWarning.setBounds(x + 160, y + 40, length, 30);
        pwWarning = new JLabel("비밀번호를 7자 이상 입력하세요");
        pwWarning.setBounds(x + 160, y + 110, length, 30);
        pwcWarning = new JLabel("비밀번호가 일치하지 않습니다");
        pwcWarning.setBounds(x + 160, y + 180, length, 30);

        enroll_btn = new JButton("회원가입하기");

        enroll_btn.setBounds(x + 190, y + 430, 120, 30);

        add(logoLabelr);
        add(idLabelr);
        add(idTextFieldr);
        add(idCheck_btn);
        add(idWarning);
        add(Box.createRigidArea(new Dimension(605, 10)));

        add(pwLabelr);
        add(pwTextFieldr);
        add(pwWarning);
        add(Box.createRigidArea(new Dimension(605, 10)));

        add(pwcLabelr);
        add(pwcTextFieldr);
        add(pwcWarning);
        add(Box.createRigidArea(new Dimension(605, 10)));

        add(nameLabelr);
        add(nameTextFieldr);
        add(Box.createRigidArea(new Dimension(605, 10)));

        add(phoneLabelr);
        add(phoneTextFieldr);
        add(Box.createRigidArea(new Dimension(605, 10)));

        add(mailLabelr);
        add(mailTextFieldr);
        add(Box.createRigidArea(new Dimension(605, 10)));

        add(enroll_btn);

        revalidate();
        repaint();
        fp.add(this);
    }

    public void setEvent() {
        enroll_btn.addActionListener(enroll_btn_actionlistener);
        idCheck_btn.addActionListener(idavailablecheck_actionlistener);
        idTextFieldr.getDocument().addDocumentListener(idchangeDocumentListener);
        pwTextFieldr.getDocument().addDocumentListener(pwchangeDocumentListener);
        pwcTextFieldr.getDocument().addDocumentListener(pwcchangeDocumentListener);
    }

    ActionListener idavailablecheck_actionlistener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkidavailablefunc();
        }
    };

    public void checkidavailablefunc() {
        String readpath = "src/db/UsersID.txt";
        String tryingidstring = idTextFieldr.getText();
        try {
            FileReader fr = new FileReader(readpath);
            BufferedReader br = new BufferedReader(fr);
            String line;


            isIDexisted=false;
            outer: while ((line = br.readLine()) != null) {
                System.out.println(line);
                System.out.println();
                String[] temps = line.split("\\|");
                for (String r : temps) {
                    if(r.equals(tryingidstring)){
                        isIDexisted=true;
                        break outer;
                    }
                }
                System.out.println();
            }

            if (isIDexisted) {
                JOptionPane.showMessageDialog(null, "중복된 아이디가 있습니다.");
                idWarning.setText("사용중인 아이디입니다.");
                idWarning.setForeground(Color.red);
            } else {
                JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
                idWarning.setText("사용 가능한 아이디입니다.");
                idWarning.setForeground(Color.green);
            }

            br.close();
            fr.close();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    ActionListener enroll_btn_actionlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("enroll 버튼이 눌림");
            if(isIDexisted) {
                JOptionPane.showMessageDialog(null, "아이디 중복 확인이 필요합니다.", "오류", JOptionPane.ERROR_MESSAGE);

                idWarning.setText("아이디 중복 확인 필요");
//                System.out.println("아이디 중복 확인 실패로 거절");
                return;
            }else if(isUnder7pw){
                JOptionPane.showMessageDialog(null, "비밀번호를 8자 이상 입력하세요", "오류", JOptionPane.ERROR_MESSAGE);

                pwWarning.setText("비밀번호를 8자 이상 입력하세요");
                return;
            }else if(ispwCheck){
                JOptionPane.showMessageDialog(null, "비밀번호 확인이 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);

                pwcWarning.setText("비밀번호 확인이 일치하지 않습니다.");
                return;
            }
            System.out.println("거절시에는 이콘솔이 찍히면안됨");
            String towrite = "{";

            towrite += idTextFieldr.getText();
            System.out.println(idTextFieldr.getText());
            towrite += "|";

            towrite += pwTextFieldr.getText();
            System.out.println(pwTextFieldr.getText());
            towrite += "|";

            towrite += nameTextFieldr.getText();
            System.out.println(nameTextFieldr.getText());
            towrite += "|";

            towrite += phoneTextFieldr.getText();
            System.out.println(phoneTextFieldr.getText());
            towrite += "|";

            towrite += mailTextFieldr.getText();
            System.out.println(mailTextFieldr.getText());
            towrite += "},";

            System.out.println("towrite : " + towrite);

            //계정의 모든 값을 적는 경로
            String writepath = "src/db/UsersInfo.txt";


            try {
                fw = new FileWriter(writepath, true);
                bw = new BufferedWriter(fw);
                // 여기서 towrite 검사 메서드를 추가할수잇음 현재는
                // scene연결하는중이라서 이 메서드는 추후
                bw.write(towrite);
                bw.write("\n");

//				fw2 = new FileWriter(idsonlypath, true);
//				bw = new BufferedWriter(fw2);
//				bw.write(idtfr.getText());

                bw.close();
                fw.close();

                String writepath2 = "src/db/UsersID.txt";
                FileWriter fw2 = new FileWriter(writepath2,true);
                bw = new BufferedWriter(fw2);
                bw.write(idTextFieldr.getText()+"|");

                bw.close();
                fw2.close();

                fp.remove(fp.getComponent(0));
                Login login = new Login(fp,origin);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
    };

//    public boolean parseidandcompare(String line, String id) {
//        int idxfrom = line.indexOf("{");
//        int idxto = line.indexOf("|");
//        String idfromtext = line.substring(idxfrom + 1, idxto);
//        if (idfromtext.equals(id)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    DocumentListener idchangeDocumentListener = new DocumentListener() {
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

        public void changed() {
            idWarning.setText("아이디 중복확인 필요");
            idWarning.setForeground(Color.red);
            isIDexisted = true;
        }
    };

    DocumentListener pwchangeDocumentListener = new DocumentListener() {
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

        public void changed() {
            int len = pwTextFieldr.getText().length();
            confirmPassword();
            if (len < 7) {
                pwWarning.setText("비밀번호는 7자이상 입력 필요!");
                pwWarning.setForeground(Color.red);
                isUnder7pw = true;
            } else {
                pwWarning.setText("비밀번호는 7자이상 입력 확인!");
                pwWarning.setForeground(Color.green);
                isUnder7pw = false;
            }
        }
    };

    DocumentListener pwcchangeDocumentListener = new DocumentListener() {
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

        public void changed() {
            confirmPassword();
        }
    };

    public void confirmPassword() {
        String pw1 = pwTextFieldr.getText();
        String pw2 = pwcTextFieldr.getText();
        if (pw1.equals(pw2)) {
            pwcWarning.setText("비밀번호 정상 입력 !");
            pwcWarning.setForeground(Color.green);
            ispwCheck = false;
        } else {
            pwcWarning.setText("비밀번호 확인 불일치 !");
            pwcWarning.setForeground(Color.red);
            ispwCheck = true;
        }
    }
}
