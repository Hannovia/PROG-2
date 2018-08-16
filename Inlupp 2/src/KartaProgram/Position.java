package KartaProgram;

public class Position  {
	
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Position) {
			return ((Position)o).x==this.x && ((Position)o).y==this.y;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		
		return x*10000+y;
	}
}
