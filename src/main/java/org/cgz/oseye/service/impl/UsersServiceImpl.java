package org.cgz.oseye.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.cgz.oseye.common.BaseDao;
import org.cgz.oseye.common.QLBuilder;
import org.cgz.oseye.common.SystemConstant;
import org.cgz.oseye.email.Email;
import org.cgz.oseye.email.EmailService;
import org.cgz.oseye.model.Blogs;
import org.cgz.oseye.model.Post_categories;
import org.cgz.oseye.model.UserSettings;
import org.cgz.oseye.model.Users;
import org.cgz.oseye.service.FeedService;
import org.cgz.oseye.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 
 * @author 陈广志 
 * @Description: 用户服务实现类
 */
@Service("userService")
@Transactional
public class UsersServiceImpl implements UsersService {

	@Resource
	private BaseDao baseDao;
	@Resource
	private EmailService emailService;
	@Resource
	private FeedService feedService;
	
    /**
     * 通过ID查找
     * @return Users
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Users findById(Integer id) {
        return baseDao.find(Users.class,id);
    }
    
    /**
     * 通过ID查找
     * @return Users
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Users getById(Integer id) {
        return baseDao.get(Users.class,id);
    }


    /**
     * 通过email查找
     * @param email 邮件地址
     * @return Users
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Users findByEmail(String email) {
        return baseDao.findByWhere(QLBuilder.select(Users.class).where("email").setQlParams(email));
    }
    
    /**
     * 通过name查找
     * @param name 用户名
     * @return Users
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Users findByName(String name) {
    	return baseDao.findByWhere(QLBuilder.select(Users.class).where("name").and("status").setQlParams(name,SystemConstant.USERS_ACTIVE));
    }
    
    /**
     * 通过nickname查找
     * @param nickname 昵称
     * @return Users
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Users findByNickName(String nickname) {
    	return baseDao.findByWhere(QLBuilder.select(Users.class).where("nickname").setQlParams(nickname));
    }
    
    /**
     * 通过sessionKey查找
     * @param sessionKey
     * @return Users
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public Users findBySessionKey(String sessionKey) {
        return baseDao.findByWhere(QLBuilder.select(Users.class).where("sessionKey").setQlParams(sessionKey));
    }

    /**
     * 判断用户名是否存在
     *
     * @param name 用户名
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public boolean userNameIsExist(String name) {
        return baseDao.isExistedByWhere(QLBuilder.count(Users.class).where("name").setQlParams(name));
    }

    /**
     * 判断邮件地址是否存在
     *
     * @param email
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public boolean emailIsExist(String email) {
        return baseDao.isExistedByWhere(QLBuilder.count(Users.class).where("email").setQlParams(email));
    }


    /**
     * 判断用户是否已被激活
     *
     * @param name
     * @return
     */
    @Override
    @Transactional(readOnly=true,propagation= Propagation.NOT_SUPPORTED)
    public boolean userIsActivated(String name) {
    	return baseDao.isExistedByWhere(QLBuilder.count(Users.class).where("name").setQlParams(name));
    }

    /**
     * 准备注册 向用户邮件中发送激活链接 生成账户激活码保存到数据库,此时的用户没有激活
     *
     * @param user         用户
     * @param activateLink 激活链接
     */
    @Override
    public void prepareRegister(Users user, String activateLink) {
    	user.setPwd(DigestUtils.shaHex(user.getPwd()));//SHA加密用户密码
    	user.setActivateCode(UUID.randomUUID().toString());//UUID生成激活码
    	user.setNickname(user.getNickname());
    	baseDao.save(user);//保存用户
    	Email email = new Email(user.getEmail(), "bebop用户激活", "register.ftl");
    	Map<String , Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("activateLink", activateLink);
    	dataMap.put("email", user.getEmail());
        dataMap.put("name",user.getName());
        dataMap.put("activateCode",user.getActivateCode());
    	email.setDataMap(dataMap);
        emailService.sendMailByAsynchronousMode(email);//异步发送激活邮件
    	//emailService.sendMailBySynchronizationMode(email);
    }

    /**
     * 完成注册,并为用户开启博客
     *
     * @param name
     * @param activateCode
     */
    @Override
    public void finishRegister(String name, String activateCode) {
        Users user = baseDao.findByWhere(QLBuilder.select(Users.class).where("name").and("activateCode").setQlParams(name,activateCode));
        if(user!=null) {
        	user.setStatus(SystemConstant.USERS_ACTIVE);
        	user.setActivateCode("");
            user.setName(name);
        	//为用户创建博客
        	Blogs blog = new Blogs();
            blog.setId(user.getId());//博客编号和用户编号一致
            blog.setName(user.getName());//用户名默认即为博客的名称
            blog.setUsers(user);
            user.setBlogs(blog);
            baseDao.save(blog);
            //用户默认的一些设置
            UserSettings userSettings = new UserSettings();
            userSettings.setId(user.getId());
            userSettings.setUser(user);
            baseDao.save(userSettings);
            //保存用户 
            baseDao.update(user);
            //添加默认分类
            Post_categories post_category = new Post_categories();
            post_category.setName("默认");
            post_category.setBlogs(blog);
            post_category.setUsers(user);
            baseDao.save(post_category);
            post_category.setSort(post_category.getId());
            feedService.addFeed(user);
        }
    }

