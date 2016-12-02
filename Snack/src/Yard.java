import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;


public class Yard extends Frame {

	PaintThread pt=new PaintThread();
	//Ϊ�˽���޷���ʾ��Ϸ���������⣬����false����
	private boolean gameOver=false; 

	public static final int COLS=40;
	public static final int ROWS=30;
	public static final int BLOCK_SIZE=15;
	
	private Font fontGameOver = new Font("����", Font.BOLD, 50);
	
	private int score = 0;

	Snake s=new Snake(this);
	Egg e=new Egg();
	
	Image offScreenImage = null;//ͼ��˫���壬���update����ʹ��
	
	public void launch(){
		this.setLocation(200,70);
		this.setSize(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		this.setVisible(true);
		this.addKeyListener(new KeyMoniter());
		
		new Thread(pt).start();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Yard().launch();
	}
	
	public void stop(){
		this.gameOver=true;
	}

	@Override
	public void paint(Graphics g) {
		Color c=g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
//		������
		g.setColor(Color.DARK_GRAY);
		for(int i=0;i<ROWS;i++){
			g.drawLine(0, BLOCK_SIZE*i, COLS*BLOCK_SIZE, BLOCK_SIZE*i);
		}
		for(int i=0;i<COLS;i++){
			g.drawLine(BLOCK_SIZE*i,0,BLOCK_SIZE*i,ROWS*BLOCK_SIZE);
		}
		
		g.setColor(Color.red);
		g.drawString("score:" + score, 10, 60);
		
		if(gameOver) {
			g.setFont(fontGameOver);
			g.drawString("��Ϸ����", 120, 180);
			
			pt.gameOver();
		}
		
		g.setColor(c);
		
		s.eat(e);
		e.draw(g);
		s.draw(g);
	}
	
	//�����Ļ��˸����
	//˫���壬��дrepaint���õ�update����
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if(offScreenImage==null){
			offScreenImage=this.createImage( COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics  gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage,0,0,null);
	}
	
//	�����˶�������ÿ100msˢ��һ��
	private class PaintThread implements Runnable{
		//���߳��п��ƽ��̵����кͽ���
		private boolean running=true;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(running){
				repaint();
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		
		public void gameOver(){
			this.running=false;
		}
	}
	
	private class KeyMoniter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}



