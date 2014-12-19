<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>邮箱重置</title>
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/front-top.ftl" />
   	<!-- header end ================================================== -->
   <div class="blog-banner">
      <div class="blog-banner-uname">Bebeop博客用户 - 重置邮箱</div>
    </div>
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="container" style="width:1170px">
    	<div class="uconwap">
        	 <div class="row  ucon-top">
              <div class="ucon-body">
                <form id="chg_email_form"  method="post" action="/admin/finish-change-email" class="form-horizontal ucon-body-form">
				  	 <input type="hidden" name="id" value="${id}"/>
				  	 <input type="hidden" name="activateCode" value="${activateCode}"/>
	                  <div class="control-group" style="padding-left:180px;">
	                  		<p class="text-error"> 请输入正确的密码 以便激活新邮箱</p>
	                  </div>
	                  
			    	  <div class="control-group">
	                    <label class="control-label" for="email">您的新邮箱</label>
	                    <div class="controls">
	                      <input type="text" name="email" id="email" value="${email}" class="uneditable-input">
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <label class="control-label" for="password">登录密码</label>
	                    <div class="controls">
	                      <input type="password" name="password" id="password">
	                    </div>
	                  </div>
	                  
	                  <div class="control-group">
	                    <div class="controls">
	                      <input type="button" class="btn btn-primary" id="chg_email_bt" value="激活新邮箱" />
	                      &nbsp;&nbsp;<span id="oK"></span>
	                    </div>
	                  </div>
		    	</form>
              </div>
             </div>
        </div>
     </div>
   <!-- main end   ================================================== -->
   <script src="http://bebop.qiniudn.com/scripts/jquery.form.js" type="text/javascript"></script>
   <script type="text/javascript">
		$(function() {
			$('#chg_email_bt').attr("disabled",false);
			$('#chg_email_bt').click(function() {
				$('#chg_email_form').ajaxSubmit({ 
			    	dataType:'json',
			        success: function(data) {
			        	if(data.status) {
			        		$('#password').val("");
			        		$('#chg_email_bt').attr("disabled",true);
			        		$("#oK").html(data.msg);
		        			$("#oK").fadeIn('fast');
			        	}else {
			        		$.jBox.tip(data.msg,'error',{top:'155px',focusId:"password"});
			        	}
			        } 
			    });
			});
		});
	</script>
   <!-- header start ================================================== -->
    <#include "/share/foot.ftl" />
   	<!-- header end ================================================== -->
</body>
</html>
