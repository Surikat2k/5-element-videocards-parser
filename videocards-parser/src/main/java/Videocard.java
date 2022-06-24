import java.net.MalformedURLException;
import java.net.URL;

import org.apache.poi.ss.usermodel.Hyperlink;

public class Videocard {

	private int id;
	private String brand;
	private String name;
	private double priceOld;
	private double price;
	private String url;
	private String image;
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("ID: ")
			.append(this.id)
			.append(" | Barnd: ")
			.append(this.brand)
			.append(" | Name: ")
			.append(this.name)
			.append(" | Old Price: ")
			.append(this.priceOld)
			.append(" | Price: ")
			.append(this.price)
			.append(" | URL: ")
			.append(this.url)
			.append(" | Image URL: ")
			.append(this.image);
		return res.toString();
	}
	
	public Videocard(String id, String barnd, String name, String priceOld, String price, String url, String image) throws MalformedURLException {
		this.id = Integer.parseInt(id);
		this.brand = barnd;
		this.name = name;
		this.priceOld = priceOld == "" ? Double.parseDouble(price) : Double.parseDouble(priceOld);
		this.price = Double.parseDouble(price);
		this.url = url;
		this.image = image;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPriceOld() {
		return priceOld;
	}
	
	public void setPriceOld(double priceOld) {
		this.priceOld = priceOld;
	}
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	
}