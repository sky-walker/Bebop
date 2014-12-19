<link href="/styles/blog.css" rel="stylesheet" type="text/css" />
<link href="/styles/bebop.css" rel="stylesheet" type="text/css" />
<link href="/styles/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/styles/jquery.qtip.css" rel="stylesheet" type="text/css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script src="/scripts/oseye.js" type="text/javascript"></script>
<script src="/scripts/jquery.qtip.min.js" type="text/javascript"></script>
<script src="/scripts/jquery.lazyload.min.js" type="text/javascript"></script>
<#assign currentUser = Session["OSEYRUSER"]>
<#if currentUser!"">
	<div class="header clearfix">
		<a href="/blog/${currentUser.blogs.name}" title="查看我的首页" class="welcome" >欢迎 ${currentUser.blogs.name}</a>
		<#assign ose_unReadRemindCount=JspTaglibs["/WEB-INF/UnReadRemindCountTag.tld"]/>
       	<@ose_unReadRemindCount.unReadRemindCount id='${currentUser.id}'/>
		<span id="msgtip">
			<#assign ose_unReadMessageCount=JspTaglibs["/WEB-INF/UnReadMessageCountTag.tld"]/>
       		<@ose_unReadMessageCount.unReadMessageCount id='${currentUser.id}'/>  
		  </span>
		| <span style="position:relative;" id='blog_setting'><a href="/blog/${currentUser.blogs.name}">我的博客</a>
		<ul id="my_toolbar" style="display: none; ">
			<li class="posts_"><a href="/admin/posts" target='_blank'>我的文章</a></li>
			<li class="comments_"><a href="/admin/comments" target='_blank'>博客评论</a></li>
			<li class="concern_"><a href="/admin/concern" target='_blank'>我的关注</a></li>
			<li class="feed_"><a href="/admin/feeds" target='_blank'>好友动态</a></li>
			<li class="favorites_"><a href="/admin/favorites" target='_blank'>我的收藏</a></li>
			<li class="portraint_"><a href="/admin/portraint" target='_blank'>我的头像</a></li>
			<li class="privacy_"><a href="/admin/privacy" target='_blank'>隐私设置</a></li>
			<li class="profile_"><a href="/admin/profile" target='_blank'>个人资料</a></li>
		</ul>
		</span>
		| <a href="/admin/logout" class="logout">退出</a>
	</div>
		<script language='javascript'>
			$(function() {
				 $("img").lazyload({ 
				 	placeholder : "/images/grey.gif",
			        effect : "fadeIn"
			     });

				$('#remind_count').qtip({position: {my: 'top center', at: 'bottom center'},show: 'click',hide: {event: 'click unfocus'},content: {text: "<div class='remind_loading'></div>",ajax: {url: '/remind/last',type: 'POST',data: {},once: false,success: function(data, status) {this.set('content.text', data);ajax_post_json("/remind/unread",{id:${currentUser.id}},function(data) {if(data.status) {$("#remind_count").html(data.msg);$("#remind_count").attr('class','');}});}}}});
				$('#blog_setting')
				.mouseover(function() {$("#my_toolbar").stop(false, true).show();$('#my_toolbar').show();})
				.mouseout(function() {$("#my_toolbar").stop(false, true).hide();$('#my_toolbar').hide();});
			});
		</script>
	<#else>
	<div class="header">
		<a href="javascript:ajax_box_login()" class="welcome">您还未登录!</a> 
		| <a href="javascript:ajax_box_login()" id="login">登录</a> 
		| <a href="/user/register" target="_blank">注册</a>
		</div>
</#if>