    /**
     * 修改密码
     * @param user
     * @param newPassword
     */
    @Override
    public void changePassword(Users user, String newPassword) {
        user.setPwd(DigestUtils.shaHex(newPassword));
        user.setActivateCode("");
        baseDao.update(user);
    }

    /**
     * 准备取回密码(发送邮件生成随机10位密码,只有当用户点击激活链接时,此密码才会生效)
     * @param email
     * @param activateLink
     */
    @Override
    public void prepareResetPassword(String email, String activateLink) {
        Users user = baseDao.findByWhere(QLBuilder.select(Users.class).where("email").setQlParams(email));
    	if(user!=null) {
    		String resetPasswordKey = UUID.randomUUID().toString().substring(26);//生成一个10位的临时密码
    		user.setActivateCode(UUID.randomUUID().toString());//产生激活码
    		Email mail = new Email(email, "bebop密码重置", "resetPassword.ftl");
    		Map<String, Object> dataMap = new HashMap<String, Object>();//创建数据模型
    		dataMap.put("resetPasswordKey", resetPasswordKey);
    		dataMap.put("activateLink", activateLink);
    		dataMap.put("email", email);
    		dataMap.put("activateCode", user.getActivateCode());
    		mail.setDataMap(dataMap);
    		//emailService.sendMailBySynchronizationMode(mail);
    		user.setResetPasswordKey(DigestUtils.shaHex(resetPasswordKey));
    		baseDao.update(user);
    		emailService.sendMailByAsynchronousMode(mail);
    	}
    	
    }

    /**
     * 启动新的随机密码
     *
     * @param email
     * @param activateCode
     */
    @Override
    public void finishResetPassword(String email, String activateCode) {
        Users user = baseDao.findByWhere(QLBuilder.select(Users.class).where("email").and("activateCode").setQlParams(email,activateCode));
        if(user!=null && (user.getResetPasswordKey().trim()!=null || !"".equals(user.getResetPasswordKey().trim()))) {
        	user.setPwd(user.getResetPasswordKey());//将临时密码变成真实密码
        	user.setActivateCode("");
        	user.setResetPasswordKey("");
        	baseDao.update(user);
        }
    }

    /**
     * 准备修改邮箱(发送邮箱激活链接到用户的新邮箱中)
     * @param oldEmail
     * @param newEmail
     * @param activateLink
     */
    @Override
    public void prepareChangeEmail(String oldEmail, String newEmail, String activateLink) {
        Users user = baseDao.findByWhere(QLBuilder.select(Users.class).where("email").setQlParams(oldEmail));
        if(user!=null) {
        	user.setActivateCode(UUID.randomUUID().toString());
        	Email email = new Email(newEmail, "bebop邮箱重置", "changeEmail.ftl");
        	Map<String, Object> dataMap = new HashMap<String, Object>();
        	dataMap.put("activateLink", activateLink);
        	dataMap.put("id", user.getId());
        	dataMap.put("email", newEmail);
        	dataMap.put("activateCode", user.getActivateCode());
        	email.setDataMap(dataMap);
        	emailService.sendMailByAsynchronousMode(email);
        	//emailService.sendMailBySynchronizationMode(email);
        }
    }

    /**
     * 完成新邮箱的激活
     * @param id
     * @param newEmail
     * @param activateCode
     */
    @Override
    public boolean finishChangeEmail(Integer id, String pwd, String newEmail, String activateCode) {
        Users user = baseDao.findByWhere(QLBuilder.select(Users.class).where("id").and("pwd").and("activateCode").setQlParams(id,DigestUtils.shaHex(pwd),activateCode));
        if(user!=null) {
        	user.setEmail(newEmail);
        	user.setActivateCode("");
        	baseDao.update(user);
        	return true;
        }
        return false;
    }

    /**
     * 保存用户的头像
     *
     * @param id
     * @param portraint
     */
    @Override
    public void saveUserPortraint(Integer id, String portraint) {
        Users user = findById(id);
        if(user!=null) {
        	user.setPortraint(portraint);
        	baseDao.update(user);
        }
    }

    /**
     * 检查用户使用的激活账户连接是否合法
     * @param name
     * @param activateCode
     * @return
     */
    @Override
    public boolean checkRegUrl(String name, String activateCode) {
        return baseDao.isExistedByWhere(QLBuilder.count(Users.class).where("name").and("activateCode").setQlParams(name,activateCode));
    }

    /**
     * 查询昵称是否已经存在
     * @param nickname
     * @return
     */
    public boolean checkNickNameIsExist(String nickname){
    	return baseDao.isExistedByWhere(QLBuilder.count(Users.class).where("nickname").setQlParams(nickname));
    }
    
    /**
     * 用户登录
     *
     * @param name
     * @param password
     */
    @Override
    public Users login(String email,String password) {
        return baseDao.findByWhere(QLBuilder.select(Users.class).where("email").and("pwd").setQlParams(email,DigestUtils.shaHex(password)));
    }
    
    /**
     * 更新用户
     */
    public void update(Users users) {
        baseDao.update(users);
    }
}
