package in.co.rays.project_3.dto;

public class ProductNameDTO extends BaseDTO {
	public static final int FRUIT =1;
	public static final int Electronic = 2;
	public static final int GROCERY = 3;
	public static final int OIL = 4;

	private String name;
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return name + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name + "";
	}
	
}
