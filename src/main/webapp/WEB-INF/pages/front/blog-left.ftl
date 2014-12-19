<div class="span3 front-left">
       <div class="divbord">
       		<div class="media front-left-head" style="margin-top:2px;margin-bottom:5px;">
              <a class="pull-left thumbnail" href="/blog/${user.name}">
                <img class="media-object" style="width: 135px; height: 135px; " src="${user.portraint}" >
              </a>
              <div class="media-body">
                <p>
                	<#assign ose_userstatus=JspTaglibs["/WEB-INF/UserStatusTag.tld"]/>
	          		<@ose_userstatus.userStatus uid='${user.id}'/> 
                </p>
                <p>
	                	<#if concernIsFriends?exists>
	          				<#if concernIsFriends = 1>
	          					<i class="icon-refresh"></i>
	          					<a href="javascript:follow(${blog.id},false);">互为关注</a>
	          				</#if>
	          				<#if concernIsFriends = 0>	
	          					<i class="icon-headphones"></i>
	          					<a href="javascript:follow(${blog.id},false)">取消关注</a>
	          				</#if>
	          			<#else>
	          				<i class="icon-headphones"></i>
	          				<a href="javascript:follow(${blog.id},true)">添加关注</a>
	          			</#if>
                	</a>
                </p>
                <p><a href="javascript:send_msg(${user.id})"><i class="icon-envelope"></i>&nbsp;发送消息</a></p>
                <p><a href="/blog/${user.name}/follows" title="所有的关注"><i class="icon-user"></i>&nbsp;关注</a><a href="/blog/${user.name}/fans" title="所有的粉丝">粉丝</a></p>
                <p><a href="/blog/${user.name}/feeds"  title="用户最近的动态"><i class="icon-volume-up"></i>&nbsp;最近动态</a></p>
              </div>
              <p class="front-left-uname">${user.nickname}</p>
              <table  class="muted">
              	<tr>
                	<td width="130px">城市：${user.province} ${user.city}</td>
                    <td>浏览：	${blog.viewsNum}次</td>
                </tr>
                <tr>
                	<td>
                		性别：
                    	<#if user.gender=1>
                    		帅哥
                    	<#elseif user.gender=2>
                    		美女
                    	<#else>
                    		保密
                    	</#if>
                	</td>
                	<td>登录：	<span class="timeago" title="${user.thisLoginTime?string('yyyy-MM-dd HH:mm:ss')}">${user.thisLoginTime}</span></td>
                </tr>
              </table>
            </div>
       </div>
       <br/>
       <div class="divbord front-left-item">
       		<h6>最近访客<span class="pull-right"><a href="/blog/${user.name}/visits">更多访客...</a></span></h6>
            <ul class="front-left-img" id="blog-left-visits">
			     <div class="loading" style="display:block;">正在加载...</div>       	
            </ul>
       </div>
       
       <div class="divbord">
       		<h6>博客分类</h6>
            <ul class="front-left-list"  id="blog-left-categories">
            	<div class="loading" style="display:block;">正在加载...</div>       	
            </ul>
       </div>
       
        <div class="divbord">
       		<h6>评论排行</h6>
           <ul class="front-left-list"  id="blog-left-mostcomment">
           		<div class="loading" style="display:block;">正在加载...</div>       	
            </ul>
       </div>
       
       <div class="divbord">
       		<h6>阅读排行</h6>
            <ul class="front-left-list"  id="blog-left-mostview">
            	<div class="loading" style="display:block;">正在加载...</div>       	
            </ul>
       </div>

       <div class="divbord">
       		<h6>我的关注</h6>
            <ul class="front-left-img"  id="blog-left-follows">
            	<div class="loading" style="display:block;">正在加载...</div>       	
            </ul>
       </div>
       
       <div class="divbord">
       		<h6>我的粉丝</h6>
            <ul class="front-left-img"  id="blog-left-fans">
            	<div class="loading" style="display:block;">正在加载...</div>       	
            </ul>
       </div>
  </div>
  <script type="text/javascript">
	$(function(){
		$('#blog-left-categories').load("/blog/categories",{id:'${blog.id}'},function(){}).fadeIn();	
		$('#blog-left-mostcomment').load("/blog/mostComment",{id:'${blog.id}'},function(){}).fadeIn();
		$('#blog-left-mostview').load("/blog/mostRead",{id:'${blog.id}'},function(){}).fadeIn();
		$("#blog-left-visits").load("/blog/leftVisits",{id:'${blog.id}'},function(){}).fadeIn();
		$("#blog-left-follows").load("/blog/leftFollows",{id:'${blog.id}'},function(){}).fadeIn();
		$("#blog-left-fans").load("/blog/leftFans",{id:'${blog.id}'},function(){}).fadeIn();
	});
  </script>