<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>忘记密码  发送密码重置链接</title>
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/user-top.ftl" />
   	<!-- header end ================================================== -->
   <div class="blog-banner">
      <div class="blog-banner-uname">Bebop用户 - 忘记密码?</div>
    </div>
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="container" style="width:1170px">
    	<div class="uconwap">
        	 <div class="row  ucon-top">
              <div class="ucon-body">
                <form id="reset_pw_form"  method="post" action="/user/prepare-reset-password" class="form-horizontal ucon-body-form">
	                  <div class="control-group" style="padding-left:80px;">
	                    	<blockquote>
	                    		 <small>请输入您注册的邮箱地址，系统将发送重置密码的链接到邮箱中</small>
	                    	</blockquote>
	                  </div>
	                  
			    	  <div class="control-group">
	                    <label class="control-label" for="email">电子邮箱</label>
	                    <div class="controls">
	                      <input type="text" name="email" id="email">
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
	                      <input type="button" class="btn btn-primary" id="reset_pw_bt" value="密码重置" />
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
			$('#email').focus();
			$('#reset_pw_bt').attr("disabled",false);
			$('#reset_pw_bt').click(function() {
				$(this).attr("disabled",true);
				$(this).attr("value","正在提交信息...");
				$('#reset_pw_form').ajaxSubmit({ 
			    	dataType:'json',
			        success: function(data) {
			        	if(data.status) {
			        		$('#reset_pw_bt').attr("disabled",true);
							$('#reset_pw_bt').attr("value","邮件已发送,请查收");
			        	}else {
			        		$.jBox.tip(data.msg,'info',{top:'165px',timeout:800});
			        		$('#reset_pw_bt').attr("disabled",false);
			        		$('#reset_pw_bt').attr("value","密码重置");
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
