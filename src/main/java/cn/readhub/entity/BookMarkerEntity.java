package cn.readhub.entity;


public class BookMarkerEntity {

	private Integer id;
	private Integer bookId;
	private Integer readPageNumber;
	private String createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	 
	public Integer getReadPageNumber() {
		return readPageNumber;
	}
	public void setReadPageNumber(Integer readPageNumber) {
		this.readPageNumber = readPageNumber;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	 
}
