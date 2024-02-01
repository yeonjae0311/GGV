package GGVpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

// 로그인 화면 패널
public class Login extends JPanel {

    // 이미지 소스 : <a href="https://www.flaticon.com/kr/free-icons/" title="영화관 아이콘">영화관 아이콘  제작자: Flat Icons - Flaticon</a>

    // 변수(public으로)
    GGV origin;
    public JPanel fp;
    public JLabel idLabel;
    public JLabel pwLabel;
    public JTextField idTextField;
    public JPasswordField pwTextField;
    public JButton login_btn;
    public JButton join_btn;
    public JButton findPw_btn;

    public Login(JPanel fp, GGV origin) {
        this.origin = origin;
        this.fp = fp;
        buildGUI();
        setEvent();
    }

    //
    public void buildGUI() {

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setBounds(10, 10, 605, 864);
        setVisible(true);

        // 로고 사진
        ImageIcon logo = new ImageIcon("src/images/cinema2.png");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logo);
        logoLabel.setBounds(177, 90, 250, 250);

        // 아이디 입력란
        idLabel = new JLabel("아이디");
        idTextField = new JTextField(20);
        idLabel.setBounds(100, 400, 70, 50);
        idTextField.setBounds(180, 410, 280, 30);

        // 비밀번호 입력란
        pwLabel = new JLabel("비밀번호");
        pwTextField = new JPasswordField(20);
        pwLabel.setBounds(100, 460, 70, 50);
        pwTextField.setBounds(180, 470, 280, 30);

        // 로그인, 회원가입, 비밀번호 찾기 버튼

        login_btn = new JButton("로그인");
        login_btn.setBackground(Color.GRAY);
        login_btn.setFont(new Font("맑은 고딕", Font.BOLD, 11));
        login_btn.setBounds(210, 535, 90, 30);
        join_btn = new JButton("회원가입");
        join_btn.setBackground(Color.GRAY);
        join_btn.setFont(new Font("맑은 고딕", Font.BOLD, 11));
        join_btn.setBounds(330, 535, 90, 30);
        findPw_btn = new JButton("비밀번호 찾기");
        findPw_btn.setBackground(Color.GRAY);
        findPw_btn.setFont(new Font("맑은 고딕", Font.BOLD, 11));
        findPw_btn.setBounds(195, 585, 240, 25);

        add(idLabel);
        add(idTextField);
        add(pwLabel);
        add(pwTextField);
        add(login_btn);
        add(join_btn);
        add(findPw_btn);
//        findPw_btn.setVisible(false);
        add(logoLabel);

        fp.add(this);
        fp.revalidate();
        fp.repaint();
    }

    public void setEvent() {
        login_btn.addActionListener(tryloginactionlistener);
        join_btn.addActionListener(registeractionlistener);
        findPw_btn.addActionListener(findPw_btnListener);
    }

    ActionListener findPw_btnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            FindPassword findPassword = new FindPassword(fp,origin);
            fp.remove(fp.getComponent(0));
        }
    };

    ActionListener registeractionlistener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            System.out.println(b.getText());

            Register register = new Register(fp,origin);
            fp.remove(fp.getComponent(0));

        }
    };

    ActionListener tryloginactionlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("로그인 시도 버튼이 눌림");
            String idtemp = idTextField.getText();
            String pwtemp = pwTextField.getText();
//			System.out.println(idtemp);
//			System.out.println(pwtemp);
            String readpath = "src/db/UsersInfo.txt";
            try {
                FileReader fr = new FileReader(readpath);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    System.out.println();
                    String[] temps = line.split(",");
                    for (String r : temps) {
						System.out.println(r);
                        if (catchidpw(r, idtemp, pwtemp)) {
                            return;
                        }
                    }
                    System.out.println();
                }

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);

        }
    };

    public boolean catchidpw(String str, String id, String pw) {
        int idx = str.indexOf("|");
        String str1 = str.substring(1, idx);
        int idx2 = str.indexOf("|", idx + 1);
        String str2 = str.substring(idx + 1, idx2);
        if (str1.equals(id) && str2.equals(pw)) {
            System.out.println("로그인 성공");
            JOptionPane.showMessageDialog(null, "로그인 성공");
            origin.loginedUsername=id;
            origin.islogined = true;

            MovieList movielist = new MovieList(fp, origin);
            fp.remove(fp.getComponent(0));

//            fp.revalidate();
//            fp.repaint();

            return true;
        } else {
            System.out.println("로그인 실패");
            idTextField.setText("");
            pwTextField.setText("");
        }
        return false;
    }
}
