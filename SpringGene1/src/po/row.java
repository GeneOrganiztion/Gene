package po;

import java.util.List;

public class row {
	private int id;
	private String name;
	private List<Classify> row;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Classify> getRow() {
		return row;
	}

	public void setRow(List<Classify> row) {
		this.row = row;
	}

}
