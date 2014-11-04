package com.iot.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public BoardDAO(){
		try {
			Context init=new InitialContext();
			DataSource ds = (DataSource) init
				    .lookup("java:comp/env/jdbc/MyDB");
			conn=ds.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean boardInsert(BoardBean boarddata) {
		String sql="";
		int num=0;
		int result=0;
		try {
			sql="select max(board_num) from board";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1) + 1;
			}else{
				num=1;
			}
						
			sql="insert into board values(?,?,?,?,?,?,?,?,?,sysdate)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, boarddata.getBoard_id());
			ps.setString(3, boarddata.getBoard_subject());
			ps.setString(4, boarddata.getBoard_content());
			ps.setString(5, boarddata.getBoard_file());
			ps.setInt(6, num);
			ps.setInt(7, 0);
			ps.setInt(8, 0);
			ps.setInt(9, 0);
			
			result=ps.executeUpdate();
			if(result==0)
				return false;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	public ArrayList<BoardBean> select_list(int page , int limit) {
		int start_num= (page-1)*limit +1;
		int end_num= start_num + limit -1;
		
		String sql="select * from "
						+ "(select  BOARD_NUM, BOARD_ID, BOARD_SUBJECT, BOARD_CONTENT, BOARD_FILE, "
						+ "BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ,BOARD_READCOUNT, BOARD_DATE, rownum rnum"
						+ " from"
								+ "(select *from board"
								+ " order by board_num desc))"
					+ "where rnum>=? and rnum<=?";
		ArrayList<BoardBean> list=new ArrayList<BoardBean>();
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, start_num);
			ps.setInt(2, end_num);
			rs=ps.executeQuery();
			
			while(rs.next()){
				BoardBean bean=new BoardBean();
				
				bean.setBoard_num(rs.getInt(1));
				bean.setBoard_id(rs.getString(2));
				bean.setBoard_subject(rs.getString(3));
				bean.setBoard_content(rs.getString(4));
				bean.setBoard_file(rs.getString(5));
				bean.setBoard_re_ref(rs.getInt(6));
				bean.setBoard_re_lev(rs.getInt(7));
				bean.setBoard_re_seq(rs.getInt(8));
				bean.setBoard_readcount(rs.getInt(9));
				bean.setBoard_date(rs.getDate(10));
				
				list.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
		
	}
	public int get_list_all_count() {
		String sql="select count(*) from board";
		int result=0;
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			if(rs.next()) 
				result=rs.getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	public BoardBean board_detail(int board_num) {
		String sql="select * from board where board_num=?";
		BoardBean bean=new BoardBean();
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, board_num);
			rs=ps.executeQuery();
			
			if(rs.next()){
				bean.setBoard_num(rs.getInt(1));
				bean.setBoard_id(rs.getString(2));
				bean.setBoard_subject(rs.getString(3));
				bean.setBoard_content(rs.getString(4));
				bean.setBoard_file(rs.getString(5));
				bean.setBoard_re_ref(rs.getInt(6));
				bean.setBoard_re_lev(rs.getInt(7));
				bean.setBoard_re_seq(rs.getInt(8));
				bean.setBoard_readcount(rs.getInt(9));
				bean.setBoard_date(rs.getDate(10));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bean;
		
	}
	
	//////////////////////////////////////////////////////////
	
	
	

}
