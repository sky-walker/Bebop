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
	              	<a href="/blog/${currentUser.name}">
	              		<img src='${currentUser.portraint}?imageView/1/w/20/h/20' width="20px" height="20px" border="0">&nbsp;${currentUser.nickname}
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
	                  	<i class="icon-chevron-down"></i>&nbsp;我的博客
	                  </a>
	                  <ul class="dropdown-menu" role="menu">
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
              	<a href="/user/login">您还未登录!</a> 
              </li>
              <li>
              	 <a href="/user/register">注册</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
</div>
</#if>
<!-- header end ================================================== -->