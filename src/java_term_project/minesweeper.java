package java_term_project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;

public class minesweeper extends JFrame {
	public Container c; 
	myPanel p; //패널
	private JButton iButton=new JButton(); //이미지버튼
	private JLabel stageShow=new JLabel("<html>초급<br/>지뢰수 : 10"); //패널에 부착할 라벨
	ImageIcon iconArr[]= {new ImageIcon("images/스마일마크.jpg"),new ImageIcon("images/승리마크.png"),new ImageIcon("images/사망마크.jpg"),new ImageIcon("images/타일.png")};
	private JLabel timerLabel;
	private Thread th;
	private Timer runnable;

	public minesweeper() { //생성자
		c=getContentPane();//mine 클래스의 컨탠트팬 저장
		p=new myPanel(); //p에 새로운 패널 할당
		c.setLayout(new BorderLayout()); //레이아웃을은borderLayout
		c.add(p,BorderLayout.NORTH); //레이아웃의 북쪽에 패널할당
		c.add(new Stage(9,9,10),BorderLayout.CENTER);
		
		setSize(300,300);//크기는 300,300
		setVisible(true); //프레임 출력
		setTitle("MINESWEEPER");
	}

	private void resetPanel() {
		iButton.setIcon(iconArr[0]); //화면 상단의 이미지를 기본 스마일마크로 설정
	}
	class myPanel extends JPanel{//화면 상단에 부착될 패널
		public myPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER,30,0)); //레이아웃은 FlowLayout 중심정렬 및 수평간격 30 수직간격 0으로 설정
			//타이머설정
			timerLabel=new JLabel(); //Label 새로 동적할당
			timerLabel.setSize(200, 200); //레이블 크기는 200,200
			runnable=new Timer(timerLabel); //쓰레드로 동작시킬 메소드 할당
			th=new Thread(runnable); //쓰레드 할당
			th.start(); //쓰레드 시작
			//스마일마크 설정
			iButton.setBackground(Color.white); //이미지버튼 배경색은 흰색
			iButton.setIcon(iconArr[0]);//기본 이미지
			iButton.setBorder(null); //이미지의 여백 삭제
			iButton.setBorderPainted(false); //
			iButton.addActionListener(new resetAction()); //이미지버튼에 리스너 부착
			
			add(timerLabel); //쓰레드로 돌아가는 레이블 부착 
			add(iButton); //이미지버튼 
			add(stageShow); //스테이지
		}
		class resetAction implements ActionListener{ //스마일마크 클릭시 게임 재시작
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll(); //컨탠트팬 삭제
				c.removeAll(); //c에 할당된 모든 것을 삭제
				c=getContentPane(); //c에 컨탠트팬 할당
				resetPanel(); //이미지버튼을 기본이지미로
				c.setLayout(new BorderLayout()); //레이아웃은BorderLayout
				c.add(p,BorderLayout.NORTH); //북쪽에 패널 추가
				Image img=iconArr[3].getImage();
				Image newImg;

				c.add(new Stage(9,9,10),BorderLayout.CENTER);////행, 열, 지뢰수, 초급단계		
				
				c.setVisible(true);//프레임 출력
				iButton.setIcon(iconArr[0]); //스마일마크를 기본이미지로
				
			}
		}
		private void resetPanel() {
			iButton.setIcon(iconArr[0]); //화면 상단의 이미지를 기본 스마일마크로 설정
		}
	}

	
	class Stage extends JPanel {
		private boolean left=false,right=false,same=false;
		final int min=0;
		int iRow,iCol;
		JButton button[][]; 
		int numberArr[][];
		int mine;
		boolean checkArr[][]; 
		
		private void numberInit(int i,int s) { //근처의 지뢰의 수를 파악해 버튼이 클릭 되었을때 삽입
			int count=0; //지뢰의 수 체크  변수  
			if( ((i-1)>=min) && ((i-1)<iRow)){ //a 위치의 지뢰체크 
				int x=i-1; //행
				if( ( (s-1) >=min ) && ( (s-1)<iCol ) )//s-1이 0이상이면서 열보다 작다면 
					if(checkArr[x][s-1]==true) //실제 그곳에 지뢰가 있다면 count증가
						count++;
				if( ( s >=min ) && ( s<iCol ) ) //s가 0이상이면서 열보다 작다면
					if(checkArr[x][s]==true)//실제 그곳에 지뢰가 있다면 count증가
						count++;
				if( ( (s+1) >=min ) && ( (s+1)<iCol ) )//s+1이 0이상이면서 열보다 작다면 
					if(checkArr[x][s+1]==true)//실제 그곳에 지뢰가 있다면 count증가
						count++;
			}
			if( (i>=min) && (i<iRow)) { //b 위치의 지뢰체크
				int x=i;
				if( ( (s-1) >=min ) && ( (s-1)<iCol ) ) //s-1이 0이상이면서 열보다 작다면
					if(checkArr[x][s-1]==true)//실제 그곳에 지뢰가 있다면 count증가
						count++;
				if( ( (s+1) >=min ) && ( (s+1)<iCol ) ) //s+1이 0이상이면서 열보다 작다면
					if(checkArr[x][s+1]==true)//실제 그곳에 지뢰가 있다면 count증가
						count++;
			}
			if( ((i+1)>=min) && ((i+1)<iRow)) { //c 위치의 지뢰체크 
				int x=i+1;
				if( ( (s-1) >=min ) && ( (s-1)<iCol ) ) //s-1이 0이상이면서 열보다 작다면
					if(checkArr[x][s-1]==true)//실제 그곳에 지뢰가 있다면 count증가
						count++;
				if( ( s >=min ) && ( s<iCol ) ) //s가 0이상이면서 열보다 작다면
					if(checkArr[x][s]==true) //실제 그곳에 지뢰가 있다면 count증가
						count++;
				if( ( (s+1) >=min ) && ( (s+1)<iCol ) ) //s+1이 0이상이면서 열보다 작다면
					if(checkArr[x][s+1]==true)//실제 그곳에 지뢰가 있다면 count증가
						count++;
			}
			this.numberArr[i][s]=count; //근처 지뢰의 수를 체크하는 배열에 count값 할당
			
		}
		public Stage(int iRow,int iCol,int imine) { //Stage클래스의 생성자
			//생성자로 부터 전달받은 값을 할당
			this.iRow=iRow;this.iCol=iCol; //전달 받은 행과 열 할당
			this.button=new JButton[this.iRow][this.iCol]; //button배열에 행과 열에 맞게 할당
			this.numberArr=new int[this.iRow][this.iCol]; //numberArr배열에 행과 열에 맞게 할당
			this.mine=imine; //전달받은 지뢰의 수 할당
			this.checkArr=new boolean[this.iRow][this.iCol]; //지뢰를 저장하는 배열 
			setLayout(new GridLayout(this.iRow,this.iCol)); //레이아웃은 grid 행과 열에 맞게 생성
			Random rand=new Random(System.currentTimeMillis()); //랜덤을 쓰기위해
			for(int i=0;i<this.iRow;i++) //지뢰 배열초기화
				for(int s=0;s<this.iCol;s++)
					checkArr[i][s]=false; //기본 false로 초기화 
			
			for(int i=0;i<mine;i++) { //지뢰배치
				int x=rand.nextInt(iRow); //행에 맞게 난수 할당
				int y=rand.nextInt(iCol); //열에 맞게 난수 할당
				if(checkArr[x][y]==false) { //난수에 해당하는 인덱스가 가리키는 값이 false라면 true로 변경(지뢰 배치)
					checkArr[x][y]=true;
				}
				else
					i--; //이미 해당하는 위치에 지뢰가 배치되었기에 다시 난수 할당 
			}
//			JOptionPane.showMessageDialog(null, "게임을 시작합니다.\n지뢰의 갯수는 "+ mine + "개 입니다.", "지뢰의 수 ", JOptionPane.INFORMATION_MESSAGE); //지뢰 갯수 출력 메시지
			Image img=iconArr[3].getImage();
			Image newImg;
			newImg=img.getScaledInstance(32, 27, java.awt.Image.SCALE_SMOOTH);
			
			iconArr[3]=new ImageIcon(newImg);
			//버튼생성
			for(int i=0;i<this.iRow;i++) { //행의 수만큼 반복
				for(int s=0;s<this.iCol;s++) { //열의 수만큼 반복
					//button[i][s]=new JButton(); //버튼할당 
					
					button[i][s]=new JButton(iconArr[3]);
					//button[i][s].setBorder(null);
					//button[i][s].setBorderPainted(false);
					button[i][s].setMargin(new Insets(0,0,0,0)); //버튼의 여백을 없게 
					button[i][s].setFont(new Font("함초롱바탕",Font.BOLD,20)); //버튼의 폰트설정
					button[i][s].setForeground(Color.red); //지뢰의 색설정 
					if(checkArr[i][s]!=true) //해당버튼이 지뢰가 아니라면
						this.numberInit(i, s); //주위의 지뢰의 수 체크 후 저장
					else
						this.numberArr[i][s]=-1; //지뢰이기에 numberArr에 -1저장
						
					button[i][s].addMouseListener(new Mouse()); //버튼에 리스너 부착
					button[i][s].setBackground(Color.blue); //버튼의 배경색은 회색
					add(button[i][s]); //패널에 버튼 부착
				}
			}
		}
		class Mouse extends MouseAdapter{ //버튼을 눌렀을 시에 동작하는 마우스리스너 
			
			private int blankCheck(int row,int col) {//해당하는 버튼이 공백이라면 주위의 버튼들도 체크하는 메소드 
				if( ( (row>=min) && (row<iRow) ) && ( (col>=min) && (col<iCol) ) ){ //전달 받은 행과 열이 범위를 초과하지 않는지
					if(numberArr[row][col]==0 && (button[row][col].isEnabled()==true) && !button[row][col].getText().equals("?")) { //행과 열에 해당하는 곳이 빈칸이면서 사용가능하다면 
						button[row][col].setIcon(null); //변경점
						button[row][col].setText(""); //해당하는 곳의 텍스트를 ""로
						button[row][col].setBackground(Color.white);//배경색을 흰색
						button[row][col].setEnabled(false);//사용가능하지 않게(빈칸은 클릭되지 않게)

						blankCheck(row-1,col-1); //a위치의 맨 좌측 체크
						blankCheck(row-1,col); //위치의 중간체크
						blankCheck(row-1,col+1); //a위치의 맨 우측 체크
						blankCheck(row,col-1); //b위치의 좌측 체크
						blankCheck(row,col+1); //b위치의 우측 체크
						blankCheck(row+1,col-1); //c위치의 맨 좌측 체크
						blankCheck(row+1,col); //c위치의 중간 체크
						blankCheck(row+1,col+1); //c위치의 맨 우측 체크 
						return 1; //재귀를 사용과 동시에 종료하기위한 의미없는 리턴값
					}
					else if(numberArr[row][col]>0) { //가라키는 곳에 지뢰가 없고 빈칸이 아니라면 
						int x=numberArr[row][col]; //가리키는 곳의 값을 저장
						button[row][col].setText(Integer.toString(numberArr[row][col])); //버튼에 해당하는 숫자를 저장
						button[row][col].setIcon(null);
						//숫자에 맞게 버튼의 글자색을 변경하기 위해
						if(x==1)
							button[row][col].setForeground(Color.blue);
						else if(x==2)
							button[row][col].setForeground(Color.green);
						else if(x==3)
							button[row][col].setForeground(Color.red);
						else if(x==4)
							button[row][col].setForeground(Color.magenta);
						else if(x==5)
							button[row][col].setForeground(Color.darkGray);
						else
							button[row][col].setForeground(Color.red);
					
						button[row][col].setBackground(Color.white); //배경색을 흰색으로 
						
						return 1; //재귀를 종료하기 위한 의미없는 리턴 값
					}
					else
						return 1; //재귀를 종료하기 위한 의미없는 리턴 값
				}
				else
				return 1; //재귀를 종료하기 위한 의미없는 리턴 값
			}
			public void mousePressed(MouseEvent e) { //마우스를 눌렀을때 동작하는 마우스 리스너 
				if(e.getButton()==MouseEvent.BUTTON1)//왼쪽 버튼이 눌려졌다면 left를 true
					left=true;
				if(e.getButton()==MouseEvent.BUTTON3)//오른쪽 버튼이 눌려졌다면 right를 true
					right=true;
				
				if(left==true && right==true) //마우스 왼쪽 오른쪾 둘다 눌려졌다면 same을 true로 
					same=true;
				
			}
			public void mouseReleased(MouseEvent e) { //마우스를 뗐을 때 동작하는 리스너 
				JButton bu=(JButton)e.getSource();//bu에 해당하는 버튼을 캐스팅해 저장
				int row=0,col=0;
				for(int i=0;i<iRow;i++) { //마우스가 떼진 버튼의 인덱스를 찾기위한 반복문 
					for(int s=0;s<iCol;s++) {
						if(button[i][s]==bu) {
							row=i;
							col=s;
							break;
						}
					}
				}
				
				if(same) { //마우스 양쪽을 같이 뗐을 경우 
					int num=numberArr[row][col]; //해당하는 버튼의 텍스트를 저장
					int count=0; //근처 지뢰의 수를 체크하기위한 변수 
					if(row>=min&&row<iCol &&col>=min&&col<iCol) { //해당하는 행과 열이 범위를 벗어나지는 않았는지
						int i=row,s=col; //i는 행 s는 열을 가리킴
						boolean bFlag=true; //패배를 체크하기위한 bool형 변수 bflag

						if( ((i-1)>=min) && ((i-1)<iRow) && bFlag){ //a 위치의 깃발 체크 
							int x=i-1; //x에 i-1저장, a위치의 행을 가리키기 위함 
							
							if( ( (s-1) >=min ) && ( (s-1)<iCol ) ) //a위치의 맨좌측 깃발체크 
								if(button[x][s-1].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s-1]==-1)//깃발이 있는곳에 지뢰가 있다면 count를 증가 
									count++;
									else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
										bFlag=false;
								
							if( ( s >=min ) && ( s<iCol ) )  //a위치의  중간 깃발 체크
								if(button[x][s].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s]==-1) //깃발이 있는곳에 지뢰가 있다면 count를 증가 
										count++;
										else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
											bFlag=false;
								
							if( ( (s+1) >=min ) && ( (s+1)<iCol ) ) //a위치의  중간 깃발 체크 
								if(button[x][s+1].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s+1]==-1) //깃발이 있는곳에 지뢰가 있다면 count를 증가 
										count++;
										else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
											bFlag=false;
								
						}
						if( (i>=min) && (i<iRow) && bFlag) { //b 위치의 깃발 체크 
							int x=i;
							if( ( (s-1) >=min ) && ( (s-1)<iCol ) ) //b위치의 맨좌측 깃발체크 
								if(button[x][s-1].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s-1]==-1) //깃발이 있는곳에 지뢰가 있다면 count를 증가 
										count++;
										else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
											bFlag=false;
								
							if( ( (s+1) >=min ) && ( (s+1)<iCol ) )  //b위치의 맨우측 깃발체크 
								if(button[x][s+1].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s+1]==-1) //깃발이 있는곳에 지뢰가 있다면 count를 증가 
										count++;
										else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
											bFlag=false;
								
						}
						if( ((i+1)>=min) && ((i+1)<iRow) && bFlag) { //c 위치의 깃발 체크  
							int x=i+1;
							if( ( (s-1) >=min ) && ( (s-1)<iCol ) ) //c위치의 맨좌측 깃발체크 
								if(button[x][s-1].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s-1]==-1) //깃발이 있는곳에 지뢰가 있다면 count를 증가 
										count++;
										else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
											bFlag=false;
								
							if( ( s >=min ) && ( s<iCol ) ) //c위치의 중간 깃발체크 
								if(button[x][s].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s]==-1) //깃발이 있는곳에 지뢰가 있다면 count를 증가 
										count++;
										else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
											bFlag=false;
							
							if( ( (s+1) >=min ) && ( (s+1)<iCol ) ) //c위치의 맨우측 깃발체크 
								if(button[x][s+1].getText().equals("★")==true) //깃발이 꼽혀있니?
									if(numberArr[x][s+1]==-1) //깃발이 있는곳에 지뢰가 있다면 count를 증가 
									count++;
									else //깃발이 있는 곳에 지뢰가 없으면 bflag를 false로
										bFlag=false;
								
						}
						if(bFlag==false) { //가리킨 버튼의 숫자를 초과하는 수의 깃발을 꼽았거나 깃발을 지뢰가 없는 곳에 자신의 수이상 꼽은 경우 
							JOptionPane.showMessageDialog(null, "지뢰를 밟았습니다", "패배", JOptionPane.ERROR_MESSAGE); //패배 메시지 출력
							iButton.setIcon(iconArr[2]); //화면 상단의 이미지를 실패 이미지로 
							
							for(i=0;i<iRow;i++)  //해당하는 행만큼 
								for(s=0;s<iCol;s++)  //해당하는 열만큼
									button[i][s].setEnabled(false); //모든 버튼들을 사용 불가로 
						}
					}
					if(num==count) { //깃발을 제대로 꼽고 마우스 양쪽을 클릭한 경우 
						//공백을 체크하는 함수 실행 
						blankCheck(row-1,col-1); //a위치의 맨 우측
 						blankCheck(row-1,col); //a위치의 중앙
						blankCheck(row-1,col+1); //a위치의 맨 좌측
						blankCheck(row,col-1); //b위치의 좌측
						blankCheck(row,col+1); //b위치의 우측
						blankCheck(row+1,col-1); //c위치의 맨 좌측
						blankCheck(row+1,col); //c위치의 중앙
						blankCheck(row+1,col+1); //c위치의 맨 우측
					}
				}
				else if(left) { //마우스 왼쪽만 클릭한 경우
					
					if(button[row][col].getText().equals("★")!=true &&button[row][col].isEnabled()==true &&button[row][col].getText().equals("")) { //깃발이 꼽혀있는 곳은 클릭되지 않게
						
						if(numberArr[row][col]==0) { //비어있는 곳을 클릭한 경우 
							this.blankCheck(row, col);//공백체크 메소드 
						}
						else if(numberArr[row][col]==-1) { //지뢰를 밟은 경우
							JOptionPane.showMessageDialog(null, "지뢰를 밟았습니다", "패배", JOptionPane.ERROR_MESSAGE); //실패 메시지
					
							for(int i=0;i<iRow;i++)  //행의 수만큼 반복
								for(int s=0;s<iCol;s++)  //열의 수만큼 반복
									button[i][s].setEnabled(false); // 버튼들을 사용 불가로 
	
							iButton.setIcon(iconArr[2]); //스마일마크를 실패 이미지로 
						}
						else { //지뢰 근처의 버튼을 클릭한 경우(숫자출력)
							int x=numberArr[row][col]; //해당하는 곳의 숫자를 저장
							button[row][col].setIcon(null);
							button[row][col].setText(Integer.toString(numberArr[row][col])); //해당하는버튼의 텍스트를 x로 변경
							
							//해당하는 곳의 숫자에 따라서 글자 색을 변경 
							if(x==1)
								button[row][col].setForeground(Color.blue);
							else if(x==2)
								button[row][col].setForeground(Color.green);
							else if(x==3)
								button[row][col].setForeground(Color.red);
							else if(x==4)
								button[row][col].setForeground(Color.magenta);
							else if(x==5)
								button[row][col].setForeground(Color.darkGray);
							else
								button[row][col].setForeground(Color.red);
							button[row][col].setBackground(Color.white); //배경색은 흰색 
						}	
					}
				}
				else if(right) { //마우스 오른쪽을 클릭한 경우 
					if(button[row][col].getText().equals("★")==true) {//해당하는 곳에 깃발이 꼽혀 있다면
						button[row][col].setText("?"); 
					}
					else if(button[row][col].getText().equals("?")==true) {//해당하는 곳에 깃발이 꼽혀 있다면
						button[row][col].setText(""); //깃발 제거
						button[row][col].setIcon(iconArr[3]);
					}
					else if(button[row][col].getText().equals("")) { //빈칸이라면 
						button[row][col].setIcon(null);
						button[row][col].setText("★"); //깃발 생성
					}
						
				}
				int counter=0; //승리조건을 카운트하는 변수 
				//해당 마우스 리스너가 실행될 대 마다 승리조건을 체크
				for(int i=min;i<iRow;i++) { //행의 수만큼 반복
					for(int s=min;s<iCol;s++) { //열의 수만큼 반복 
						if(button[i][s].getBackground()==Color.blue||button[i][s].getIcon()==iconArr[3]) { //사용가능한 칸 체크 
							counter++;
			
						}
					}
					if(counter>mine)//사용가능한 칸이 지뢰 갯수 이상이면 탈출(아직 찾아야할 지뢰가 있다는 뜻)
						break;
				}
				if(counter==mine) { //누를 수 있는 버튼이 지뢰 밖에 안 남았다는 뜻
					JOptionPane.showMessageDialog(null, "지뢰를 모두 찾았습니다", "승리", JOptionPane.INFORMATION_MESSAGE);//승리 메시지
					for(int i=min;i<iRow;i++) {
						for(int s=min;s<iCol;s++) 
							button[i][s].setEnabled(false); //버튼을 모두 사용불가로 변경
					}
					iButton.setIcon(iconArr[1]); //화면 상단 이미지를 승리 이미지로 변경 
				}
				
				left=false;right=false;same=false;//마우스를 다시 체크하기위해 false로 초기화
			}
			
		}

	}

	public static void main(String[] args) {
		new minesweeper();
	}

}

class Timer implements Runnable{ //쓰레드를 구현하기 위한 Timer
	JLabel timerLabel; 
	private int n=0; 
	/*
	 *  @param  timerLabel 숫자를 보여주기위한 레이블
	 *  @param n 시간을 체크하기 위한 변수 
	 */
	public Timer(JLabel timerLabel) { //생성자
		this.timerLabel=timerLabel;
	}
	@Override
	public void run() {
		int i=1; //플레이한 시간을 체크하기 위한 변수 
		while(true) {
			timerLabel.setText(Integer.toString(n)); //레이블의 텍스트를 n으로 변경
			n++;//n값 증가
			try {
				Thread.sleep(1000);//1000만큼 대기 
			}
			catch(InterruptedException e) {//예외발생시 종료 
				return;
			}
				
		}
	}
}
