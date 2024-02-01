package GGVpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MoviePayment extends JPanel {

    GGV origin;
    public JPanel fp;

    // 영화
    public String title = "예매 내역 없음";

    // 시간
    public String time;

    // 상영관
    public String roomNum;

    // 가격
    public String price;

    // 선택된 좌석
    public String seatNo;

    // 결제 옵션 라디오 버튼
    public String[] paymentOptionArr = { "신용/체크카드", "계좌이체", "가상계좌", "네이버페이", "카카오페이", "토스" };
    public JRadioButton[] optionArr = new JRadioButton[paymentOptionArr.length];

    // 결제 버튼
    public JButton payment_btn = new JButton("결제");

    public JPanel paymentpanel;

    ButtonGroup btgroup; // 함수에서 쓰기위해 외부로 빼서 구현

    public MoviePayment(JPanel fp, GGV origin) {
        this.origin = origin;
        this.fp = fp;
        setResult();
        buildGUI();
        setEvent();

    }

    public void buildGUI() {

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setBounds(10, 10, 605, 864);
        setVisible(true);

        // 위 구분 줄
        JLabel line01 = new JLabel("==============================");
        line01.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        line01.setBounds(120, 10, 400, 50);

        // 영화 제목
        JPanel movieTpanel = new JPanel();
        movieTpanel.setBackground(Color.LIGHT_GRAY);
        JLabel mtitle = new JLabel(title);
        mtitle.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        mtitle.setHorizontalAlignment(JLabel.CENTER);
        movieTpanel.setBounds(120, 50, 330, 50);
        movieTpanel.add(mtitle);

        // 상영시간
        JLabel mtime = new JLabel(time);
        mtime.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        mtime.setBounds(180, 100, 400, 50);

        // 상영관
        JLabel mNum = new JLabel(roomNum);
        mNum.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        mNum.setBounds(260, 150, 400, 50);

        // 좌석 정보 (12개 다고르면 창 넘어감,,, 다른 방법 생각해보자)
        JLabel sNum = new JLabel("<html><body><center>좌석 : " + seatNo + "</center></body></html>");
        sNum.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        sNum.setBounds(170, 200, 250, 50);

        // 아래 구분 줄
        JLabel line02 = new JLabel("==============================");
        line02.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        line02.setBounds(120, 250, 400, 50);

        // 결제 옵션
        JLabel chose = new JLabel("결제 수단 선택");
        chose.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        chose.setBounds(120, 275, 400, 50);

        // 버튼 위 구분 줄
        JLabel line03 = new JLabel("==============================");
        line03.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        line03.setBounds(120, 300, 400, 50);

        // 라디오 버튼 붙을 패널
        JPanel radiopanel = new JPanel(new GridLayout(2, 3, 5, 5));
        radiopanel.setBackground(Color.LIGHT_GRAY);
        radiopanel.setBounds(120, 355, 330, 200);
        // 버튼 그룹
        btgroup = new ButtonGroup();
        // 라디오 버튼 생성
        for (int i = 0; i < optionArr.length; i++) {
            optionArr[i] = new JRadioButton(paymentOptionArr[i]);
            optionArr[i].setBackground(Color.GRAY);
            optionArr[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));

            btgroup.add(optionArr[i]);
            radiopanel.add(optionArr[i]);
        }

        optionArr[0].setSelected(true); // 기본으로 신용체크카드 선택

        // 품목 아래 구분 줄
        JLabel line04 = new JLabel("==============================");
        line04.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        line04.setBounds(120, 550, 400, 50);

        // 결제 가격
        JLabel pricelabel = new JLabel("결제 금액 : " + price + "원");
        pricelabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        pricelabel.setBounds(280, 580, 400, 50);

        JLabel line05 = new JLabel("==============================");
        line05.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        line05.setBounds(120, 610, 400, 50);

        // 결제 버튼
        payment_btn.setBackground(Color.GRAY);
        payment_btn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        payment_btn.setBounds(300, 655, 150, 50);

        JLabel line06 = new JLabel("==============================");
        line06.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        line06.setBounds(120, 695, 400, 50);

        add(line01);
        add(movieTpanel);
        add(mtime);
        add(mNum);
        add(sNum);
        add(line02);
        add(chose);
        add(line03);
        add(radiopanel);
        add(line04);
        add(pricelabel);
        add(line05);
        add(payment_btn);
        add(line06);

        fp.add(this);

    }

    public void setEvent() {
        // 결제 버튼에 기능 연결
        payment_btn.addActionListener(payment_btnListener);

    }

    // 값 지정
    public void setResult() {
        this.title = origin.title;
        this.time = origin.time;
        this.roomNum = origin.roomNum;
        this.price = origin.price;
        this.seatNo = origin.seatNo;
    }

    // 결제 버튼 리스너
    ActionListener payment_btnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
//	            boolean ischeked;

            // 누른 라디오 버튼의 내용 담을 변수
            String strFromrb = "";
            for (int i = 0; i < paymentOptionArr.length; i++) {
                if (optionArr[i].isSelected()) {
                    strFromrb = paymentOptionArr[i]; // 누른 버튼 정보 저장
                    break;
                }
                // 여기 예외처리 넣을수있지만 일단안함
            }

            int result = JOptionPane.showConfirmDialog(null, // 중앙에 출력
                    strFromrb + "로 " + price + "원을 결제하시겠습니까?", // 메세지
                    "결제", // 다이얼로그 제목
                    JOptionPane.YES_NO_OPTION, 3);

            // OK 버튼 누르면
            if (result == 0) {
                JOptionPane.showMessageDialog( // 화면 중앙에서 메세지 출력
                        null, "결제가 완료되었습니다!");
                MovieList movieList = new MovieList(fp, origin);
                fp.remove(fp.getComponent(0));
            }

            //
        }
    };

}
