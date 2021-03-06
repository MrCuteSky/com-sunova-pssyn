package com.sunova.psinfo.conponment;

import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import java.util.Properties;
@Component
public class AdCon {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public SearchResult findUserByUserId(String userId, String domain,InitialLdapContext dc) {
        String searchBase ="OU=星诺医药,DC=sunovapharma,DC=com";
        if(StringUtils.isNotBlank(domain)) {
            searchBase=domain;
        }
//        logger.info("查找范围:{},查找:{}",searchBase,userId);
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchFilter = "sAMAccountName=" + userId;
        String[] returnedAtts = {"distinguishedName", "samAccountName", "department", "departmentNumber", "userAccountControl", "lockoutTime", "otherMailbox"}; // null 返回；定制返回属性
        searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集

        try {
//            System.out.println(dc);
            NamingEnumeration<SearchResult> answer = dc.search(searchBase, searchFilter, searchCtls);
            if (answer.hasMoreElements()) {
                return answer.next();
            }
        } catch (NamingException e) {
            logger.warn("未找到{}",userId);
        }
        return null;
    }

    public void modifyPassWord(String userId,String newPassword) throws Exception{
        String ldapSSL = "ldaps://172.17.3.2:636";
        String domain = "@sunovapharma.com";
        System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk-17.0.1\\lib\\security\\cacerts"); //AD证书
//        System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files (x86)\\Java\\jdk1.8.0_131\\jre\\lib\\security\\cacerts"); //AD证书
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification","true");

        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");// LDAP访问安全级别："none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, "050217"+domain);
        env.put(Context.SECURITY_CREDENTIALS, "@Zhangyu2016");
        env.put(Context.PROVIDER_URL, ldapSSL);
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        InitialLdapContext dc = new InitialLdapContext(env, null);

        SearchResult sr = findUserByUserId(userId,"OU=星诺医药,DC=sunovapharma,DC=com",dc);
        if(sr==null) {
            throw new Exception("用户不存在");
        }
        Attributes attrs = sr.getAttributes();
        Attribute attr = attrs.get("distinguishedName"); // 全局唯一的。同一个域名下面的cn 不能重复。
        ModificationItem[] mods = new ModificationItem[1];
        String newQuotedPassword = "\"" + newPassword + "\"";
        byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                new BasicAttribute("unicodePwd", newUnicodePassword));
        // 修改密码
        dc.modifyAttributes(attr.get().toString(), mods);
    }

}
