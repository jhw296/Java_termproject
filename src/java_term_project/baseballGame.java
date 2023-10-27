package java_term_project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//배열에 3자리 랜덤 숫자 선언
class randomNum {
	int randomArr[];
	public randomNum() {
		randomArr = new int[3];
		
		randomArr[0] = (int)(Math.random()*9+1); //백의 자리의 숫자(랜덤값 1~9)
		randomArr[1] = (int)(Math.random()*9+1); //십의 자리의 숫자(랜덤값 1~9)
		randomArr[2] = (int)(Math.random()*9+1); //일의 자리의 숫자(랜덤값 1~9)
		
		//3자리의 숫자에 동일한 값이 들어가지 않도록 설정
		while(randomArr[0]==randomArr[1]) {
			randomArr[1] = (int)(Math.random()*9+1);
		}

		while(randomArr[0]==randomArr[2] || randomArr[1]==randomArr[2]) {
			randomArr[2] = (int)(Math.random()*9+1);
		}
	}
	
	public int[] getRandomArr() {
		return randomArr;
	}
}


//사용자가 입력한 숫자와 랜덤 숫자 비교 결과를 나타내는 클래스
class resultCheck {
	int strike, ball;
	boolean out;
	public resultCheck(int[] randomArr, int[] inputNum) {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(randomArr[i] == inputNum[j]) { //랜덤 숫자와 사용자의 입력 숫자가 같을 경우 아래의 조건물 실행
					if(i == j) strike++; //랜덤 숫자와 사용자가 입력한 숫자가 같고 위치도 같을 경우 스트라이크 값 증가
					else ball++; //랜덤 숫자와 사용자가 입력한 숫자는 같지만 위치가 다를 경우 볼 값 증가
				}
			}
		}
		//사용자가 입력한 숫자가 랜덤 숫자에 모두 포함되어 있지 않을 경우 아웃값을 true, 그렇지 않을 경우 false 반환
		if(strike == 0 && ball == 0) out = true;
		else out = false;
	}
	
	public int resultStrike() {
		return strike;
	}
	
	public int resultBall() {
		return ball;
	}
	
	public boolean resultOut() {
		return out;
	}
}

class resultPanel extends JPanel{
	int strike = 0;
	int ball = 0;
	boolean out = false;
	
	void setResult(int strike, int ball, boolean out) {
		this.strike = strike;
		this.ball = ball;
		this.out = out;
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.BOLD, 15));
		g.drawString("빈칸에 3자리 숫자를 입력하세요", 80, 90);
		
		if(strike == 3) {
			g.setFont(new Font("", Font.BOLD, 40));
			g.drawString("!THREE STRIKE!", 30, 180); //랜덤 숫자와 입력한 숫자가 모두 같을 경우 출력되는 문구
			g.setFont(new Font("", Font.BOLD, 20));
			g.drawString("숫자를 모두 맞췄습니다!", 70, 220);
			g.drawString("게임을 재시작해주세요", 75, 260);
		}
		else {
			g.setFont(new Font("", Font.BOLD, 20));
			
			//입력한 숫자와 랜덤 숫자의 위치와 숫자가 같을 경우 출력되는 문구
			if(strike == 0) g.drawString("-", 30, 160);
			else if(strike == 1) g.drawString("ONE STRIKE", 30, 160);
			else if(strike == 2) g.drawString("TWO STRIKE", 30, 160);
			
			//입력한 숫자와 랜덤 숫자의 숫자는 같지만 위치가 다를 경우 출력되는 문구
			if(ball == 0) g.drawString("-", 30, 230);
			else if(ball == 1) g.drawString("ONE BALL", 30, 230);
			else if(ball == 2) g.drawString("TWO BALL", 30, 230);
			
			//입력한 숫자와 랜덤 숫자가 모두 일치하는게 없는 경우 출력되는 문구
			if(out) g.drawString("OUT", 30, 300);
			else g.drawString("-", 30, 300);
		}
	}
}

public class baseballGame extends JFrame implements ActionListener {
	resultPanel rp;
	JTextField text;
	JButton btn;
	randomNum rn;
	JLabel l;
	int strike;
	
	public baseballGame(randomNum rn) {
		setTitle("BASEBALL GAME");
		this.rn = rn;
		System.out.println(rn.getRandomArr()[0] + "" + rn.getRandomArr()[1] + "" + rn.getRandomArr()[2]);
		Container c = getContentPane();
		
		c.setLayout(new BorderLayout());
		
		rp = new resultPanel();
		text = new JTextField(15);
		rp.add(text);
		
		btn = new JButton("결과보기");
		btn.addActionListener(this);
		btn.setBackground(new Color(230, 180, 78));
		rp.add(btn);

		c.add(rp);
		
		Color bgColor = new Color(31, 64, 114);
		rp.setBackground(bgColor);
		setSize(400, 400);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int[] userNum = new int[3];
		String[] num = text.getText().split("");
		
		for(int i=0; i<num.length; i++)
			userNum[i] = Integer.parseInt(num[i]);
		
		resultCheck rc = new resultCheck(rn.getRandomArr(), userNum);
		System.out.println(rc.resultBall() + "" + rc.resultStrike());
		rp.setResult(rc.resultStrike(), rc.resultBall(), rc.resultOut());
		rp.repaint();
	}
	
	public static void main(String[] args) {
		new baseballGame(new randomNum());
	}

}