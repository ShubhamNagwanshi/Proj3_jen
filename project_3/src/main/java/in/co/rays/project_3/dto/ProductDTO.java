package in.co.rays.project_3.dto;

import java.util.Date;

public class ProductDTO extends BaseDTO{
	
	
	
	
	private String pName;
	private String price;
	private Date pDate;
//	private long productId;
	/*
	 * public long getProductId() { return productId; } public void
	 * setProductId(long productId) { this.productId = productId; }
	 */
	private String productType;
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Date getpDate() {
		return pDate;
	}
	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}
	
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return productType + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return productType ;
	}

	

}
