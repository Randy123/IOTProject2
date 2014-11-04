package com.iot.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iot.board.db.BoardBean;
import com.iot.board.db.BoardDAO;

public class Board_list_Action implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		int limit=5; //한페이지에 표현될 게시글수
		int page_blok=3;//한블럭당 페이지 개수
		int page=1; //처음 1 page
		
		BoardDAO dao=new BoardDAO();
		int list_ea=dao.get_list_all_count();//게시글 총수
		int max_page= list_ea/limit; //페이지 개수
		if(list_ea%limit > 0) 
			max_page++;
		//총블럭수
		int page_block_EA= (int)Math.ceil((double)max_page/page_blok);
		
		String getPage=request.getParameter("page");
		if(getPage!=null)
			page=Integer.parseInt(getPage);
		
		//현재 블럭값
		int current_block=(int)Math.ceil((double)page/page_blok);
		//현재 블럭의 start_page
		int start_page= (current_block-1)*page_blok+1;
		//현재 블럭의 end_page
		int end_page= current_block * page_blok;
		if(end_page > max_page)
			end_page=max_page;
		
		ArrayList<BoardBean> list=dao.select_list(page , limit);
		
		HttpSession session=request.getSession();
		session.setAttribute("contents_page", "./board/board_list.jsp");
		session.setAttribute("list", list);
		session.setAttribute("list_ea", list_ea);
		session.setAttribute("max_page", max_page);
		session.setAttribute("start_page", start_page);
		session.setAttribute("end_page", end_page);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		//path 생략
		
		return forward;
	}

}
