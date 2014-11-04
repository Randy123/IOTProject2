package com.iot.memberController;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iot.medel.MemberCheck;
import com.iot.medel.MemberDAO;
import com.iot.medel.MemberDTO;


//@WebServlet("*do")
public class MemberLoginController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String Req_uri=request.getRequestURI();
		//  "/chap12/loginProcess.do"
		String contextPath=request.getContextPath();
		// "/chap12"
		String command=Req_uri.substring(contextPath.length() +1);
		HttpSession session=request.getSession();
		
		if(command.equals("loginProcess.do")){
			
			//회원로그인체크 처리*****************************
			String id=request.getParameter("user_id");
			String pw=request.getParameter("user_pass");
			
			MemberDAO dao=new MemberDAO();
			MemberCheck result_mc=dao.memberCheck(id, pw);
			//3가지종류로 mc반환
			
			
			session.setAttribute("mc",  result_mc);
			if(result_mc.getName()==null){
				//회원이 아니거나 pw틀리면
				session.setAttribute("contents_page","./member/loginForm.jsp");
			
			}else{
				//회원이다..로그인처리
				session.setAttribute("login_OK_id",  id);
				session.removeAttribute("contents_page");
			}
			response.sendRedirect("index.jsp");
		}else if(command.equals("logout.do")){
			session.removeAttribute("mc");
			session.removeAttribute("login_OK_id");
			session.removeAttribute("contents_page");
			response.sendRedirect("index.jsp");
		}else if(command.equals("joinForm.do")){
			//회원가입페이지로 이동
			session.setAttribute("contents_page", "./member/joinForm.jsp");
			response.sendRedirect("index.jsp");
		}else if(command.equals("loginJoinAction.do")){
			//회원가입 처리 부분*************************************
			String id=request.getParameter("userid");
			String pass=request.getParameter("userpass");
			String name=request.getParameter("username");
			String email=request.getParameter("useremail");
			
			MemberDTO to=new MemberDTO();
			to.setId(id);
			to.setPass(pass);
			to.setName(name);
			to.setEmail(email);
			
			MemberDAO dao=new MemberDAO();
			dao.insertMember(to);
			
			session.removeAttribute("contents_page");
			response.sendRedirect("index.jsp");
			
		}else if(command.equals("memberView.do")){
			//멤버 전체 보기*****************************
			MemberDAO dao=new MemberDAO();
			Vector<MemberDTO> result_memberAll=dao.selectAllMember();
			
			session.setAttribute("result_memberAll", result_memberAll);
			session.setAttribute("contents_page", "./member/memberView.jsp");
			response.sendRedirect("index.jsp");
		}else if(command.equals("deleteMember.do")){
			//멤버 삭제************************************
			String del_id=request.getParameter("del_id");
			MemberDAO dao=new MemberDAO();
			dao.deleteMember(del_id);
			
			session.setAttribute("contents_page", "./member/memberView.jsp");
			response.sendRedirect("./memberView.do");
		}else if(command.equals("editMember.do")){
			//수정버튼 처리
			String edit_id=request.getParameter("edit_id");
			MemberDAO dao=new MemberDAO();
			MemberDTO to=dao.editMember(edit_id);
			session.setAttribute("to", to);
			session.setAttribute("contents_page", "./member/editMember.jsp");
			response.sendRedirect("index.jsp");
		}else if(command.equals("editProcess.do")){
			//수정처리
			String id=request.getParameter("userid");
			String pass=request.getParameter("userpass");
			String name=request.getParameter("username");
			String email=request.getParameter("useremail");
			
			MemberDTO to=new MemberDTO();
			to.setId(id);
			to.setPass(pass);
			to.setName(name);
			to.setEmail(email);
			
			MemberDAO dao=new MemberDAO();
			dao.editMemberProcess(to);
			session.setAttribute("contents_page", "./member/memberView.jsp");
			response.sendRedirect("./memberView.do");

		}else if(command.equals("login.do")){
			session.setAttribute("contents_page", "./member/loginForm.jsp");
			response.sendRedirect("index.jsp");
		}else if(command.equals("index.do")){
			session.removeAttribute("contents_page");
			response.sendRedirect("index.jsp");
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
