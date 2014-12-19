<style type="text/css">
#ajax_login_box {
	
}


</style>


<div id="ajax_login_box" >
	<blockquote style="margin-left:auto;margin-right:auto;">
        <small> 登录Bebop，如果尚未加入的请点击<strong><a href="/user/register" target="_blank"> 注册 </a></strong>加入</small>
    </blockquote>
	<form class="form-horizontal">
	  <div class="control-group">
	  	<label class="control-label" for="inputEmail">电子邮箱</label>
	    <div class="controls">
	    	<input id="email" name="email" class="span3"  type="text">
	    </div>
	  </div>
	  <div class="control-group">
	  	<label class="control-label" for="inputEmail">密码</label>
	  	<div class="controls">
		  <input type="password" id="password" name="pwd"  class="span3" >
		</div>
	  </div>
	  
	  <div class="control-group">
	    <label class="control-label" for="inputEmail">验证码</label>
	    <div class="controls">
	      <input type="text" id="vcode" name="vcode"  class="span2" >
	      <a href="javascript:change_vcode()" id="vcode_change">
				<img src="/user/captcha" width="100" height="25" title="点击更换图片" id="v_code_image" style="border: 0px;">
		  	</a>
		  	<script type="text/javascript">
				function change_vcode() {
					$('#v_code_image').attr('src',"/user/captcha?r="+Math.random(1000));
				}
		    </script>
	    </div>
	  </div>
	  
	  <div class="control-group">
	    <div class="controls">
	      <label class="checkbox">
	        <input type="checkbox" name="rememberme" value="true" id="rememberme"/>记住密码
     	    <a href="/user/password-reset" target="_blank">忘记密码?</a>
	      </label>
	    </div>
	  </div>
	</form>
</div>	