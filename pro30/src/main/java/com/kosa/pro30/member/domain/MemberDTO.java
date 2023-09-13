package com.kosa.pro30.member.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	private String userid;
	private String pwd;
	private String uname;
	private Date birth;
	private String gender;
	private String phone;
	private String email;
	
	
	// 로그인 - 비밀번호 일치여부
	public boolean isEqualsPwd(MemberDTO member) {
		return pwd.equals(member.getPwd());
	}
	
	// 로그인 - 비밀번호 일치여부
	public boolean isEqualsID(MemberDTO member) {
		return userid.equals(member.getUserid());
	}
	
	
	
} // memberDTO


/*
 * //로그인 public MemberDTO(String id, String pwd) { this.id = id; this.pwd = pwd;
 * }
 * 
 * 
 * public MemberDTO(String id, String name, String pwd) { this.id = id;
 * this.name = name; this.pwd = pwd; }
 * 
 * public MemberDTO(String id, String name, String pwd, String phone) { this.id
 * = id; this.name = name; this.pwd = pwd; this.phone = phone; }
 * 
 * 
 * @Override public boolean equals(Object obj) { if (this == obj) return true;
 * if (obj == null) return false; if (getClass() != obj.getClass()) return
 * false; MemberDTO other = (MemberDTO) obj; return Objects.equals(id,
 * other.id); }
 * 
 * 
 * @Override public int hashCode() { return Objects.hash(id); }
 */
