package com.multi.gazee.member;

import java.sql.Timestamp;
import java.util.Date;

//RAM에 만드는 저장공간을 만든다.
public class MemberVO {
	// MemberVO가방에 넣은 데이터는 Member테이블에 들어갈 예정
	// 각 컬럼과 일치시켜 줌.
	private int no;
	private String id;
	private String pw;
	private String name;
	private String tel;
	private String nickname;
	private String email;
	private int userLevel;
	private String account;
	private String bank;
	private String status;
	private Date birth;
	private String gender;
	private Timestamp joinDate;
	private String profileImg;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public Timestamp getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Timestamp joinDate) {
		this.joinDate = joinDate;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	
	@Override
	public String toString() {
		return "MemberVO [no=" + no + ", id=" + id + ", pw=" + pw + ", name=" + name + ", tel=" + tel + ", nickname="
				+ nickname + ", email=" + email + ", userLevel=" + userLevel + ", account=" + account + ", bank=" + bank
				+ ", status=" + status + ", birth=" + birth + ", gender=" + gender + ", joinDate=" + joinDate
				+ ", profileImg=" + profileImg + "]";
	}
	



	
	
}
