<script src="http://bebop.qiniudn.com/scripts/roll.js" type="text/javascript" ></script>
<div class="blog-banner">
  <div class="blog-banner-uname">${user.signature}</div>
  <#if currentUser.id==user.id>
	  <p class="pull-right blog-banner-op">
	  	<a href="/admin/home">管理</a> <i class="icon-plus icon-white"></i> 
	  	<a href="/admin/post/new" target="_blank">发表</a>
	  </p>
  </#if>
</div>