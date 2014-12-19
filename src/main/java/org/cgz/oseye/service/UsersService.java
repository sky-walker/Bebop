package org.cgz.oseye.service;


import org.cgz.oseye.model.Users;

/**
 * @author 陈广志 
 * @Description: 用户服务接口
 */
public interface UsersService {

    /**
     * 通过ID查找
     * @return Users
     */
    public Users findById(Integer id);
    
    public Users getById(Integer id);

    /**
     * 通过email查找
     * @param email 邮件地址
     * @return Users
     */
    public Users findByEmail(String email);

    /**
     * 通过name查找
     * @param name 用户名
     * @return Users
     */
    public Users findByName(String name);
    
    /**
     * 通过nickname查找
     * @param nickname 昵称
     * @return Users
     */
    public Users findByNickName(String nickname);
    
    /**
     * 通过sessionKey查找
     * @param sessionKey
     * @return Users
     */
    public Users findBySessionKey(String sessionKey);
    
    /**
     * 判断用户名是否存在
     * @param name 用户名
     * @return
     */
    public boolean userNameIsExist(String name);

    /**
     * 查询昵称是否已经存在
     * @param nickname
     * @return
     */
    public boolean checkNickNameIsExist(String nickname);
    
    /**
     * 判断邮件地址是否存在
     * @param email
     * @return
     */
    public boolean emailIsExist(String email);


    /**
     * 判断用户是否已被激活
     * @param name
     * @return
     */
    public boolean userIsActivated(String name);

    /**
     * 准备注册 向用户邮件中发送激活链接 生成账户激活码保存到数据库,此时的用户没有激活
     * @param user 用户
     * @param activateLink 激活链接
     */
    public void prepareRegister(Users user, String activateLink);

    /**
     * 完成注册
     * @param userName
     * @param activateCode
     */
    public void finishRegister(String userName, String activateCode);


    /**
     * 修改密码
     * @param user
     * @param newPassword
     */
    public void changePassword(Users user, String newPassword);

    /**
     * 准备取回密码
     * @param email
     * @param actionlink
     */
    public void prepareResetPassword(String email, String actionlink);


    /**
     * 启动新的随机密码
     * @param email
     * @param activateCode
     */
    public void finishResetPassword(String email, String activateCode);

    /**
     * 发送邮箱激活链接到用户的新邮箱中
     * @param oldEmail
     * @param newEmail
     * @param activateLink
     */
    public void prepareChangeEmail(String oldEmail, String newEmail, String activateLink);

    /**
     * 完成新邮箱的激活
     * @param id
     * @param newEmail
     * @param activateCode
     */
    public boolean finishChangeEmail(Integer id, String pwd, String newEmail, String activateCode);

    /**
     * 保存用户的头像
     * @param id
     * @param portraint
     */
    public void saveUserPortraint(Integer id, String portraint);


    /**
     * 检查用户使用的激活账户连接是否合法
     * @param userName
     * @param activateCode
     * @return
     */
    public boolean checkRegUrl(String userName, String activateCode);

    /**
     * 用户登录
     * @param name
     * @param password
     */
    public Users login(String name, String password);

    /**
     * 更新用户
     * @param users
     */
    public void update(Users users);
    
}
