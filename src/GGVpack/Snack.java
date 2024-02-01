package GGVpack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 음식 선택 패널
public class Snack extends JPanel {
    GGV origin;
    JPanel fp;
    JPanel snackPanel;
    String[] imagePaths;
    String[] menuNames;
    int[] menuPrices;
    JLabel menuLabel;
    String name;
    JPanel panel;
    JPanel imagePanel;
    JLabel textLabel;
    JButton snackPayment_btn;

    JScrollPane menuScroll;

    JPanel shoppingPanel;
    JScrollPane shoppingScrollPane;
    int total;
    int sum;



    public Snack(JPanel fp, GGV origin) {
        this.origin = origin;
        this.fp = fp;
        buildGUI();
    }

    MouseAdapter miniAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JPanel minipanel = (JPanel) e.getSource();
            String miniName = minipanel.getName();
            int idxMini = Integer.parseInt(miniName);
            if (origin.countmap.containsKey(idxMini)) {
                if (origin.countmap.get(idxMini) == 1) {
                    shoppingPanel.remove(minipanel);
                    origin.miniShoppingPanelList.remove(minipanel);
                    origin.countmap.remove(idxMini);
                } else {
                    origin.countmap.put(idxMini, origin.countmap.get(idxMini) - 1);
                    minipanel.remove(minipanel.getComponent(1));
                    minipanel.add(new JLabel(menuNames[idxMini] + " - " +origin.countmap.get(idxMini) + "개"), "East");
                }
            } else {
                System.out.println("에러");
            }
            revalidate();
            repaint();
        }
    };

    public void buildGUI() {

        setLayout(null);

        JLabel snackInfo1 = new JLabel("=== SNACK BAR ===");
        snackInfo1.setFont(new Font("맑은 고딕",Font.BOLD, 30));
        snackInfo1.setBounds(125, 10, 400, 50);

        JLabel snackInfo2 = new JLabel("# 원하시는 메뉴의 이미지를 눌러주세요");
        snackInfo2.setFont(new Font("맑은 고딕",Font.BOLD, 10));
        snackInfo2.setBounds(125, 55, 400, 50);

        // 스낵 메뉴를 표시할 패널 생성
        snackPanel = new JPanel(new GridLayout(3, 2, 20, 10));
        snackPanel.setBounds(120, 90, 340, 400);

        // 스낵 메뉴의 이미지 경로와 메뉴명 배열 정의
        imagePaths = new String[] { "src/images/Snack/고소팝콘(M).png", "src/images/Snack/달콤팝콘(M).png",
                "src/images/Snack/더블콤보.png", "src/images/Snack/스몰세트.png", "src/images/Snack/칠리치즈나쵸.png",
                "src/images/Snack/칠리치즈핫도그.png" };

        // 스낵 배열 정의 (6가지)
        menuNames = new String[] { "고소팝콘(M)", "달콤팝콘(M)", "더블콤보원", "스몰세트", "칠리치즈나쵸", "칠리치즈핫도그" };

        menuPrices = new int[] { 5000, 6000, 13000, 7000, 4900, 5000 };

        // 장바구니에 인덱스별 개수를 저장하는 해쉬맵
        origin.countmap.clear();
        origin.miniShoppingPanelList = new ArrayList<>();

        shoppingPanel = new JPanel();

        // 메뉴 스크롤
        menuScroll = new JScrollPane(shoppingPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        menuScroll.setBounds(110, 490, 400, 200);
        menuScroll.setBackground(new Color(255, 255, 255, 26));
        menuScroll.setBorder(new EmptyBorder(3, 5, 7, 9));
        menuScroll.revalidate();
        menuScroll.repaint(); //

//        shoppingPanel.setBackground(Color.CYAN);
        shoppingPanel.setBounds(85, 650, 400, 40);
        shoppingPanel.setBackground(new Color(255, 255, 255, 26));
        shoppingPanel.setLayout(new GridLayout(6,1,0,0));
//
//
//        //상하 좌우 스크롤바가 달려있지만 보이지 않는 상태를 설정하는 상수;
////        shoppingScrollPane = new JScrollPane(shoppingPanel,21,31);
////        shoppingScrollPane.setBounds(0,650,0,200);
//
        for (int i = 0; i < 6; i++) {
//            imagePath = imagePaths[i];

//            icon = new ImageIcon(imagePath);
            menuLabel = new JLabel(new Resizing().resizingImg(imagePaths[i], 160, 100));

            name = "<html><body><center>" + menuNames[i] + " <br> " + menuPrices[i] + "원<center><body><html>";

            // 각 스낵 메뉴를 표시할 패널 생성
            panel = new JPanel(new BorderLayout());
            panel.add(menuLabel, BorderLayout.CENTER); // 이미지는 중앙에 배치

            textLabel = new JLabel(name);
            textLabel.setHorizontalAlignment(JLabel.CENTER);

            menuLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            // 글꼴, 굵기, 크기 설정
            Font font = textLabel.getFont().deriveFont(Font.BOLD, 16);
            textLabel.setFont(font);

            panel.add(textLabel, BorderLayout.SOUTH); // 텍스트는 아래에 배치

            int index = i;
//
            // 각 스낵메뉴 패널에 마우스 클릭 이벤트 리스너 추가
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                System.out.println(e.getSource());
                if (!origin.countmap.containsKey(index)) {// 방금 고른 메뉴의 버튼 생성
                    origin.countmap.put(index, origin.countmap.getOrDefault(index, 0) + 1);
                    JPanel newMenuPanel = new JPanel(new BorderLayout());// 리스트에도 넣어주고 스크롤판에 들어가는 판에도 붙여줘야함
                    newMenuPanel.setSize(100, 100);
                    newMenuPanel.setName(index + "");

                    JLabel miniImageLabel = new JLabel(new Resizing().resizingImg(imagePaths[index], 75, 75));

                    JLabel miniTextLabel = new JLabel(menuNames[index] + " - " +origin.countmap.get(index) + "개");
                    miniTextLabel.setSize(100, 100);
                    newMenuPanel.add(miniImageLabel, "West");
                    newMenuPanel.add(miniTextLabel, "East");
                    newMenuPanel.addMouseListener(miniAdapter);
                    shoppingPanel.add(newMenuPanel);
                    origin.miniShoppingPanelList.add(newMenuPanel);
                    System.out.println("버튼추가");
                } else {// 이미 고른메뉴 수량 증가
                    origin.countmap.put(index, origin.countmap.get(index) + 1);
                    for (JPanel mini : origin.miniShoppingPanelList) {// 순환해서 원하는 index 대상찾고
                        if (mini.getName().equals(index + "")) {
                            mini.remove(mini.getComponent(1));
                            mini.add(new JLabel(menuNames[index] + " - " +origin.countmap.get(index) + "개"), "East");
                            break;
                        }
                    }

                    System.out.println("값 증가");
                }
                showSelectedMenu(menuNames[index], menuPrices[index]); // 메뉴 정보 보여주기

                revalidate();
                repaint();
                }
            });

            snackPanel.add(panel); // 전체 패널에 스낵메뉴 패널 추가
        }

        JLabel snackInfo3 = new JLabel("# 메뉴를 클릭하여 제거");
        snackInfo3.setFont(new Font("맑은 고딕",Font.BOLD, 10));
        snackInfo3.setBounds(400, 680, 400, 50);
