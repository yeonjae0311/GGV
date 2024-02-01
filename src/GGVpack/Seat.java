package GGVpack;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class Seat extends JPanel{

    public JPanel fp;
    public GGV origin;

    public JPanel seatGrouppanel;
    public JButton seats[][];

    public JPanel seatsFunc_btnpanel;
    public JPanel screen;
    public JLabel screenLabel;
    public JButton select_btn;
    public JButton reset_btn;

    public JLabel theater_warning;

    // 좌석 수 설정
    public int seatsrows = 10; // 행의 수
    public int seatscols = 8; // 열의 수



    public Seat(GGV origin,JPanel fp){
        this.origin = origin;
        this.fp=fp;
        buildGUI();
        setEvent();
    }

    public String getSelectedSeatString(){
        //origin.selectedSeatsStrings 이것이 List<String>임
        String SeatString = "";
        for(int i=0;i<origin.selectedSeatsStrings.size();i++){
            SeatString+= origin.selectedSeatsStrings.get(i);
            if(i!=origin.selectedSeatsStrings.size()-1){//마지막 일때는 ,를붙이지 않기위한 예외처리
                SeatString+=", ";
            }
        }
        return SeatString;
    }
    // 좌석 고르는 메서드
    ActionListener seat_btnlistener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();

            if (b.getBackground() == Color.blue) {
                b.setBackground(new Color(220, 220, 220));
                origin.selectedSeatsStrings.remove(b.getText());
                origin.selectableTotalNum++;
            }else if (origin.selectableTotalNum == 0) {
                JOptionPane.showMessageDialog(null, "자리를 더 이상 고를 수 없습니다.");
                return;
            } else if (origin.selectableTotalNum > 0) {
                (origin.selectableTotalNum)--;
                origin.selectedSeatsStrings.add(b.getText());
                b.setBackground(Color.blue);//내가 선택한 좌석은 일시적으로 파란색이됨
                //내 리스트에 추가해야되고
                // 이어서 코드필요함
            } else {
                System.out.println("4");
                System.out.println("selectabletotalnum이 음수인 에러");
            }
            theater_warning.setText("선택된 좌석 : "+getSelectedSeatString());
            revalidate();
            repaint();
        }
    };

    // (유저명) | (예약좌석) 을 모아놓는 txt에 기록하는 코드
    public void addSeatBooked(){

        try {
            FileWriter fw = new FileWriter("src/db/UserSeatBook.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);

            String toBeWritenString = origin.loginedUsername;
            toBeWritenString += "|";

            toBeWritenString += ""+origin.roomRunningTimeTableidx;

            toBeWritenString += "|";

            toBeWritenString += getSelectedSeatString();

            toBeWritenString += "\n";

            System.out.println("here toBeWritenString : "+toBeWritenString);

            bw.write(toBeWritenString);

            bw.close();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // (유저명) | (예약좌석) 을 모아놓는 txt에 기록하는 코드
    public int seatstoidx(String seatstring){
        int idx =(seatstring.charAt(0)- 'A')*8+(seatstring.charAt(1)-'0'-1);
        return  idx;
    }
    public void bookrenewal(){
        ArrayList<Integer> justsoldidxlist= new ArrayList<>();
        for(int i=0; i<origin.selectedSeatsStrings.size();i++){
            int idx = seatstoidx(origin.selectedSeatsStrings.get(i));
            System.out.print(idx+" ");
            origin.seatmap.put(idx,true);
            justsoldidxlist.add(idx);
            //이쪽의 인덱스값의 범위는 0~79임
        }
        Collections.sort(justsoldidxlist);
//        for(int a : justsoldidxlist){
//            System.out.print(a+" ");
//        }
//        System.out.println();
        String newMadeBookedString="{";
        newMadeBookedString+=origin.roomRunningTimeTableidx;
        newMadeBookedString+=":";
        for(int i=0;i<origin.seatmap.size();i++){
            if(origin.seatmap.get(i)) {
                newMadeBookedString += true;
                newMadeBookedString += "|";

            }else{
                newMadeBookedString += false;
                newMadeBookedString += "|";
            }
        }
        newMadeBookedString += "},";
        origin.seatsDummyStringList.add(origin.roomRunningTimeTableidx,newMadeBookedString);
//        System.out.println("내가 원하는 출력문");
//        System.out.println(newMadeBookedString);
//        for(String s :origin.seatsDummyStringList ){
//            System.out.println(s);
//        }

        try {

            FileWriter fw = new FileWriter("src/db/Booked.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s :origin.seatsDummyStringList ){
                System.out.println(s);
                bw.write(s);
                bw.write("\n");
            }

            bw.close();
            fw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        addSeatBooked();
    }

    ActionListener seatsFunc_btnlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if (b.getText().equals("선택 완료")) {
                if (origin.selectableTotalNum == 0) {//변경사항 N자리 구했다면 N자리를 골라야만 좌석완료가능
                    System.out.println(b.getText() + "버튼이 눌림" + "모든 좌석 선택완료");
                    origin.seatNo = origin.setSeatNo();


                    bookrenewal();
                    //여기에 Booked.txt 수정코드필요
                    MoviePayment moviePayment = new MoviePayment(fp, origin);
                    fp.remove(fp.getComponent(0));

                } else {
                    JOptionPane.showMessageDialog(null, "아직" + origin.selectableTotalNum + "자리를 선택하지 않았습니다.");
                    return;
                }

            } else if (b.getText().equals("다시 고르기")) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < 8; i++) {
                        if (seats[j][i].getBackground() == Color.blue) {
                            System.out.println("다시고르기의 if가 참");
                            seats[j][i].setBackground(new Color(220,220,220));
                            origin.selectableTotalNum++;
                            origin.selectedSeatsStrings.remove(seats[j][i].getText());
                        }
                    }
                }

            } else {
                System.out.println("정의되지않은 예외처리");
            }

            theater_warning.setText("선택된 좌석 : "+getSelectedSeatString());
        }
    };

    public void buildGUI(){
        System.out.println("영화관" + origin.roomRunningTimeTableidx + " 입장");

        //몇관 인지를 검사해서 HashMap 제작 + 이용가능한 자리와 이용불가능한 자리를 빈값에 채워넣어줌
        origin.readgetSeat(origin.roomRunningTimeTableidx);
        // 페이지 전체 색상 설정
        Color theaterColor = new Color(0x5A88F5);
        setBackground(theaterColor);
        setLayout(new FlowLayout());

        // 스크린 위치
        screen = new JPanel();
        screen.setPreferredSize(new Dimension(590, 30));
        screenLabel = new JLabel("SCREEN");
        screenLabel.setForeground(new Color(100, 98, 98));
        screen.add(screenLabel);
        screen.setBackground(Color.BLACK);
        add(screen);

        // 좌석이 올라가는 패널
        seatGrouppanel = new JPanel();
        add(seatGrouppanel);
        seatGrouppanel.setBackground(theaterColor);

        // 좌석 패널 사이즈
        seatGrouppanel.setPreferredSize(new Dimension(470, 650));
        seatGrouppanel.setLayout(new GridLayout(seatsrows, seatscols, 10, 10));
        seats = new JButton[seatsrows][seatscols];

        // 맵함수 작동
        // 앞에 있는 roomnumber을 이용해 map을 작성한다.


        for (int j = 0; j < seatsrows; j++) { // j=0;이 A j=1이 B ...
            for (int i = 0; i < seatscols; i++) {
                seats[j][i] = new JButton("" + (char) ('A' + j) + (i + 1) + "");
//                seats[j][i].setUI(new BasicButtonUI());
                //여기에 좌석이 고를수있는지 아닌지에 따라서
                seats[j][i].setFocusPainted(false);
                //녹색 또는 빨간색으로 이미 출력이 되는쪽이 좋을듯
                if (origin.seatmap.get(j * seatscols + i) == true) {//true가사용중 //false가사용가능
                    System.out.print(true);
                    seats[j][i].setBackground(new Color(119,136,153));
                } else {
                    System.out.print(false);//false가 예매가능 , 예매가능한곳만 버튼리스너
                    seats[j][i].setBackground(new Color(220,220,220));
                }
                //seats[j][i].setBackground(new Color(j * 8 + i, j * 8 + i, j * 8 + i));
                //seats[j][i].setForeground(new Color(255 - (j * 8 + i), 255 - (j * 8 + i), 255 - (j * 8 + i)));
                // 액션리스너 달아줘야하고

                seatGrouppanel.add(seats[j][i]);
            }
        }

        // 리셋, 선택 완료 버튼용 패널

        seatsFunc_btnpanel = new JPanel();

        seatsFunc_btnpanel.setLayout(new FlowLayout(1, 40, 15));
        seatsFunc_btnpanel.setBackground(theaterColor);

        select_btn = new JButton("선택 완료");

        reset_btn = new JButton("다시 고르기");

        seatsFunc_btnpanel.add(select_btn);
        seatsFunc_btnpanel.add(reset_btn);

        theater_warning = new JLabel("선택된 좌석 : ");
        theater_warning.setSize(200, 40);
        add(Box.createRigidArea(new Dimension(605, 1)));
        add(theater_warning);
        add(Box.createRigidArea(new Dimension(605, 1)));
        add(seatsFunc_btnpanel);

        fp.add(this);
    }

    public void setEvent() {
        select_btn.addActionListener(seatsFunc_btnlistener);
        reset_btn.addActionListener(seatsFunc_btnlistener);

        for (int j = 0; j < seatsrows; j++) { // j=0;이 A j=1이 B ...
            for (int i = 0; i < seatscols; i++) {
                if (origin.seatmap.get(j * seatscols + i) == true) {
                } else {
                    seats[j][i].addActionListener(seat_btnlistener);
                }
            }
        }
    }
}
