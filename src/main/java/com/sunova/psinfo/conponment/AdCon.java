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
        String searchBase ="OU=万邦医药集团,DC=wbpharma,DC=com";
        if(StringUtils.isNotBlank(domain)) {
            searchBase=domain;
        }
        logger.info("查找范围:{},查找:{}",searchBase,userId);
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

    public void testAD(String userId,String newPassword) throws Exception{
        String ldapSSL = "LDAPS://192.168.0.100:636";
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");// LDAP访问安全级别："none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, "账号");
        env.put(Context.SECURITY_CREDENTIALS, "密码");
        System.setProperty("javax.net.ssl.trustStore", "/usr/local/jdk/jdk1.8.0_311/jre/lib/security/cacerts"); //AD证书
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification","true");

        env.put(Context.PROVIDER_URL, ldapSSL);
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        InitialLdapContext dc = new InitialLdapContext(env, null);

        try {
            SearchResult sr = findUserByUserId(userId,"OU=万邦医药集团,DC=wbpharma,DC=com",dc);
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
        } catch (Exception e) {
            logger.error("Problem resetting password: " + e.getMessage());
            Exception err = new Exception("用户密码重置错误!");
            if(e.getMessage().contains("5003")) {
                err = new Exception("密码违法策略要求");
            }
            throw err ;
        }
    }
}