//
        snackPayment_btn = new JButton("결제하기");
        snackPayment_btn.setSize(20, 30);
        snackPayment_btn.addActionListener(snackPaymentActionListener);
        snackPayment_btn.setBounds(260, 730, 100, 50);
        snackPayment_btn.setBackground(Color.LIGHT_GRAY);
        snackPayment_btn.setFont(getFont().deriveFont(Font.BOLD, 15));

        add(snackInfo1);
        add(snackInfo2);
        add(snackInfo3);
        add(snackPanel); // 스낵메뉴 패널을 메인 컨텐트 패널에 추가
//
        add(menuScroll);
        add(snackPanel); // 스낵메뉴 패널을 프레임에 추가
        add(snackPayment_btn);
//
//
        fp.add(this);
        revalidate();
        repaint();
    }

    ActionListener snackPaymentActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            total = getTotal();
            sum = getSum();
            if(origin.miniShoppingPanelList.size()==0 || origin.miniShoppingPanelList==null){
                JOptionPane.showMessageDialog(null,"선택한 상품이 없습니다.","결제확인",2);
                return;
            }


            int result = JOptionPane.showConfirmDialog(null, "총 " + total + "개의 상품을 결제하시겠습니까?", "결제확인",
                    JOptionPane.YES_NO_OPTION, 3);


            if (result == 0) {
                SnackPayment snackPayment = new SnackPayment(fp, origin);
                fp.remove(fp.getComponent(0));
            }
        }
    };

    private void showSelectedMenu(String menuName, int menuPrice) {
        JOptionPane.showMessageDialog(null, "선택한 메뉴: " + menuName + " - " + menuPrice + "원");
    }

    private int getTotal() {
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += origin.countmap.getOrDefault(i, 0);
        }
        return sum;
    }

    private int getSum() {
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += menuPrices[i] * origin.countmap.getOrDefault(i, 0);
        }
        return sum;
    }

}