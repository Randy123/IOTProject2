package com.iot.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iot.board.db.BoardBean;
import com.iot.board.db.BoardDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		BoardBean boarddata=new BoardBean();
		String board_id=request.getParameter("board_id");
		String board_subject=request.getParameter("board_subject");
		String board_content=request.getParameter("board_content");
		String board_file=request.getParameter("board_file");
		
		boarddata.setBoard_id(board_id);
		boarddata.setBoard_subject(board_subject);
		boarddata.setBoard_content(board_content);
		boarddata.setBoard_file(board_file);
		
		BoardDAO dao=new BoardDAO();
		boolean result=dao.boardInsert(boarddata);
		if(result==false){
			System.out.println("게시판 등록실패");
			return null;
		}
		System.out.println("게시판 등록 완료");
		
		
		//파일 업로드 webserver
		String realFolder="";
		String saveFolder="boardupload";
		int fileSize=5*1024*1024;
		realFolder=request.getRealPath(saveFolder);
		try {
			MultipartRequest multi=null;
			
			multi=new MultipartRequest(
					request,
					realFolder,
					fileSize,
					"utf-8",
					new DefaultFileRenamePolicy() );
			
			
		} catch (Exception e) {
		}
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("board_list.bo");//게시글 리스트 화면으로 이동
		
		return forward;
	}

}
