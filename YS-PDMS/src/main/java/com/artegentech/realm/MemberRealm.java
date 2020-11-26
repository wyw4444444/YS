package com.artegentech.realm;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import com.artegentech.action.shiro.ShiroSession;
import com.artegentech.system.service.IMemberService;
import com.artegentech.system.vo.Member;

public class MemberRealm extends AuthorizingRealm {

	private Logger log = Logger.getLogger(MemberRealm.class);

	@Resource
	private IMemberService memberService;

	// ATGSYSTEM
	private static final String SALT = "QVRHU1lTVEVN";

	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("********** 2、用戶角色與許可權：doGetAuthorizationInfo **********");
		//String username = (String) principals.getPrimaryPrincipal();// 取得用戶登錄名
		Member vo = (Member) principals.getPrimaryPrincipal();
		String username = vo.getMember_id();// 取得用戶登錄名
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();// 定義授權信息的返回數據
		try {
			Map<String, Object> map = this.memberService.listAuthByMember(username);
			Set<String> allRoles = (Set<String>) map.get("allRoles");
			Set<String> allActions = (Set<String>) map.get("allActions");
			auth.setRoles(allRoles);
			auth.setStringPermissions(allActions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auth;
	}
	/*SparkTemplate*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("********** 1、用戶登錄認證：doGetAuthenticationInfo() **********");
		// 登錄認證的方法需要先執行，需要用來判斷登錄的用戶信息是否合法
		String username = (String) token.getPrincipal();// 取得用戶名
		String requestSessionId = (String) SecurityUtils.getSubject().getSession().getId();
		// log.info("username : " + username);
		Member vo;
		try {
			vo = this.memberService.getById(username);
			 log.info("vo : " + vo);
			if (vo == null) {
				log.info("vo : " + vo);
				throw new UnknownAccountException("該用戶名不存在!");
			} else { // 進行密碼的驗證處理
				 String password_login = new String((char[])token.getCredentials());
				 String password_login_enc = new Md5Hash(password_login, SALT, 5).toString();
				 
				 String password = vo.getPassword();
				 
				 
				 log.info("password_login : " +password_login); //用戶輸入密碼
				 log.info("password_login_enc : " +password_login_enc); //用戶輸入加密後密碼
				 log.info("password : " + password);   //正確加密後密碼

				if (!password_login_enc.equals(password)) {
					throw new IncorrectCredentialsException("密碼錯誤！");
				}

				// 处理session，将此用户已经登录的session删除掉，保证一个用户同一时间只能登录一次
				log.warn("删除此用户名的其它session！");
				DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
				// DefaultWebSessionManager sessionManager =
				// (DefaultWebSessionManager)securityManager.getSessionManager();
				ShiroSession sessionManager = (ShiroSession) securityManager.getSessionManager();
				Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();// 获取当前已登录的用户session列表
				for (Session session : sessions) {
					String currentLoginUser = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
					String currentLoginSessionId = (String) session.getId();
					// 清除该用户以前登录时保存的session
					if (username.equals(currentLoginUser) && !requestSessionId.equals(currentLoginSessionId)) {
						System.out.println("单点登录username : " + username);
						System.out.println("session : " + session);
						sessionManager.getSessionDAO().delete(session);
					}
				}
				log.warn("已經删除此用户名的其它session！");
				//AuthenticationInfo auth = new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes(SALT), getName());
				AuthenticationInfo auth = new SimpleAuthenticationInfo(vo, password, ByteSource.Util.bytes(SALT), getName());
				return auth;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // 根據後台業務查詢用戶的完整數據
		return null;
	}
}
