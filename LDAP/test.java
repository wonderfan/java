package com.ldap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.SortKey;

public class MyTask {
	
	private Hashtable<String, String> env;
	
	public static void main(String[] args) {
		MyTask task = new MyTask();
		task.init();
		LdapContext context = task.getContext();  
		try {
			// task.removeEntry(context);
			task.registerNewEntry(context);
			task.getAdminUsers(context);
			
		} catch (Exception e) {
			//System.out.println(e);
			e.printStackTrace();
		}finally{
			try {
				context.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void init(){
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		// env.put(Context.PROVIDER_URL,"ldap://10.20.0.75:389/");
		env.put(Context.PROVIDER_URL,"ldap://10.20.0.51:389/");
		env.put(Context.SECURITY_AUTHENTICATION,"simple");
		env.put(Context.SECURITY_PRINCIPAL,"cn=Root");
		env.put(Context.SECURITY_CREDENTIALS,"abcdef");
		env.put(Context.REFERRAL, "follow");
		env.put("com.sun.jndi.ldap.connect.timeout", "30000");
		env.put("com.sun.jndi.ldap.read.timeout", "30000");
		env.put("com.sun.jndi.ldap.connect.pool", "true");
	}
	
	public  LdapContext getContext(){
		LdapContext ctxt = null;
		try {
			ctxt = new InitialLdapContext(env, null);
		}catch (Exception e){
			System.out.print(e.getMessage());
		}
		return ctxt;
    }
	
	public void getAdminUsers(LdapContext lCtx) throws Exception{
        SearchControls schCtrls = new SearchControls();  
        schCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);  
        String filter = "(&(objectclass=CBFUser))";
        String dynamicFilter = filter;
        String baseAdmin = "cn=Users,cn=CBF,cn=IBM";
        lCtx.setRequestControls(new Control[] {new SortControl(new SortKey[]{new SortKey("createDate",false,null)},Control.CRITICAL) ,new PagedResultsControl(50, Control.CRITICAL)});  
        NamingEnumeration<SearchResult> results = lCtx.search(baseAdmin, dynamicFilter, schCtrls);  
        while (results != null && results.hasMoreElements()) {  
            SearchResult sr = results.next();  
        	String userDN = sr.getNameInNamespace();
        	Attributes attrs = sr.getAttributes();
        	System.out.println(userDN);
        	System.out.println(getAttribute(attrs, "createDate"));
        	System.out.println(getAttribute(attrs, "mobile"));
        	System.out.println(getAttribute(attrs, "account"));
        }
        
	}

	public String getAttribute(Attributes attrs, String attrName) throws NamingException {
		Attribute attr = attrs.get(attrName);
		if (attr == null) {
			return "";
		} else {			
			return (String) attr.get();
		}
	}
	
	public void registerNewEntry( LdapContext context) throws Exception{
		BasicAttributes attrsbu = new BasicAttributes();
		BasicAttribute objclassSet = new BasicAttribute("objectclass");
		objclassSet.add("top");
		objclassSet.add("CBFUser");
		attrsbu.put(objclassSet);
		attrsbu.put("cn", "jiahe");
		attrsbu.put("sn", "jiage");
		attrsbu.put("givenname", "jiahe");
		String password = "aq1sw2de";
		attrsbu.put("userPassword", password.getBytes());
		attrsbu.put("languageCode", "zh_CN");
		attrsbu.put("telephoneNumber", "1334444444");
		attrsbu.put("mobile", "3388888888888");
		attrsbu.put("department", "IBM");
		String uid = "fanjiahe@qq.com";
		attrsbu.put("uid", uid);
		attrsbu.put("status", "0");	
		attrsbu.put("userRole","97");
		Date cerateDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createTime = dateFormat.format(cerateDate); 
		attrsbu.put("createDate", createTime);
		String uidDirectory = "uid="+uid+",cn=Users,cn=CBF,cn=IBM";
		context.createSubcontext(uidDirectory, attrsbu);
	}
	
	public void  removeEntry(LdapContext context) throws Exception{
		String uid = "fanjiahe@qq.com";
		String uidDirectory = "uid="+uid+",cn=Users,cn=CBF,cn=IBM";
		context.destroySubcontext(uidDirectory);
	}
}
