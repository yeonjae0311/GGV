package GGVpack;

import static java.awt.Component.CENTER_ALIGNMENT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class SnackPayment extends JPanel {
	GGV origin;
	public JPanel fp;

	// 스낵 이름
	public String[] snackN;
	String[] menuNames;

	int[] num;

	// 가격
	int[] menuPrices = { 5000, 6000, 13000, 7000, 4900, 5000 };

	// 결제 옵션 라디오 버튼
	public String[] paymentOptionArr = { "신용/체크카드", "계좌이체", "가상계좌", "네이버페이", "카카오페이", "토스" };
	public JRadioButton[] optionArr = new JRadioButton[paymentOptionArr.length];

	// 결제 버튼
	public JButton payment_btn = new JButton("결제");
	ButtonGroup btgroup;

	public SnackPayment(JPanel fp, GGV origin) {
		this.origin = origin;
		this.fp = fp;
		setName();
		buildGUI();
		setEvent();
	}

	public void buildGUI() {

		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		setBounds(10, 10, 605, 864);
		setVisible(true);

		JLabel line01 = new JLabel("==============================");
		line01.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line01.setBounds(120, 10, 400, 50);

		// 매점
		// 구매 목록
		JLabel buyInfolabel = new JLabel("장바구니");
		buyInfolabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		buyInfolabel.setBounds(250, 45, 400, 50);

		// 품목 위 구분 줄
		JLabel line02 = new JLabel("==============================");
		line02.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line02.setBounds(120, 70, 400, 50);

		// 고른거
		JPanel selectSnackpanel = new JPanel(new GridLayout(6, 1, 3, 3));
		JPanel selectSnackNumpanel = new JPanel(new GridLayout(6, 1, 3, 3));

		selectSnackpanel.setBounds(130, 110, 150, 150);
		selectSnackNumpanel.setBounds(300, 110, 50, 150);
		JLabel[] selectSancklabel = new JLabel[snackN.length];
		JLabel[] selectSanckNumlabel = new JLabel[num.length];
		
		for (int i = 0; i < snackN.length; i++) {
			if (snackN[i] != null) {
				selectSancklabel[i] = new JLabel(snackN[i]);
				selectSancklabel[i].setFont(new Font("맑은 고딕", Font.BOLD, 15));
				selectSancklabel[i].setHorizontalAlignment(JLabel.LEFT);
				selectSnackpanel.add(selectSancklabel[i]);
				
				selectSanckNumlabel[i] = new JLabel(num[i] + "개");
				selectSanckNumlabel[i].setFont(new Font("맑은 고딕", Font.BOLD, 15));
				selectSanckNumlabel[i].setHorizontalAlignment(JLabel.LEFT);
				selectSnackNumpanel.add(selectSanckNumlabel[i]);
			}
		}
		selectSnackpanel.setBackground(Color.LIGHT_GRAY);
		selectSnackNumpanel.setBackground(Color.LIGHT_GRAY);

		// 수량 넣을거면 위랑 똑같이 가로만 옮겨서 넣기
		JLabel totlalabel = new JLabel("총 : " + getTotal() + "개");
		totlalabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		totlalabel.setBounds(400, 220, 80, 50);

		// 품목 아래 구분 줄
		JLabel line03 = new JLabel("==============================");
		line03.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line03.setBounds(120, 250, 400, 50);

		// 결제 옵션
		JLabel chose = new JLabel("결제 방법 선택");
		chose.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		chose.setBounds(120, 275, 400, 50);

		// 버튼 위 구분 줄
		JLabel line04 = new JLabel("==============================");
		line04.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line04.setBounds(120, 300, 400, 50);

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

		optionArr[0].setSelected(true);   // 기본으로 신용체크카드 선택

		// 품목 아래 구분 줄
		JLabel line05 = new JLabel("==============================");
		line05.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line05.setBounds(120, 550, 400, 50);

		// 결제 가격
		JLabel pricelabel = new JLabel("결제 금액 : " + getSum() + "원");
		pricelabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		pricelabel.setBounds(270, 580, 400, 50);

		JLabel line06 = new JLabel("==============================");
		line06.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line06.setBounds(120, 610, 400, 50);

		// 결제 버튼
		payment_btn.setBackground(Color.GRAY);
		payment_btn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		payment_btn.setBounds(300, 655, 150, 50);

		JLabel line07 = new JLabel("==============================");
		line07.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		line07.setBounds(120, 695, 400, 50);

		add(line01);
		add(buyInfolabel);
		add(line02);
		add(selectSnackpanel);
		add(selectSnackNumpanel);
		add(totlalabel);
		add(line03);
		add(chose);
		add(line04);
		add(radiopanel);
		add(line05);
		add(pricelabel);
		add(line06);
		add(payment_btn);
		add(line07);

		fp.add(this);
	}

	public void setEvent() {

		payment_btn.addActionListener(payment_btnListener);

	}

	ActionListener payment_btnListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			String strFromrb = "";
			for (int i = 0; i < paymentOptionArr.length; i++) {
				if (optionArr[i].isSelected()) {
					strFromrb = paymentOptionArr[i];
					break;
				}
				// 여기 예외처리 넣을수있지만 일단안함
			}
			int result = JOptionPane.showConfirmDialog(null, // 중앙에 출력
					strFromrb + "로 " + getSum() + "원을 결제하시겠습니까?", // 메세지
					"결제", // 다이얼로그 제목
					JOptionPane.YES_NO_OPTION, 3);

			// OK 버튼 누르면
			if (result == 0) {
				addUserSnackRecord();
				JOptionPane.showMessageDialog( // 화면 중앙에서 메세지 출력
						null, "결제가 완료되었습니다!");
				MovieList movieList = new MovieList(fp, origin);
				fp.remove(fp.getComponent(0));
			}

			//
		}
	};
	public void addUserSnackRecord(){
		String towrite = "";
		String id = origin.loginedUsername;

		towrite += id+"|";
		for(int i=0;i<6;i++){
			if(origin.countmap.containsKey(i)){
				towrite += i + "|" + origin.countmap.get(i) +"|";
			}
		}
		System.out.println("towrite " + towrite);
		try {
			FileWriter fw = new FileWriter("src/db/UserSnackBook.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(towrite+"\n");

			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 개수 구하는 메서드
	private int getTotal() {
		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum += origin.countmap.getOrDefault(i, 0);
		}
		return sum;
	}

	// 가격 구하는 메서드
	private int getSum() {
		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum += menuPrices[i] * origin.countmap.getOrDefault(i, 0);
		}
		return sum;
	}

	public void setName() {
		num = new int[6];
		snackN= new String[6];
		menuNames = new String[] { "고소팝콘(M)", "달콤팝콘(M)", "더블콤보원", "스몰세트", "칠리치즈나쵸", "칠리치즈핫도그" };
		for (int i = 0; i < 6; i++) {
			if (origin.countmap.containsKey(i)) {
				this.snackN[i] = menuNames[i];
				this.num[i] = origin.countmap.get(i);
			}
		}
	}

}
