<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Bebop用户登陆</title>
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/user-top.ftl" />
   	<!-- header end ================================================== -->
   <div class="blog-banner">
      <div class="blog-banner-uname">Bebeop用户 - 登录</div>
    </div>
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="container" style="width:1170px">
    	<div class="uconwap">
        	 <div class="row  ucon-top">
              <div class="ucon-body">
                <form id="loginform" action="/user/loginactive" method="post" class="form-horizontal ucon-body-form">
                	  <div class="control-group">
	                    <label class="control-label"></label>
	                    <div class="controls">
	                    </div>
	                  </div>
			    	  <div class="control-group">
	                    <label class="control-label" for="email">电子邮箱</label>
	                    <div class="controls">
	                      <input type="text" name="email" id="email">
	                    </div>
	                  </div>
	                  <div class="control-group">
	                    <label class="control-label" for="password">密码</label>
	                    <div class="controls">
	                      <input type="password" name="pwd" id="password">
	                    </div>
	                  </div>
	                  <div class="control-group">
	                    <label class="control-label" for="vcode">验证码</label>
	                    <div class="controls">
	                      <input type="text" name="vcode" id="vcode" maxlength="5" class="input-small">
	                       	 请输入下面的验证码:
	                      <div style="border: 2px solid #CCC;width:120px;margin-top:10px;">
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
	                    	<label class="checkbox">
						        <input type="checkbox" name="rememberme" value="true" id="checkbox"  checked="checked"/> 记住密码
						        <a href="/user/register" target="_blank">注册</a> <a href="/user/password-reset" target="_blank">忘记密码?</a>
						    </label>
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    	<p style="margin-left:80px;">注意：如果你在网吧等公众场合的机器上登录本站,请在离开机器前退出.</p>
	                  </div>
	                  
	                  <div class="control-group">
	                    <div class="controls">
	                      <input type="button" class="btn btn-primary" id="login_button" value="登  录" />
	                    </div>
	                  </div>
		    	</form>
              </div>
        </div>
     </div>
   <!-- main end   ================================================== -->
   <script src="http://bebop.qiniudn.com/scripts/jquery.form.js" type="text/javascript"></script>
   <script type="text/javascript">
		$(function() {
			$('#login_button').attr("disabled",false);
			$("#login_button").click(function() {
				$.jBox.tip("进行登录...", 'loading',{top:'155px'});
				$(this).attr("disabled",true);
				$(this).attr("value","正在提交登录信息...");
				$('#loginform').ajaxSubmit({ 
			    	dataType:'json',
			        success: function(data) {
			        	if(!data.status) {
			        		$.jBox.tip(data.msg,'info',{top:'155px',focusId:"uname",timeout:800});
			        		$('#email').focus();
			        		$('#login_button').attr("disabled",false);
							$('#login_button').attr("value","现在登录");
			        	}else {
			        		location.href="/";
			        	}
			        } 
			    });
			});
		});
	</script>
   <!-- foot start ================================================== -->
    <#include "/share/foot.ftl" />
   	<!-- foot end ================================================== -->
</body>
</html>
