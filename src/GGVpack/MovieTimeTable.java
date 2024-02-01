package GGVpack;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MovieTimeTable extends JPanel {
    // Dialog용 변수
    public JDialog ticketCountDialog;
    public JPanel ticketCountpanel;
    public String ageGroupArr[] = { "성인", "청소년", "어린이" };
    public int agePriceArr[] = { 8000, 7000, 6000 };
    public Integer howManyticket[] = { 0, 1, 2, 3, 4 }; // int ok?

    public JLabel ageInfo;
    public JLabel ageChargeTableArr[]; // 3
    public JComboBox<Integer> comboBoxforAdult;
    public JComboBox<Integer> comboBoxforTeen;
    public JComboBox<Integer> comboBoxforKids;

    // dialog 완료 버튼
    public JButton OKdialog;
    public int selectedAdultValue;
    public int selectedTeenValue;
    public int selectedKidValue;
    public int selectableTotalNum;

    // 변수명
    GGV origin;
    JPanel fp;
    public JPanel motherScrollpanel;
    public JPanel timeTableSetArr[];
    public int moviecount = 4;
    public int movieTime = 4;
    public int thickness = 2; // 버튼 보더라인 두께 설정
    public Border border;
    Color borderColor = Color.BLACK; // 보더라인 색상

    public JLabel movieNamepanel[];
    public JLabel movietableUpperpanel[];
    public JLabel movieInfopanel[];
    public JPanel movieTimespanel[];

    public JButton movieTimeEach[][];
    public String startEmpty = "    ";
    public String[] movienames = { "오펜하이머 (Oppenheimer) - 1관 ", "엘리멘탈 (Elemental) - 2관 ", "해리포터와 혼혈왕자 (Harry Potter) - 3관 ", "더 퍼스트 슬램덩크 (The First Slam Dunk) - 4관 "};
    public String[] movieInfo = {
            "<html>스릴러 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>★8.51<br>180분</html>",
            "<html>애니메이션 &nbsp;&nbsp;<br>★8.94<br>109분</html>",
            "<html>판타지 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>★7.84<br>153분</html>",
            "<html>애니메이션 &nbsp;&nbsp;<br>★9.26<br>124분</html>" };

    public String[][] moviesTimesString = { { "07:00~10:10", "11:05~14:15", "16:40~19:50", "20:15~23:25" },
            { "09:30~11:29", "12:05~14:04", "14:30~16:29", "17:00~18:59" },
            { "10:30~13:13", "13:30~16:13", "16:30~19:13", "19:30~22:13" },
            { "08:00~10:14", "11:15~14:15", "15:20~17:34", "18:00~20:14" } };

    public JScrollPane timeTablescrollPane;

    public MovieTimeTable(GGV origin, JPanel fp) {
        this.origin = origin;
        this.fp = fp;
        buildGUI();
        setEvent();
    }

    public void buildGUI() {
        System.out.println("진입");
        setLayout(new BorderLayout());
        // 스크롤 기능이 달릴 패널
        motherScrollpanel = new JPanel();

        // 영화당 시간표 4개를 전부 포함하는 패널 (movieName + movieTimes)
        timeTableSetArr = new JPanel[moviecount];

        movieNamepanel = new JLabel[moviecount]; // ppt에서 파란색(영화제목)을 담을 부분
        movieInfopanel = new JLabel[moviecount]; // 영화정보를 담을 부분
        movieTimespanel = new JPanel[moviecount]; // ppt에서 노란색들(영화 상영 시간)을 담을 부분 가로로 한줄당 1개
        movieTimeEach = new JButton[moviecount][movieTime]; // movieTime : 영화당 상영횟수
        movietableUpperpanel = new JLabel[moviecount];

        // 상영 시간 작성
//        moviesTimesString = new String[moviecount][movieTime];
//        moviesTimesString =
        for (int i = 0; i < movienames.length; i++) {
            movienames[i] = startEmpty + movienames[i];
            movieInfo[i] = movieInfo[i];
        }
        for (int i = 0; i < moviecount; i++) {
            timeTableSetArr[i] = new JPanel();
            movietableUpperpanel[i] = new JLabel();
            movieNamepanel[i] = new JLabel(movienames[i]);
            movieInfopanel[i] = new JLabel(movieInfo[i]);
            movieTimespanel[i] = new JPanel();
            for (int j = 0; j < movieTime; j++) {
                // 라벨 안의 매개변수가 나중에 String 으로 정의되어야함
                movieTimeEach[i][j] = new JButton(moviesTimesString[i][j]);
                Font buttonFont = movieTimeEach[i][j].getFont();
                movieTimeEach[i][j].setFont(new Font(buttonFont.getName(), Font.BOLD, 18)); // 글씨 크기 변경
                movieTimeEach[i][j].setBackground(new Color(194, 228, 184));
                border = new LineBorder(borderColor, thickness);
                movieTimeEach[i][j].setBorder(border);
            }
        }

        motherScrollpanel.setLayout(new GridLayout(0, 1, 20, 20));
        motherScrollpanel.setBackground(new Color(238, 238, 238));
        motherScrollpanel.setSize(605, 0);

        // 레이아웃

        for (int i = 0; i < moviecount; i++) {
            timeTableSetArr[i].add(movietableUpperpanel[i]);
            movietableUpperpanel[i].setLayout(new BorderLayout());
            motherScrollpanel.add(timeTableSetArr[i]);
//			timeTableSetArr[i].setLayout(new GridLayout(0, 1, 1, 1));
            timeTableSetArr[i].setLayout(new GridLayout(0, 1, 1, 1));
            movietableUpperpanel[i].setBackground(Color.cyan);
            movietableUpperpanel[i].setPreferredSize(null);
            timeTableSetArr[i].setPreferredSize(null);
            movieNamepanel[i].setPreferredSize(null);
            movieTimespanel[i].setPreferredSize(null);
            movietableUpperpanel[i].add(movieNamepanel[i], BorderLayout.WEST);
            movietableUpperpanel[i].add(movieInfopanel[i], BorderLayout.EAST);
            movieNamepanel[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            movieInfopanel[i].setAlignmentX(Component.RIGHT_ALIGNMENT);
            timeTableSetArr[i].add(movieTimespanel[i]);
            movieTimespanel[i].setLayout(new GridLayout(1, 0, 1, 1));
            for (int j = 0; j < movieTime; j++) {
                movieTimespanel[i].add(movieTimeEach[i][j]);
            }
        }
        motherScrollpanel.revalidate();
        motherScrollpanel.repaint();

        // 스크롤 패널 부착

        timeTablescrollPane = new JScrollPane(motherScrollpanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(timeTablescrollPane, BorderLayout.CENTER);

        fp.add(this);
    }

    public void setEvent() {

        for (int i = 0; i < moviecount; i++) {
            for (int j = 0; j < movieTime; j++) {
                movieTimeEach[i][j].addActionListener(movieeachtimelistener);

                Font labelFont = movieNamepanel[i].getFont();
                movieNamepanel[i].setFont(new Font(labelFont.getName(), Font.BOLD | Font.ITALIC, 20));
                Font labelFont2 = movieInfopanel[i].getFont();
                movieInfopanel[i].setFont(new Font(labelFont2.getName(), Font.BOLD, 17));

            }
        }

    }

    public void sethowmanyseatpopup() {

//		 new JDialog(f,"자리수 설정",);
        ticketCountDialog = new JDialog(origin.f, "예매할 티켓 수", true);
        ticketCountpanel = new JPanel();
        JPanel design2panel = new JPanel(new FlowLayout(1, 15, 15));
        design2panel.add(ticketCountpanel);
        ticketCountpanel.setLayout(new FlowLayout(1, 15, 0));
        ageInfo = new JLabel("<html>청소년 : 18세이하 <br>어린이 : 7세이하<br>");
        comboBoxforAdult = new JComboBox<Integer>(howManyticket);
        comboBoxforTeen = new JComboBox<Integer>(howManyticket);
        comboBoxforKids = new JComboBox<Integer>(howManyticket);

        ageChargeTableArr = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            ageChargeTableArr[i] = new JLabel(ageGroupArr[i] + " : " + agePriceArr[i] + "원");
        }
        OKdialog = new JButton("선택 완료");
        OKdialog.addActionListener(combocomfirmlistener);

        ticketCountpanel.add(ageChargeTableArr[0]);
        ticketCountpanel.add(comboBoxforAdult);
        ticketCountpanel.add(ageChargeTableArr[1]);
        ticketCountpanel.add(comboBoxforTeen);
        ticketCountpanel.add(ageChargeTableArr[2]);
        ticketCountpanel.add(comboBoxforKids);
        ticketCountpanel.add(ageInfo);
        ticketCountpanel.add(OKdialog);
        ticketCountpanel.add(Box.createVerticalStrut(10));
        ticketCountDialog.add(design2panel);
        ticketCountDialog.pack();
        ticketCountDialog.setLocationRelativeTo(origin.f);
        ticketCountDialog.setVisible(true);
    }

    ActionListener movieeachtimelistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            for (int i = 0; i < moviecount; i++) {
                for (int j = 0; j < movieTime; j++) {
                    if (movieTimeEach[i][j].equals(b)) {
                        System.out.println(i + " " + j);
                        origin.title = origin.movienames[i];
                        origin.time = origin.getMonthDay() + b.getText();
                        origin.roomNum = (i + 1) + "관";
                        origin.roomRunningTimeTableidx = i * moviecount + j;
                        System.out.println(origin.roomRunningTimeTableidx + "  1");

                        System.out.println(i + " " + j);
                        break;
                    }
                }
                // movietime
            }
            sethowmanyseatpopup();
        }
    };

    ActionListener combocomfirmlistener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("이버튼이눌림");
            // 어른 총원
            selectedAdultValue = (int) comboBoxforAdult.getSelectedItem();
            // 청소년 총원
            selectedTeenValue = (int) comboBoxforTeen.getSelectedItem();
            // 어린이 총원
            selectedKidValue = (int) comboBoxforKids.getSelectedItem();

            // 가격 정보 저장
            origin.price = String.valueOf((selectedAdultValue * agePriceArr[0]) + (selectedTeenValue * agePriceArr[1])
                    + (selectedKidValue * agePriceArr[2]));

            System.out.println(selectedAdultValue);
            System.out.println(selectedTeenValue);
            System.out.println(selectedKidValue);
            origin.selectableTotalNum = selectedAdultValue + selectedTeenValue + selectedKidValue;

            ticketCountDialog.dispose();
            ticketCountpanel = null;

            Seat seat = new Seat(origin, fp);
            fp.remove(fp.getComponent(0));
        }
    };
}
