package com.spring.yogiyo.sssmodel;

import org.springframework.web.multipart.MultipartFile;

public class MenuVO {
	
	private String menucode;
	private int masterno;
	private String fk_menuspeccode;
	private String menuname;
	private int menuprice;
	private String menucomment;
	private String fileName;	
	private String orgFilename; 
	private String fileSize; 	
	private String fk_shopcategorycode;
	private int fk_masterno;
	private int pop_menuspeccode;
	private MultipartFile attach;

	public MenuVO() {}

	public MenuVO(String menucode, int masterno, String fk_menuspeccode, String menuname, int menuprice,
			String menucomment, String fileName, String orgFilename, String fileSize, String fk_shopcategorycode,
			MultipartFile attach, int fk_masterno,int pop_menuspeccode) {
		super();
		this.menucode = menucode;
		this.masterno = masterno;
		this.fk_menuspeccode = fk_menuspeccode;
		this.menuname = menuname;
		this.menuprice = menuprice;
		this.menucomment = menucomment;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
		this.fk_shopcategorycode = fk_shopcategorycode;
		this.attach = attach;
		this.fk_masterno = fk_masterno;
		this.pop_menuspeccode = pop_menuspeccode;
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

	public String getFk_menuspeccode() {
		return fk_menuspeccode;
	}

	public void setFk_menuspeccode(String fk_menuspeccode) {
		this.fk_menuspeccode = fk_menuspeccode;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrgFilename() {
		return orgFilename;
	}

	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFk_shopcategorycode() {
		return fk_shopcategorycode;
	}

	public void setFk_shopcategorycode(String fk_shopcategorycode) {
		this.fk_shopcategorycode = fk_shopcategorycode;
	}

	public MultipartFile getAttach() {
		return attach;
	}

	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}

	public int getFk_masterno() {
		return fk_masterno;
	}

	public void setFk_masterno(int fk_masterno) {
		this.fk_masterno = fk_masterno;
	}

	public int getPop_menuspeccode() {
		return pop_menuspeccode;
	}

	public void setPop_menuspeccode(int pop_menuspeccode) {
		this.pop_menuspeccode = pop_menuspeccode;
	}

	
	
	
}
