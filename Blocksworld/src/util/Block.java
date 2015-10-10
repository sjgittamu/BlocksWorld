package util;

public class Block {
	Character ch;

	public Block(Character ch) {
		super();
		this.ch = ch;
	}

	public Character getCh() {
		return ch;
	}

	public void setCh(Character ch) {
		this.ch = ch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ch == null) ? 0 : ch.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (ch == null) {
			if (other.ch != null)
				return false;
		} else if (!ch.equals(other.ch))
			return false;
		return true;
	}
	
	
	
}
