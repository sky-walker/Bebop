<!--[if lte IE 9]>
<script type="text/javascript">
    location.href = '/index/unsupport-browser';
</script>
<![endif]-->
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
<!-- header start ================================================== -->
<link href="http://bebop.qiniudn.com/styles/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="http://bebop.qiniudn.com/styles/bebop-version-2013117.css" rel="stylesheet" type="text/css" />
<link href="http://bebop.qiniudn.com/styles/jquery.qtip.css" rel="stylesheet" type="text/css" />
<script src="http://bebop.qiniudn.com/scripts/jquery.js" type="text/javascript"></script>
<script src="http://bebop.qiniudn.com/scripts/jquery.qtip.min.js" type="text/javascript"></script>
<script src="http://bebop.qiniudn.com/scripts/global-version-2013123.js" type="text/javascript"></script>
<script src="http://bebop.qiniudn.com/scripts/jquery.prettydate.js" type="text/javascript"></script>
<#assign currentUser = Session["OSEYRUSER"]>
<#if currentUser!"">
	<div id="header">
	    <div class="navbar navbar-static-top">
	      <div class="navbar-inner">
	        <div class="container">
	          <a class="brand" href="/">
	          	<table>
	          		<tr><td><img src="http://bebop.qiniudn.com/images/icons/glyphicons_385_blogger.png?imageView/2/w/20" /></td><td>ebop</td></tr>
	          	</table>
	          </a>
	          <div class="nav-collapse collapse">
	            <ul class="nav pull-right">
	              <li>
	              	<a href="/blog/${currentUser.name}" >
	              		<img src='${currentUser.portraint}?imageView/1/w/18/h/18' class="topminiface" />
	              		&nbsp;${currentUser.nickname}
	              	</a>
	              </li>
	              <li>
	              	<#assign ose_unReadRemindCount=JspTaglibs["/WEB-INF/UnReadRemindCountTag.tld"]/>
	   			  	<@ose_unReadRemindCount.unReadRemindCount id='${currentUser.id}'/>
	              </li>
	              <li>
	              	<#assign ose_unReadMessageCount=JspTaglibs["/WEB-INF/UnReadMessageCountTag.tld"]/>
	   				<@ose_unReadMessageCount.unReadMessageCount id='${currentUser.id}'/>
	              </li>
	              <li class="dropdown">
	                  <a href="#" id="drop2" role="button" class="dropdown-toggle" data-toggle="dropdown" style="padding-left:5px;padding-right:5px;">
	                  	<i class="icon-cog"></i>&nbsp;我的博客
	                  </a>
	                  <ul class="dropdown-menu" role="menu">
	                  	<li><a href="/admin/home" target="_blank"><i class="icon-home"></i>&nbsp;个人中心</a></li>
	                    <li><a href="/admin/posts" target="_blank"><i class="icon-file"></i>&nbsp;我的文章</a></li>
	                    <li><a href="/admin/post-categories" target="_blank"><i class="icon-tags"></i>&nbsp;博客分类</a></li>
	                    <li><a href="/admin/comments" target="_blank"><i class="icon-comment"></i>&nbsp;博客评论</a></li>
	                    <li><a href="/admin/concern" target="_blank"><i class="icon-eye-open"></i>&nbsp;我的关注</a></li>
	                    <li><a href="/admin/visits" target="_blank"><i class="icon-road"></i>&nbsp;访客记录</a></li>
	                    <li><a href="/admin/favorites" target="_blank"><i class="icon-heart"></i>&nbsp;我的收藏</a></li>
	                    <li><a href="/admin/portraint" target="_blank"><i class="icon-picture"></i>&nbsp;我的头像</a></li>
	                    <li><a href="/admin/privacy" target="_blank"><i class="icon-lock"></i>&nbsp;隐私设置</a></li>
	                    <li><a href="/admin/profile" target="_blank"><i class="icon-user"></i>&nbsp;个人资料</a></li>
	                  </ul>
	                </li>
	              <li><a href="/admin/logout" class="logout"><i class="icon-off"></i>&nbsp;退出</a></li>
	            </ul>
	          </div>
	        </div>
	      </div>
	    </div>
	</div>
	<script language='javascript'>
	$(function() {
		$('#remind_count').qtip({position: {my: 'top center', at: 'bottom center'},show: 'click',hide: {event: 'click unfocus'},content: {text: "<div class='remind_loading'></div>",ajax: {url: '/remind/last',type: 'POST',data: {},once: false,success: function(data, status) {this.set('content.text', data);ajax_post_json("/remind/unread",{id:${currentUser.id}},function(data) {if(data.status) {$("#remind_count").html(data.msg);$("#remind_count").attr('class','');}});}}}});
	});
</script>
<#else>
<div id="header">
    <div class="navbar navbar-static-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="/">
          	<table>
          		<tr><td><img src="http://bebop.qiniudn.com/images/icons/glyphicons_385_blogger.png?imageView/2/w/20" /></td><td>ebop</td></tr>
          	</table>
          </a>
          <div class="nav-collapse collapse">
            <ul class="nav pull-right">
              <li>
              	<a href="#ajax-login-box" data-toggle="modal">您还未登录</a>
              </li>
 
				<!-- Modal start-->
				<div id="ajax-login-box" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-header">
				    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				    <h4 id="myModalLabel">
				    	<table>
			          		<tr><td><img src="http://bebop.qiniudn.com/images/icons/glyphicons_385_blogger.png?imageView/2/w/20" /></td><td>ebop博客-用户登录</td></tr>
			          	</table>
				    </h5>
				  </div>
				  <div class="modal-body">
				    <!-- login-form start -->
					<form class="form-horizontal">
						  <div class="control-group">
						  	<label class="control-label"></label>
						  </div>
						  <div class="control-group">
						  	<label class="control-label" for="email"><strong>电子邮箱</strong></label>
						    <div class="controls" >
						    	<input id="email" name="email" class="span3"  type="text">
						    </div>
						  </div>
						  <div class="control-group">
						  	<label class="control-label" for="password"><strong>密码</strong></label>
						  	<div class="controls">
							  <input type="password" id="password" name="pwd"  class="span3" >
							</div>
						  </div>
						  <div class="control-group">
						    <label class="control-label" for="vcode"><strong>验证码</strong></label>
						    <div class="controls">
						      <input type="text" id="vcode" name="vcode"  class="span2" placeholder="点击获取验证码">
						        <a href="javascript:;" id="vcode_change"></a>
							  	<script type="text/javascript">
							  		$(function() {
							  			$('#vcode').focus(function() {
							  				if($("#vcode_change").html()=="") {
							  					$("#vcode_change").html('<img src="/user/captcha?r="+Math.random(1000)  title="点击更换图片" id="v_code_image" style="border: 2px solid #CCC;width:100px;height:30px;" > ');
								  				$('#v_code_image').load(function() {
								  					$(this).click(function() {$(this).attr('src',"/user/captcha?r="+Math.random(1000));});
								  				});
							  				}
							  			});
							  		});
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
				    <!-- login-form start -->
				  </div>
				  <div class="modal-footer">
				    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
				    <button type="button" class="btn btn-primary" onclick="javascript:ajax_box_login()" data-loading-text="提交中..." id="ajax-login-btn">现在登录</button>
				  </div>
				</div>
				<!-- Modal end -->
              <li>
              	 <a href="/user/register" target="_blank">注册</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
</div>
</#if>
<!-- header end ================================================== -->