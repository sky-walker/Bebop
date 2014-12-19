<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>注册第三步,注册成功</title>
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/user-top.ftl" />
   	<!-- header end ================================================== -->
   <div class="blog-banner">
      <div class="blog-banner-uname">Bebop会员 - 注册 (第三步：注册成功)</div>
    </div>
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="container" style="width:1170px">
   	
    	<div class="uconwap">
        	  <div class="row  ucon-top">
                <div class="span3">
                    <blockquote>
                        <p><strong><span class="badge">1</span>&nbsp;填写会员信息</strong></p>
                    </blockquote>			
                </div>
                <div class="span3">
                    <blockquote>
                        <p><strong><span class="badge">2</span>&nbsp;通过邮箱验证</strong></p>
                    </blockquote>
               </div>
                <div class="span3">
                    <blockquote>
                        <p><strong><span class="badge badge-info">3</span>&nbsp;注册成功</strong></p>
                    </blockquote>		  
                </div>
              </div>
              
              <div class="ucon-body">
	              	<div class="regok">
			            <div class="alert alert-success">
						  <h4>注册成功!</h4>
						  <br/>
						  <p><i class="icon-ok"></i>&nbsp;&nbsp;用户  <span style="color:red"> ${name} </span>您好, 恭喜您已经成功完成了激活,请点击<a href="/user/login"> 这里</a>登录</p>
						</div>
		            <div>
              </div>
        </div>
        
     </div>
     </div>
   <!-- main end   ================================================== -->
   <!-- header start ================================================== -->
    <#include "/share/foot.ftl" />
   	<!-- header end ================================================== -->
</body>
</html>
