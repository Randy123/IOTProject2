package com.iot.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iot.board.action.Action;
import com.iot.board.action.ActionForward;
import com.iot.board.action.BoardAddAction;
import com.iot.board.action.BoardDetailAction;
import com.iot.board.action.Board_list_Action;
import com.iot.board.action.Board_list_Action2;



public class BoardController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String Req_uri=request.getRequestURI();
		//System.out.println(Req_uri);  
		String[] path=Req_uri.split("/");
		String command=path[path.length-1];
		
		//System.out.println(command);
		
		ActionForward forward=null;
		Action action=null;
		HttpSession session=request.getSession();
		if(command.equals("board_Add_Action.bo")){
			
			action=new BoardAddAction();
			try {
				forward=action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else if(command.equals("board_write.bo")){
			session.setAttribute("contents_page", "./board/board_write.jsp");
			forward=new ActionForward();
			forward.setRedirect(true);
			
		}else if(command.equals("board_list2.bo")){
				action=new Board_list_Action2();
				try {
					forward=action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			
			//response.sendRedirect("./board/board_write.jsp");
		}else if(command.equals("board_list1.bo")){
			action=new Board_list_Action();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("boardDetail.bo")){
			action=new BoardDetailAction();
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//if END
		
		
		
		
		
		if(forward != null){
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}else{
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
				
		}
	}
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
