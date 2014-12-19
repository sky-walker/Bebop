<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户注册</title>
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/user-top.ftl" />
   	<!-- header end ================================================== -->
   <div class="blog-banner">
      <div class="blog-banner-uname">Bebeop会员 - 注册 (第一步：填写会员信息)</div>
    </div>
   <#compress>
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="container" style="width:1170px">
    	<div class="uconwap">
        	 <div class="row  ucon-top">
                <div class="span3">
                    <blockquote>
                        <p><strong><span class="badge badge-info">1</span>&nbsp;填写会员信息</strong></p>
                    </blockquote>			
                </div>
                <div class="span3">
                    <blockquote>
                        <p><strong><span class="badge">2</span>&nbsp;通过邮箱验证</strong></p>
                    </blockquote>
               </div>
                <div class="span3">
                    <blockquote>
                        <p><strong><span class="badge">3</span>&nbsp;注册成功</strong></p>
                    </blockquote>		  
                </div>
              </div>
              <div class="ucon-body">
              	<form id="regform" action="/user/regactive" method="post" class="form-horizontal ucon-body-form">
              	
              		 <div class="control-group">
	                    <label class="control-label" for="email"></label>
	                    <div class="controls">
	                      ${message}
	                    </div>
	                  </div>
              			
			    	  <div class="control-group">
	                    <label class="control-label" for="email">电子邮箱</label>
	                    <div class="controls">
	                      <input type="text" name="email" id="email">
	                      <span id="emailTip" class="regtip"></span>
	                      <span id="emailFixTip" class="regfix"></span>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="name">用户名</label>
	                    <div class="controls">
	                      <input type="text" name="name" id="username">
	                      <span id="usernameTip"></span>
	                      <span id="usernameFixTip"></span>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="password">密码</label>
	                    <div class="controls">
	                      <input type="password" name="pwd" id="password">
	                      <span id="passwordTip" width="580"></span>
	                      <span id="passwordFixTip"></span>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="repassword">重新输入密码</label>
	                    <div class="controls">
	                      <input type="password" name="repassword" id="repassword">
	                      <span id="repasswordTip" width="580"></span>
	                      <span id="repasswordFixTip"></span>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="nickname">昵称</label>
	                    <div class="controls">
	                      <input type="text" id="nickname" name="nickname">
	                      <span id="nicknameTip" width="580"></span>
	                      <span id="nicknameFixTip"></span>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="gender">性别</label>
	                    <div class="controls">
	                      <input type="radio" name="gender" value="1" checked="checked"/>男
			              <input type="radio" name="gender" value="2" />女
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="province">居住地区</label>
	                    <div class="controls">
	                      <select onchange="showcity(this.value, document.getElementById('city'));" name="province" id="province" style="width:136px;">
	                        <option value="">--请选择省份--</option>
	                        <option value="北京">北京</option>
	                        <option value="上海">上海</option>
	                        <option value="广东">广东</option>
	                        <option value="江苏">江苏</option>
	                        <option value="浙江">浙江</option>
	                        <option value="重庆">重庆</option>
	                        <option value="安徽">安徽</option>
	                        <option value="福建">福建</option>
	                        <option value="甘肃">甘肃</option>
	                        <option value="广西">广西</option>
	                        <option value="贵州">贵州</option>
	                        <option value="海南">海南</option>
	                        <option value="河北">河北</option>
	                        <option value="黑龙江">黑龙江</option>
	                        <option value="河南">河南</option>
	                        <option value="湖北">湖北</option>
	                        <option value="湖南">湖南</option>
	                        <option value="江西">江西</option>
	                        <option value="吉林">吉林</option>
	                        <option value="辽宁">辽宁</option>
	                        <option value="内蒙古">内蒙古</option>
	                        <option value="宁夏">宁夏</option>
	                        <option value="青海">青海</option>
	                        <option value="山东">山东</option>
	                        <option value="山西">山西</option>
	                        <option value="陕西">陕西</option>
	                        <option value="四川">四川</option>
	                        <option value="天津">天津</option>
	                        <option value="新疆">新疆</option>
	                        <option value="西藏">西藏</option>
	                        <option value="云南">云南</option>
	                        <option value="香港">香港特别行政区</option>
	                        <option value="澳门">澳门特别行政区</option>
	                        <option value="台湾">台湾</option>
	                        <option value="海外">海外</option>
	                    </select>
	                    <script src="http://bebop.qiniudn.com/scripts/getcity.js"></script><span class='nodisp'></span>　
	                    <select name="city" id="city" style="width:80px;"></select>
	                      <span id="provinceTip"></span>
	                      <span id="provinceFixTip"></span>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="vcode">验证码</label>
	                    <div class="controls">
	                      <input type="text" name="vcode" id="vcode" maxlength="5">
	                      <span id="vcodeTip"></span>
	                      <span id="vcodeFixTip"></span>
	                      <div style="border: 2px solid #CCC;width:122px;margin-top:5px;">
	                      	<a href="javascript:change_vcode()" id="vcode_change">
		                        <img src="/user/captcha" width="120" height="40" title="点击更换图片" id="v_code_image" style="border: 0px;"/>
		                  	</a>
		                  	<script type="text/javascript">
		                        function change_vcode() {
		                            $('#v_code_image').attr('src',"/user/captcha?r="+Math.random(1000));
		                        }
		                    </script>
	                      </div>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <div class="controls">
	                      <button type="submit" class="btn btn-primary" id="regbutton">提交注册</button>
	                    </div>
	                  </div>
		    	</form>
              </div>
        </div>
     </div>
   <!-- main end   ================================================== -->
    <script src="/scripts/formValidator-4.1.1.js" type="text/javascript" charset="UTF-8"></script>
    <script src="http://bebop.qiniudn.com/scripts/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    <script src="http://bebop.qiniudn.com/scripts/register-version-2013122.js" type="text/javascript"></script>
   </#compress>
   <!-- foot start ================================================== -->
    <#include "/share/foot.ftl" />
   	<!--foot end ================================================== -->
</body>
</html>
