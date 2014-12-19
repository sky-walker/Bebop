<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Bebop用户注册(第二步,账户激活)</title>
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/user-top.ftl" />
   	<!-- header end ================================================== -->
   <div class="blog-banner">
      <div class="blog-banner-uname">Bebop会员 - 注册 (第二步：邮件验证)</div>
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
                        <p><strong><span class="badge badge-info">2</span>&nbsp;通过邮箱验证</strong></p>
                    </blockquote>
               </div>
                <div class="span3">
                    <blockquote>
                        <p><strong><span class="badge">3</span>&nbsp;注册成功</strong></p>
                    </blockquote>		  
                </div>
              </div>
              
              
              <div class="ucon-body">
	              	<div class="regok">
		              	 <h5>
		              	 	注册第二步，请<span style="color:red">打开邮箱 (${RequestParameters["email"]}) 收取账号激活邮件</span>，并点击邮件中的激活链接，方可使用该账号。
			            	<br />
			           		 如果您一直收不到激活邮件，请检查：
			            </h5>
			            <br />
			            <div class="alert alert-info">
						  <button type="button" class="close" data-dismiss="alert">&times;</button>
						  <h4>Warning!</h4>
						  <br/>
						  <p>1. 请确认是否填写正确的邮箱地址：<strong style='text-decoration: underline;'>${RequestParameters["email"]}</strong></p>
	                      <p>2. 请注意查看您邮箱中的垃圾邮件，可能 邮件被误杀</p>
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
