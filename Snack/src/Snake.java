import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	Node head = null;
	Node tail = null;
	int size = 0;
	Node n=new Node(20, 30, Direction.Left);
	private Yard y;

	public Snake(Yard y) {
		head = n;
		tail = n;
		size = 1;
		this.y=y;
	}

	public void addToTail() {
		Node node = null;
		switch (tail.dir) {
		case Left:
			node = new Node(tail.row, tail.col + 1, tail.dir);
			break;
		case Up:
			node = new Node(tail.row + 1, tail.col, tail.dir);
			break;
		case Right:
			node = new Node(tail.row, tail.col - 1, tail.dir);
			break;
		case Down:
			node = new Node(tail.row - 1, tail.col, tail.dir);
			break;
		}
		tail.next=node;
		node.prev=tail;
		tail=node;
		size++;
	}
	
	public void addToHead() {
		Node node = null;
		switch (head.dir) {
		case Left:
			node = new Node(head.row, head.col - 1, head.dir);
			break;
		case Up:
			node = new Node(head.row - 1, head.col, head.dir);
			break;
		case Right:
			node = new Node(head.row, head.col + 1, head.dir);
			break;
		case Down:
			node = new Node(head.row + 1, head.col, head.dir);
			break;
		}
		node.next=head;
		head.prev=node;
		head=node;
		size++;
	}

	public void draw(Graphics g){
		if(size<=0) return;
		//���ƶ��������߽ڵ㣬��������΢����һ��
		move();
		for(Node n=head;n!=null;n=n.next){
			n.draw(g);
		}
	}
	
	/*
	 * �ƶ��൱�ڽ�β��Node���Ƶ�ͷ��ǰ��
	 */
	private void move() {
		addToHead();
		deleteFromTail();
		checkDead();
	}

	private void checkDead() {
		if(head.row<2||head.col<1||head.row>Yard.ROWS-2||head.col>Yard.COLS-2){
			y.stop();
		}
		for(Node n=head.next;n!=null;n=n.next){
			if(head.row==n.row&&head.col==n.col){
				y.stop();
			}
		}
	}

	//ɾ��β�ڵ�
	private void deleteFromTail() {
		if(size==0) return;
		tail=tail.prev;
		tail.next=null;
	}
	
	public void eat(Egg e){
		if(this.getRect().intersects(e.getRect())){
			e.reAppear();
			this.addToHead();
			y.setScore(y.getScore() + 5);
		}
	}
	
	//��ȡ��ǰ��λ����Ϣ�������ͼ�����������λ��
	private Rectangle getRect(){
		return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	}

	private class Node {
		int w = Yard.BLOCK_SIZE;
		int h = Yard.BLOCK_SIZE;
		int row, col;
		Direction dir = Direction.Left;// ����������ȷ����ͷ�ķ���
		Node next;// �ߵ���һ��
		Node prev=null;//�ߵ�ǰһ��

		public Node(int row, int col, Direction dir) {
			super();
			this.row = row;
			this.col = col;
			this.dir = dir;
		}

		private void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
			g.setColor(c);
		}
	}

	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT :
			if(head.dir != Direction.Right);
				head.dir = Direction.Left;
			break;
		case KeyEvent.VK_UP :
			if(head.dir != Direction.Down)
				head.dir = Direction.Up;
			break;
		case KeyEvent.VK_RIGHT :
			if(head.dir != Direction.Left)
				head.dir = Direction.Right;
			break;
		case KeyEvent.VK_DOWN :
			if(head.dir != Direction.Up)
				head.dir = Direction.Down;
			break;
		}
	}
}
