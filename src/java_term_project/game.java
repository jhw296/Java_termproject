package java_term_project;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game extends JFrame { // 초기화면 클래스 정의
	private JButton startBtn;
	private JButton startBtn2;
	private JLabel gameTitle;
	
	public game() { 
		setTitle("Game World");  // 프레임 타이틀 정의
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		
		//시작 화면 game title 설정
		gameTitle = new JLabel("GAME WORLD");
		gameTitle.setBounds(43, 90, 350, 50);
		gameTitle.setFont(new Font("Times", Font.BOLD, 33));
		//gameTitle.setForeground(new Color(164, 174, 250));
		gameTitle.setForeground(new Color(31, 64, 114));
		gameTitle.setVisible(true);
		this.add(gameTitle);
		
		//시작 화면 지뢰 찾기 시작 버튼 설정
		startBtn = new RoundedButton("지뢰찾기");
		//startBtn.setBackground(new Color(255, 255, 255));
		startBtn.setFont(new Font("Times", Font.BOLD, 15));
		startBtn.setSize(100, 40);
		startBtn.setLocation(110, 190);
		this.add(startBtn);
		
		//시작 화면 단어 게임 시작 버튼 설정
		startBtn2 = new RoundedButton("야구 게임");
		//startBtn2.setBackground(new Color(255, 255, 255));
		startBtn2.setFont(new Font("Times", Font.BOLD, 15));
		startBtn2.setSize(100, 40);
		startBtn2.setLocation(110, 250);
		this.add(startBtn2);
		
		//시작 화면 제작자 label 설정
		JLabel producer = new JLabel("제작 : 20117894 조혜원");
		producer.setBounds(98, 345, 200, 20);
		producer.setFont(new Font("Times", Font.BOLD, 12));
		producer.setForeground(new Color(31, 64, 114));
		this.add(producer);
		
		startBtn.addActionListener(new ActionListener() { // 1번문제 메뉴에 있는 실행 아이템을 클릭 시 Start클래스 윈도우 실행
			public void actionPerformed(ActionEvent e) {
				new minesweeper();
			}
		});
		
		startBtn2.addActionListener(new ActionListener() { // 2번문제 메뉴에 있는 메모장실행 아이템을 클릭 시 Note클래스 윈도우 실행
			public void actionPerformed(ActionEvent e) {
//				new baseballGame(new baseballGame());
				new baseballGame(new randomNum());
			}
		});

		setSize(330, 500);
		setVisible(true);
		setResizable(false); // 창의 크기를 고정
		setLocationRelativeTo(null); 
	}

	//버튼 둥근 사각형 디자인 - 출처 : https://the-illusionist.me/42
	public class RoundedButton extends JButton {
		public RoundedButton(String text) { super(text);} 

			@Override 
			protected void paintComponent(Graphics g) {
				Color c=new Color(230,180,78); //배경색 결정
				Color o=new Color(0,71,113); //글자색 결정
				int width = getWidth(); 
				int height = getHeight(); 
				Graphics2D graphics = (Graphics2D) g; 
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
				if (getModel().isArmed()) { graphics.setColor(c.darker()); } 
				else if (getModel().isRollover()) { graphics.setColor(c.brighter()); } 
				else { graphics.setColor(c); } 
				graphics.fillRoundRect(0, 0, width, height, 10, 10); 
				FontMetrics fontMetrics = graphics.getFontMetrics(); 
				Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
				int textX = (width - stringBounds.width) / 2; 
				int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
				graphics.setColor(o); 
				graphics.setFont(getFont()); 
				graphics.drawString(getText(), textX, textY); 
				graphics.dispose(); 
				super.paintComponent(g); 
			}
	}

	public static void main(String [] args) {
		new game(); // No1 객체 실행
	}
}