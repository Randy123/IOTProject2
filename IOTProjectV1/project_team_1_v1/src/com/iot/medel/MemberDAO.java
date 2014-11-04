package com.iot.medel;

import java.sql.*;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class MemberDAO {
	
	
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public MemberDAO(){
		try {
			Context init=new InitialContext();
			DataSource ds = (DataSource) init
				    .lookup("java:comp/env/jdbc/MyDB");
			conn=ds.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*멤버 체크 실행 메서드*/
	public MemberCheck memberCheck(String u_id, String u_pw) {
		String sql="select * from member2 where id=?";
		MemberCheck mc=new MemberCheck();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, u_id);
			rs=ps.executeQuery();
			
			if(rs.next()){
				String id=rs.getString("id");
				String pw=rs.getString("pass");
				if(u_pw.equals(pw)){
					//ID PW일치
					mc.setId_ok(true);
					mc.setPw_ok(true);
					mc.setName(rs.getString("name"));
				}
				//pw틀림
				mc.setId_ok(true);
			}
			//ID가 없다
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mc;
	}
	
	/*회원 가입처리 메서드*/
	public void insertMember(MemberDTO vo) {
		
		
		String sql="insert into member2 values(?, ?, ?, ?)";
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getPass());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getEmail());
			
			int x=ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/*멤버 전체 보기 메소드*/
	public Vector<MemberDTO> selectAllMember() {
		String sql="select * from member2";
		
		Vector<MemberDTO> memberAll=new Vector<MemberDTO>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				MemberDTO vo=new MemberDTO();
				vo.setId( rs.getString("id") );
				vo.setPass( rs.getString("pass") );
				vo.setName(rs.getString("name") );
				vo.setEmail(rs.getString("email") );
				//한사람의 data정보를 저장
				memberAll.add(vo);
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return memberAll;
		
	}
	
	//멤버 삭제 메소드
	public void deleteMember(String del_id) {
		
		String sql="delete from member2 where id=?";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, del_id);
			int x=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public MemberDTO editMember(String edit_id) {
		String sql="select * from member2 where id=?";
		MemberDTO to=new MemberDTO();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, edit_id);
			rs=ps.executeQuery();
			if(rs.next()){
				to.setId( rs.getString("id") );
				to.setPass( rs.getString("pass") );
				to.setName(rs.getString("name") );
				to.setEmail(rs.getString("email") );
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return to;
	}
	public void editMemberProcess(MemberDTO to) {
		String sql="update  member2 set pass=? ,name=?, email=?  where id=?";
		try {
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, to.getPass());
			ps.setString(2, to.getName());
			ps.setString(3, to.getEmail());
			ps.setString(4, to.getId());
			ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
