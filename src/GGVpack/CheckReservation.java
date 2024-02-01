package GGVpack;

import static java.awt.Component.CENTER_ALIGNMENT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CheckReservation extends JPanel {

	public GGV origin;
	public JPanel fp;

	// 영화
	public String title;

	// 시간
	public String time;

	// 상영관
	public String roomNum;

	// 선택된 좌석
	public String seatNo;

	// 스낵 이름
	public String[] snackN;
	String[] menuNames;

	// 스낵 개수
	int[] num;

	Map<Integer,String> userSeatBookedMap = new HashMap<>();

	public int snackNum = 10;

	// 메인 가는 버튼
	public JButton gotoMain_btn = new JButton("메인으로");

	public CheckReservation(JPanel fp, GGV origin) {
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

		// 좌석 정보
		JLabel sNum = new JLabel("<html><body><center>좌석 : " + seatNo + "</center></body></html>");
		sNum.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		sNum.setBounds(170, 200, 250, 50);

		// 아래 구분 줄
		JLabel line02 = new JLabel("==============================");
		line02.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line02.setBounds(120, 250, 400, 50);

		// 매점
		// 구매 목록
		JLabel buyInfolabel = new JLabel("매점 구매 내역");
		buyInfolabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		buyInfolabel.setBounds(230, 295, 400, 50);

		// 품목 위 구분 줄
		JLabel line03 = new JLabel("==============================");
		line03.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line03.setBounds(120, 320, 400, 50);

		// 고른거
		JPanel selectSnackpanel = new JPanel(new GridLayout(6, 1, 3, 3));
		JPanel selectSnackNumpanel = new JPanel(new GridLayout(6, 1, 3, 3));

		selectSnackpanel.setBounds(130, 360, 150, 150);
		selectSnackNumpanel.setBounds(300, 360, 50, 150);
		JLabel[] selectSancklabel = new JLabel[snackN.length];
		JLabel[] selectSanckNumlabel = new JLabel[num.length];

		for (int i = 0; i < snackN.length; i++) {
			if (snackN[i] != null) {
				selectSancklabel[i] = new JLabel(snackN[i]);
				selectSancklabel[i].setFont(new Font("맑은 고딕", Font.BOLD, 15));
				selectSancklabel[i].setHorizontalAlignment(JLabel.LEFT);
				selectSnackpanel.add(selectSancklabel[i]);

				if (num[i] != 0) {
					selectSanckNumlabel[i] = new JLabel(num[i] + "개");
					selectSanckNumlabel[i].setFont(new Font("맑은 고딕", Font.BOLD, 15));
					selectSanckNumlabel[i].setHorizontalAlignment(JLabel.LEFT);
					selectSnackNumpanel.add(selectSanckNumlabel[i]);
				}
			}
		}
		selectSnackpanel.setBackground(Color.LIGHT_GRAY);
		selectSnackNumpanel.setBackground(Color.LIGHT_GRAY);

		// 수량 넣을거면 위랑 똑같이 가로만 옮겨서 넣기
		JLabel totlalabel = new JLabel("총 : " + getTotal() + "개");
		totlalabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		totlalabel.setBounds(400, 470, 80, 50);

		// 품목 아래 구분 줄
		JLabel line04 = new JLabel("==============================");
		line04.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line04.setBounds(120, 500, 400, 50);

		// 메인으로 버튼
		gotoMain_btn.setBackground(Color.GRAY);
		gotoMain_btn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		gotoMain_btn.setBounds(185, 650, 190, 60);

		add(line01);
		add(movieTpanel);
		add(mtime);
		add(mNum);
		add(sNum);
		add(line02);
		add(buyInfolabel);
		add(line03);
		add(selectSnackpanel);
		add(selectSnackNumpanel);
		add(totlalabel);
		add(line04);
		add(gotoMain_btn);
		fp.add(this);
	}

	public void readSeatBooked(){
		try {
			FileReader fr = new FileReader("src/db/UserSeatBook.txt");
			BufferedReader br = new BufferedReader(fr);
			String readline="";

			int idx=0;
			while((readline=br.readLine())!=null){
				userSeatBookedMap.put(idx++,readline);
			}

			br.close();
			fr.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public Boolean isIdInString(String parsedString){
		System.out.println("3");

		//아이디부분만 찾아내어
		int idxbar = parsedString.indexOf("|");
		if(idxbar==-1) return false;
		String paredid = parsedString.substring(0,idxbar);

		System.out.println(paredid);
		//비교후 같으면 예약된자리로 세팅을해줌 true반환
		if(paredid.equals(origin.loginedUsername)){
			//이쪽에 각종 자료들을 창조해야함
			String theaterNseatno = parsedString.substring(idxbar+1);
			idxbar = theaterNseatno.indexOf("|");

			int thearter = Integer.parseInt(theaterNseatno.substring(0,idxbar));
			String seatno = theaterNseatno.substring(idxbar+1);

			int j = thearter/4;
			int i = thearter%4;

			origin.title = origin.movienames[j];

			origin.roomNum = (j+1)+"관";
			origin.time = origin.getMonthDay()+origin.moviesTimesString[j][i];

			origin.seatNo=seatno;

			System.out.println(seatno);

			System.out.println("4");
			return true;
		}
		System.out.println("5");
		return false;
	}
	public void setSeatNo(){
		System.out.println("1");
		if(userSeatBookedMap==null) return;
		System.out.println("2");
		for(int i=0;i<userSeatBookedMap.size();i++){
			if(isIdInString(userSeatBookedMap.get(i))){
				break;
			}else continue;
		}
		//while문돌면서 검사하다가
		//참인값나오면 
		
	}

	// 값 지정
	public void setResult() {

		readSeatBooked();
		setSeatNo(); //여기에서 origin.seatNo를 바꿔주고 아래코드에서 그값을 가져오는 방식을채택
		setName();
		this.title = origin.title;
		this.time = origin.time;
		this.roomNum = origin.roomNum;
		if (origin.seatNo != null) {
			this.seatNo = origin.seatNo;
		} else if (origin.seatNo==null) {
			this.seatNo = "좌석 정보 없음";
		}
	}

	// 개수 구하는 메서드
	private int getTotal() {
		int sum = 0;
		if (origin.countmap != null) {
			for (int i = 0; i < 6; i++) {
				sum += origin.countmap.getOrDefault(i, 0);
			}
		}
		return sum;
	}
	public Boolean isIdInString2(String readline){
		int idxbar = readline.indexOf("|");
		String id = readline.substring(0,idxbar);
		System.out.println("id: "+id);
		if(id.equals(origin.loginedUsername)){
			origin.countmap.clear();
			String leftstr = readline.substring(idxbar+1);
			parseLeftString(leftstr);
			return true;
		}
		else{
			return false;
		}
	}

	public void parseLeftString(String leftstr){
		if(leftstr.length()<=3) return;

		int idxbar = leftstr.indexOf("|");
		int idxbar2 = leftstr.indexOf("|",idxbar+1);

		int mapIndex = Integer.parseInt(leftstr.substring(0,idxbar));
		int mapValue = Integer.parseInt(leftstr.substring(idxbar+1,idxbar2));

		origin.countmap.put(mapIndex,mapValue);
		System.out.println("origin.countmap.size() "+origin.countmap.size());
		if(idxbar2!=leftstr.length()-1){
			parseLeftString(leftstr.substring(idxbar2+1));
		}
	}

	public void setOriginCountMap(){
		try {
			FileReader fr = new FileReader("src/db/UserSnackBook.txt");
			BufferedReader br = new BufferedReader(fr);
			String readline;
			while ((readline = br.readLine())!=null){
				if(isIdInString2(readline)){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 라벨에 넣을 값들을 이클래스로 가져오는 코드
	public void setName() {
		num = new int[6];
		snackN = new String[6];

		//여기에서 origin.countmap에 을만들어줘야함
		setOriginCountMap();

		if (origin.countmap != null) {
			menuNames = new String[] { "고소팝콘(M)", "달콤팝콘(M)", "더블콤보원", "스몰세트", "칠리치즈나쵸", "칠리치즈핫도그" };
			for (int i = 0; i < 6; i++) {
				if (origin.countmap.containsKey(i)) {
					this.snackN[i] = menuNames[i];
					this.num[i] = origin.countmap.get(i);
				}
			}
		} else if (origin.countmap == null) {
			this.snackN[0] = "구매 내역 없음";
		}
	}

	public void setEvent() {

		gotoMain_btn.addActionListener(gotoMainListener);

	}

	ActionListener gotoMainListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			String textOnButton = btn.getText();
			if (textOnButton == "메인으로") {
				MovieList movieList = new MovieList(fp, origin);
				fp.remove(fp.getComponent(0));
			}
			// movielist객체가 필요함
		}
	};

}
