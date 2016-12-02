import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Egg {

	int row,col;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	private static Random r=new Random();
	private Color COLOR=Color.yellow;
	
	public Egg(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public Egg(){
		this(r.nextInt(Yard.ROWS-2)+1,r.nextInt(Yard.COLS)-1);
	}
	//��Ϊ�����ᵲסһ���֣�����-2+1�����������ȡ��������
	public void reAppear(){
		this.row = r.nextInt(Yard.ROWS-2)+1 ;
		this.col = r.nextInt(Yard.COLS)-1;
	}
	
	//��ȡ��ǰ��λ����Ϣ�������ͼ�����������λ��
		public Rectangle getRect(){
			return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
		}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(COLOR);
		g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
		g.setColor(c);
		if(COLOR==Color.yellow){
			COLOR=Color.white;
		}else{
			COLOR=Color.yellow;
		}
	}
	
	
}
