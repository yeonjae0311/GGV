package GGVpack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.*;

public class MovieList extends JPanel {

	GGV origin;
	JPanel fp;
	public JPanel upperpanel;
	public JButton reservconfirm;
	public JButton gotoreserv_btn;
	public JButton gotobuysnake_btn;
	public JPanel[] movies;
	public String[] imagePaths;
	public JPanel[] empty;
	public int moviecount=4;
	public JPanel spanninglpanel;
	public JScrollPane lowerscpanel;


	public JPanel moviepanel;
	public JLabel movieImageLabel[];
	public String movieExplain[] ={"“나는 이제 죽음이요, 세상의 파괴자가 되었다.”\n" +
			"세상을 구하기 위해 세상을 파괴할 지도 모르는 선택을 해야 하는 천재 과학자의 핵개발 프로젝트.",

			"디즈니·픽사의 놀라운 상상력!\n" +
					"올여름, 세상이 살아 숨 쉰다\n" +
					"\n" +
					"불, 물, 공기, 흙 4개의 원소들이 살고 있는 ‘엘리멘트 시티’\n" +
					"재치 있고 불처럼 열정 넘치는 ‘앰버'는 어느 날 우연히\n" +
					"유쾌하고 감성적이며 물 흐르듯 사는 '웨이드'를 만나 특별한 우정을 쌓으며,",

			"어둠의 세력이 더욱 강력해져 머글 세계와 호그와트까지 위협해온다.\n" +
					"위험한 기운을 감지한 덤블도어 교수는 다가올 전투에 대비하기 위해 해리 포터와 함께 대장정의 길을 나선다.\n" +
					"볼드모트를 물리칠 수 있는 유일한 단서이자 그의 영혼을 나누어 놓은 7개의 호크룩스를 파괴하는 미션을 수행해야만 하는 것!\n" +
					"또한 덤블도어 교수는 호크룩스를 찾는 기억여행에 결정적 도움을 줄 슬러그혼 교수를 호그와트로 초청한다.\n" +
					"한편 학교에서는 계속된 수업과 함께 로맨스의 기운도 무르익는다.\n" +
					"해리는 자신도 모르게 지니에게 점점 끌리게 되고,\n" +
					"새로운 여자 친구가 생긴 론에게 헤르미온느는 묘한 질투심을 느끼는데...\n" +
					"\n" +
					"남겨진 결전을 위한 최후의 미션,\n" +
					"볼드모트와 해리 포터에 얽힌 치명적인 비밀,\n" +
					"선택된 자만이 통과할 수 있는 대단원을 향한 본격적인 대결이 시작된다!",

			"전국 제패를 꿈꾸는 북산고 농구부 5인방의 꿈과 열정, 멈추지 않는 도전을 그린 영화"
	};

	public void checkSeatBookAndSet2(){
		try {
			FileReader fr2 = new FileReader("src/db/UserSnackBook.txt");
			BufferedReader br2 = new BufferedReader(fr2);
			String readline2;

			while ((readline2=br2.readLine())!=null){
				if(getIdfromSeatBook(readline2).equals(origin.loginedUsername)){
					gotobuysnake_btn.setEnabled(false);
					break;
				}
			}

			br2.close();
			fr2.close();

		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void checkSeatBookAndSet(){
		try {
			FileReader fr = new FileReader("src/db/UserSeatBook.txt");
			BufferedReader br = new BufferedReader(fr);
			String readline;
			//
			//두개의 불린값필요
			while ((readline=br.readLine())!=null){
				if(getIdfromSeatBook(readline).equals(origin.loginedUsername)){
					gotoreserv_btn.setEnabled(false);
					break;
				}
			}
			br.close();
			fr.close();
			revalidate();
			repaint();

		}catch (Exception e){

		}
	}

	public String getIdfromSeatBook(String str){
		int idxbar = str.indexOf("|");
		String id =  str.substring(0,idxbar);
		return id;
	}

	ActionListener loginedbtns = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			String textOnButton = btn.getText();
			switch (textOnButton) {
				case "예매하기":
				MovieTimeTable movieTimeTable = new MovieTimeTable(origin,fp);
				fp.remove(fp.getComponent(0));
				break;
				case "예매확인":
					CheckReservation checkReservation = new CheckReservation(fp, origin);
					fp.remove(fp.getComponent(0));
					break;
				case "스낵코너":
					Snack snack = new Snack(fp,origin);
					fp.remove(fp.getComponent(0));
					break;
			}
			// TODO Auto-generated method stub
		}
	};

