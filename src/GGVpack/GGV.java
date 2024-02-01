package GGVpack;

import static java.awt.Component.CENTER_ALIGNMENT;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GGV {
    
    // 기본 프레임

    JFrame f;
    Dimension size = new Dimension(605, 864);
    Scanner sc = new Scanner(System.in);
    String movienames[] = { "오펜하이머", "엘리멘탈", "해리포터와혼혈왕자", "더퍼스트슬램덩크"};



    // 기본 패널
    JPanel fp;
    CardLayout card = new CardLayout(10, 10);

    // 로그인 패널 변수

    String loginedUsername ="";
    Boolean islogined = false;

    // 결제 페이지 변수'
    String title = "예매 내역 없음";
    String time;
    String roomNum;
    String price;
    String seatNo = "좌석 정보 없음";


    // 영화 시간 선택 + 예매 페이지

    JButton movieTimeEach[][]; // 정해야할거 날짜바뀌면?
    //"오펜하이머", "엘리멘탈", "해리포터와혼혈왕자", "더퍼스트슬램덩크"};
    String moviesTimesString[][]= {
            {"07:00~10:10","11:05~14:15","16:40~19:50","20:15~23:25"},
            {"09:30~11:29","12:05~14:04","16:30~18:29","17:00~18:59"},
            {"10:30~13:13","13:30~16:13","16:30~19:13","19:30~22:13"},
            {"08:00~10:14","11:15~14:15","15:20~17:34","18:00~20:14"}
    };
    int roomRunningTimeTableidx; //0~15

    // dialog 완료 버튼

    int selectableTotalNum;

    // 좌석 선택 페이지
    Map<Integer, Boolean> seatmap;// 주인 있는 좌석은 true //이용가능한 좌석은 false
    java.util.List<String> selectedSeatsStrings = new ArrayList<>();

    public ArrayList<String> seatsDummyStringList= new ArrayList<>();;
    
    Map<Integer,Integer> countmap=new HashMap<>();
    ArrayList<JPanel> miniShoppingPanelList;


    public void readgetSeat(int roomnum) {  // Seat
    	System.out.println("파일 읽기 시도");
    	File file = new File("src/Booked.txt");
    	FileReader fr;
    	BufferedReader br;
        seatsDummyStringList.clear();
		try {
			fr = new FileReader(file);
	    	br = new BufferedReader(fr);
	    	String readstr;
	    	while(( readstr = br.readLine())!=null) {
//	    		System.out.println(readstr);
	    		if(isNthTheaterString(readstr,roomnum)) {
	    			System.out.println(readstr);
	    			System.out.println(getCountAvailable(readstr));
	    			makeBooleanSeatMap(readstr);
//	    			System.out.println();
//	    			testshowmap(); 단순 정상작동 확인용 출력문더미를 주석
//	    			System.out.println();
//	    			System.out.println(readstr);
	    		}else{
                    seatsDummyStringList.add(readstr);
                }
	    	}
	    	br.close();
	    	fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // 혹시 나중에 다른맵이 생길수도 잇으니 불린시트맵이라고 정함
    public void makeBooleanSeatMap(String str) { // readgetSeat
        seatmap = new HashMap<Integer, Boolean>();// true가 사용중 //false 사용가능
        int total = 0;
        int idxfrom = str.indexOf(":");
        int idxto = str.indexOf("|");
        while (total != 80) {
            String temp = "";
            if (idxto != -1) {
                temp = str.substring(idxfrom + 1, idxto);
            } else {
                temp = str.substring(idxfrom + 1);
            }
            System.out.println(temp);
            seatmap.put(total++, Boolean.parseBoolean(temp));
            idxfrom = idxto;
            idxto = str.indexOf("|", idxfrom + 1);
            System.out.println(seatmap.size());
        }
    }

    public Boolean isNthTheaterString(String str,int a) { // readgetSeat
        String stringeda = String.valueOf(a);

        String searchstr = "{" + stringeda + ":";
        if (str.contains(searchstr)) {
            System.out.println(searchstr);
            return true;
        }
        return false;
    }

    public int getCountAvailable(String parsedString) { // readgetSeat
        int idx = 0;
        int falsecount = 0; // false 한개당 1자리의 여유가 잇는거임
        while (idx != -1) {
//    		System.out.println(idx);
            if (parsedString.indexOf("false", idx + 1) != -1) {
                idx = parsedString.indexOf("false", idx + 1) + 1;
                falsecount++;
            } else {
                return falsecount;
            }
        }
        return falsecount;
    }


    public GGV() { // 생성자 초입

        f = new JFrame("GGV");
        setmain();
        Login login = new Login(fp, this);

    }

    public String setSeatNo() {
        String fullstring = "";
        for (int i = 0; i < selectedSeatsStrings.size(); i++) {
            if (i != selectedSeatsStrings.size() - 1) {
                fullstring += selectedSeatsStrings.get(i) + ", ";
            } else {
                fullstring += selectedSeatsStrings.get(i);
            }
        }
        return fullstring;
    }  // Seat

    public String getMonthDay() {
        Date currentDate = new Date();

        // 월과 일을 가져오기 위해 SimpleDateFormat을 사용합니다.
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        String currentMonth = monthFormat.format(currentDate);
        String currentDay = dayFormat.format(currentDate);

        return currentMonth + "월 " + currentDay + "일 ";
    }  // MovieTimeTable

    // 예매하려는 상영시간 선택하는 기능 (-> 인원선택으로 넘어가게)

    // 기본 프레임 설정
    public void setmain() {
        fp = new JPanel();

        fp.setBackground(new Color(255, 255, 255));
        fp.setSize(size);
        fp.setLayout(card); // 카드 레이아웃 사용

        f.add(fp);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(size);
        f.setLocationRelativeTo(null);
        f.setLocation((int) f.getLocation().getX() - 300, (int) f.getLocation().getY());
        f.setVisible(true);
    }

}
