<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#compress>
<html>
<head>
	<title>我的动态</title>
	<link href="/styles/admin.css" rel="stylesheet" type="text/css" />
	<link href="/styles/kandytabs.css" rel="stylesheet" type="text/css" />
	<script src="/scripts/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script src="/scripts/jquery.prettydate.js" type="text/javascript"></script>
	<script src="/scripts/oseye.js" type="text/javascript"></script>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<!-- top start -->
  	<#include "/admin/admin-top.ftl" />
  	<!-- top end-->
	<div id="admin_main">
		<div id="admin_left">
			<h3 class="admin_left_h3">博客管理</h3>
			<ul>
				<li><a href="/admin/post/new">发表文章</a></li>
				<li><a href="/admin/posts">管理文章</a></li>
				<li><a href="/admin/drafts">草稿箱(${currentUser.blogs.draftsNum})</a></li>
				<li><a href="/admin/post-categories">文章分类</a></li>
				<li><a href="/admin/comments">博客评论</a></li>
				<li><a href="/admin/messages">站内消息</a></li>
				<li><a href="/admin/reminds">我的通知</a></li>
				<li><a href="/admin/favorites">收藏管理</a></li>
				<li><a href="/admin/concern">关注管理</a></li>
				<li class="current"><a href="/admin/feeds" style="color: white;">动态管理</a></li>
				<li><a href="/admin/privacy">隐私设置</a></li>
			</ul>
			<h3 class="admin_left_h3">&nbsp;</h3>
			<h3 class="admin_left_h3">用户管理</h3>
			<ul>
				<li><a href="/admin/profile">编辑个人资料</a></li>
				<li><a href="/admin/portraint">更换头像管理</a></li>
				<li><a href="/admin/password">更改登陆密码</a></li>
				<li><a href="/admin/email">更改登录邮箱</a></li>
			</ul>
		 </div>
		<!-- adminMain开始 -->
		<div id="adminMain">
			<div id="admin_path">
				<h3 class="admin_path_h3">
					<a href="#">返回我的首页</a> » <a href="/admin">管理</a> » <a href="/admin/concern">关注管理</a> » <a href="/admin/feed/my">我的动态</a>
				</h3>
			</div>
			<!-- msgs开始 -->
			<div id="messages">
				<dl class="kandyTabs">
				<dt class="tabtitle">
					<a href="/admin/feed/follows"><span class="tabbtn">我关注的动态</span></a>
					<a href="/admin/feed/my"><span class="tabbtn tabcur">我的动态(共<em>${pager.totalRecords}</em>条动态)</span></a>
					<a href="/admin/feed/all"><span class="tabbtn">全站当天动态</span></a>
				</dt>
				<dd class="tabbody">
					<div class="tabcont">
				  		  <#list pager.resultList as entity>
				  		  	  <#include "/share/feed-template.ftl" />
						  </#list>
					</div>
			</div>
			<!-- msgs结束 -->
			<#-- 分页标签 BEGIN -->
    	    <#import "/share/pager_.ftl" as p>
		    <@p.page pager=pager link="/admin/feed/my?" showPageNum=9 anchor=""/>
		    <#-- 分页标签 END -->
			<!-- adminMain结束 -->
			<script type="text/javascript">
				$(function() {
					$(".timeago").prettyDate();
				});
			</script>
		</div>
		<div style="font: 0px/0px sans-serif; clear: both; display: block">
		</div>
		<!-- blog_footer 开始 -->
		<#include "/front/blog-footer.ftl" />
		<!-- blog_footer 结束 -->
	</div>
</body>
</html>
</#compress>