	MouseAdapter selectedProcess = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
		JLabel label = (JLabel)e.getSource();
		String tosend ="";
		for(int i=0;i<moviecount;i++){
			if(origin.movienames[i].equals(label.getName())){
				tosend=movieExplain[i];
				JOptionPane.showMessageDialog(null, tosend);
				break;
			}else{
				System.out.println("에러");
			}
		}
		}
	};

	public void buildGUI() {
		setSize(origin.size);

		setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout());

		upperpanel = new JPanel(new FlowLayout());
		upperpanel.setSize(new Dimension(605, 100));
		upperpanel.setBackground(new Color(000, 000, 102)); // 상단 패널 색
		Dimension btnsize = new Dimension(100, 33);

		gotobuysnake_btn = new JButton("스낵코너");
		gotoreserv_btn = new JButton("예매하기");
		reservconfirm = new JButton("예매확인");

		reservconfirm.setPreferredSize(btnsize);
		gotoreserv_btn.setPreferredSize(btnsize);
		gotobuysnake_btn.setPreferredSize(btnsize);

		spanninglpanel= new JPanel(new FlowLayout(1, 0, 50));
		upperpanel.add(spanninglpanel);
		upperpanel.add(reservconfirm);
		upperpanel.add(gotoreserv_btn);
		upperpanel.add(gotobuysnake_btn);
		upperpanel.add(spanninglpanel); // 스크롤판에 추가작업 필요함

		moviepanel = new JPanel();

		movies = new JPanel[moviecount];
		empty = new JPanel[moviecount];

		moviepanel.setLayout(new BoxLayout(moviepanel, BoxLayout.Y_AXIS));
		moviepanel.setPreferredSize(new Dimension(605, 0));

		imagePaths = new String[]{
				"src/images/Movie/오펜하이머.jpg",
				"src/images/Movie/엘리멘탈.jpg",
				"src/images/Movie/해리포터와혼혈왕자.jpg",
				"src/images/Movie/더퍼스트슬램덩크.jpg"
		};

		movieImageLabel = new JLabel[moviecount];


		for (int i = 0; i < moviecount; i++) {
			int movieheight = 300;

			movies[i] = new JPanel();
			empty[i] = new JPanel();
			movieImageLabel[i] = new JLabel(new Resizing().resizingImg(imagePaths[i],605,movieheight));
			movieImageLabel[i].addMouseListener(selectedProcess);
			movieImageLabel[i].setName(origin.movienames[i]);
//			movieImageLabel[i].setBackground(Color.LIGHT_GRAY); // 라벨
			empty[i].setBackground(Color.LIGHT_GRAY); // 배경색 light gray
			movies[i].add(movieImageLabel[i]);

			Dimension adsize = new Dimension(605, movieheight);
			Dimension emptysize = new Dimension(605, 40);
//			movies[i].setBackground(new Color(255, 0, 0));
//			empty[i].setBackground(new Color(255, 255, 255));
			empty[i].setBackground(new Color(204, 204, 204)); // 배경색 light gray
			movies[i].setPreferredSize(adsize);
			empty[i].setPreferredSize(emptysize);

			moviepanel.add(movies[i]);
			moviepanel.add(empty[i]);

			int newHeight = moviepanel.getPreferredSize().height + adsize.height + emptysize.height;
			moviepanel.setPreferredSize(new Dimension(605, newHeight));
		}

		lowerscpanel = new JScrollPane(moviepanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		add(upperpanel, BorderLayout.NORTH);
		add(lowerscpanel, BorderLayout.CENTER);
		revalidate();
		repaint();

		fp.add(this);
	}

	public void setEvent() {
		reservconfirm.addActionListener(loginedbtns);
		gotoreserv_btn.addActionListener(loginedbtns);
		gotobuysnake_btn.addActionListener(loginedbtns);
	}

	public MovieList(JPanel fp, GGV origin) {
		this.origin = origin;
		this.fp = fp;
		buildGUI();
		setEvent();
		checkSeatBookAndSet();
		checkSeatBookAndSet2();
	}
}
