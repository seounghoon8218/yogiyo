package com.spring.yogiyo.sssmodel;

public class MenuVO {
	
	private String menucode;
	private int masterno;
	private String menuspeccode;
	private String menuname;
	private int menuprice;
	private String menucomment;
	private String menuimage;
	
	public MenuVO() {};
	
	public MenuVO(String menucode, int masterno, String menuspeccode, String menuname, int menuprice,
			String menucomment, String menuimage) {
		super();
		this.menucode = menucode;
		this.masterno = masterno;
		this.menuspeccode = menuspeccode;
		this.menuname = menuname;
		this.menuprice = menuprice;
		this.menucomment = menucomment;
		this.menuimage = menuimage;
	}

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	public int getMasterno() {
		return masterno;
	}

	public void setMasterno(int masterno) {
		this.masterno = masterno;
	}

	public String getMenuspeccode() {
		return menuspeccode;
	}

	public void setMenuspeccode(String menuspeccode) {
		this.menuspeccode = menuspeccode;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public int getMenuprice() {
		return menuprice;
	}

	public void setMenuprice(int menuprice) {
		this.menuprice = menuprice;
	}

	public String getMenucomment() {
		return menucomment;
	}

	public void setMenucomment(String menucomment) {
		this.menucomment = menucomment;
	}

	public String getMenuimage() {
		return menuimage;
	}

	public void setMenuimage(String menuimage) {
		this.menuimage = menuimage;
	}
	
	
	
	
}
