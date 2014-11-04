package com.iot.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iot.board.db.BoardBean;
import com.iot.board.db.BoardDAO;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String board_num=request.getParameter("board_num");
		
		BoardDAO dao=new BoardDAO();
		BoardBean bean=dao.board_detail(Integer.parseInt(board_num));
		HttpSession session=request.getSession();
		
		session.setAttribute("board_detail", bean);
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./board/board_detail.jsp?board_num="+board_num);
		return forward;
	}

}
