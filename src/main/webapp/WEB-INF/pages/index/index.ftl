<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Bebeop系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <!-- header start ================================================== -->
    <#include "/share/front-top.ftl" />
   	<!-- header end ================================================== -->
   <!-- banner start ================================================== -->
   <div class="blog-banner">
      <div class="blog-banner-uname">
      	<table>
      		<tr><td>Welcome to&nbsp;</td><td><img src="http://bebop.qiniudn.com/images/icons/glyphicons_385_blogger.png?imageView/2/w/30"/></td><td>ebop!</td></tr>
      	</table>
      </div>
   </div>
   <!-- banner end ================================================== -->
   <#compress>
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="row">
      <div class="container" style="width:1170px">
      
      	<!-- users start ================================================== -->
         <div class="span2" id="users">
			<blockquote>
				<p><img src="http://bebop.qiniudn.com/images/icons/glyphicons_043_group.png">&nbsp;&nbsp;<strong>最近登录用户</strong></p>
			</blockquote>
				<ul class="unstyled">
					<div class="list-widget">
				      <li>
				        <p><h6><img src="http://bebop.qiniudn.com/images/icons/glyphicons_385_blogger.png?imageView/2/w/20" >&nbsp;&nbsp;<a href="/blog/${admin.name}" target="_blank" title="${admin.nickname}">@${admin.nickname}</a></h6></p>
				        <a class="thumbnail pull-left" href="/blog/${admin.name}" style="width:45px;height:45px;" title="${admin.nickname}"  target="_blank"><img src="${admin.portraint}"  style="width:45px;height:45px;"></a>
				        <p>&nbsp;&nbsp;
				        	<#assign ose_userstatus=JspTaglibs["/WEB-INF/UserStatusTag.tld"]/>
				          	<@ose_userstatus.userStatus uid='${admin.id}'/> 
				        </p>
				        <p class="muted">&nbsp;&nbsp;登录：<span class="timeago muted" title="${admin.thisLoginTime?string('yyyy-MM-dd HH:mm:ss')}">${user.thisLoginTime}</span></p>
				        <p><button type="button" class="btn btn-success" onclick="javascript:send_msg(${admin.id})"><i class="icon-envelope icon-white"></i>&nbsp;有问题请给我留言</button></p>
				      </li>
				  </div>
				  <p></p>
				</ul>
				
            	<ul class="unstyled" id="usersitems">
            		<#-- 新加入代码 -->
            		<#list usersPage.resultList as user>
						 <div class="list-widget">
						      <li>
						        <p><h6><a href="/blog/${user.name}" target="_blank" title="${user.nickname}">@${user.nickname}</a></h6></p>
						        <a class="thumbnail pull-left" href="/blog/${user.name}" title="${user.nickname}"  target="_blank"><img src="${user.portraint}"  style="width:45px;height:45px;"></a>
						        <p>&nbsp;&nbsp;
						        	<#assign ose_userstatus=JspTaglibs["/WEB-INF/UserStatusTag.tld"]/>
						          	<@ose_userstatus.userStatus uid='${user.id}'/> 
						        </p>
						        <p class="muted">&nbsp;&nbsp;登录：<span class="timeago muted" title="${user.thisLoginTime?string('yyyy-MM-dd HH:mm:ss')}">${user.thisLoginTime}</span></p>
						      </li>
						  </div>
					</#list>
            		<#-- 新加入代码 -->
            		
                </ul>
                <div id="usersloading" class="loading">正在加载...</div>
        </div>
       
        <!-- users end ================================================== -->
        
        <!-- feeds start ================================================== -->
        <div class="span4">
			<blockquote>
				<p><img src="http://bebop.qiniudn.com/images/icons/glyphicons_244_conversation.png">&nbsp;&nbsp;<strong>用户最近动态</strong></p>
			</blockquote>
            	<ul class="unstyled" id="feedsitems">
            		<#-- 新加入代码 -->
	            		<#list feedsPage.resultList as entity>
						<#-- 用户注册加入-->
						<#if entity.optType=0>
							<div class="list-widget">
								<p></p>
						      	<li>
							    	<table>
							          <tr>
							            <td rowspan="2" valign="top">
							            	<div  class="feed-item-head">
							                	<a href="/blog/${entity.who.name}" target="blank"  class="thumbnail">
							                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25" >
							                	</a>
							                </div>
							            </td>
							            <td colspan="2" width="100%">
							                 <div class="pm-item-summary">
							                  <p>
								                   <a href="/blog/${entity.who.name}" target="_blank" >@${entity.who.nickname}</a> 
								                   &nbsp;加入了Bebop
							                  </P> 
							                  <p>
							                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
							                  </p>
							                </div>
							            </td>
							          </tr>
									</table>
								</li>
							 </div>
						</#if>
						
						<#-- 用户发表文章-->
						<#if entity.optType=1>
							<div class="list-widget">
								<p></p>
						      	<li>
							    	<table>
							          <tr>
							            <td rowspan="2" valign="top" width="25">
							            	<div  class="feed-item-head">
							                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
							                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25" >
							                	</a>
							                </div>
							            </td>
							            <td colspan="2" width="100%">
							                 <div class="pm-item-summary">
							                  <p>
								                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
								                   &nbsp;发表了博客文章：<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a>
							                  </P> 
							                  <p>
							                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
							                  </p>
							                </div>
							            </td>
							          </tr>
									</table>
								</li>
							 </div>
						</#if>
						
						<#-- 用户发表评论-->
						<#if entity.optType=2>
							<div class="list-widget">
								<p></p>
						      	<li>
							    	<table>
							          <tr>
							            <td rowspan="2" valign="top" width="35">
							            	<div  class="feed-item-head">
							                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
							                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25" >
							                	</a>
							                </div>
							            </td>
							            <td colspan="2" width="100%">
							                 <div class="pm-item-summary">
							                  <p>
								                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
								                   &nbsp;在博客：<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}#comments" target="_blank">${entity.posts.title}</a>
								                   中发表了评论
							                  </P> 
							                  <p>
							                  	${entity.comments.content}
							                  </p>
							                  <p>
							                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
							                  </p>
							                </div>
							            </td>
							          </tr>
									</table>
								</li>
							 </div>
						</#if>
						
						<#-- 用户关注-->
						<#if entity.optType=3>
							<div class="list-widget">
								<p></p>
						      	<li>
							    	<table>
							          <tr>
							            <td rowspan="2" valign="top" width="35">
							            	<div  class="feed-item-head">
							                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
							                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
							                	</a>
							                </div>
							            </td>
							            <td colspan="2" width="100%">
							                 <div class="pm-item-summary">
							                  <p>
								                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
								                   &nbsp;关注了  <a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a> 的动态
							                  </P> 
							                  <p>
							                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
							                  </p>
							                </div>
							            </td>
							          </tr>
									</table>
								</li>
							 </div>
						</#if>
						
						<#-- 用户收藏文章-->
						<#if entity.optType=4>
							<div class="list-widget">
								<p></p>
						      	<li>
							    	<table>
							          <tr>
							            <td rowspan="2" valign="top" width="35">
							            	<div  class="feed-item-head">
							                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
							                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
							                	</a>
							                </div>
							            </td>
							            <td colspan="2" width="100%">
							                 <div class="pm-item-summary">
							                  <p>
								                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
								                   &nbsp;收藏了  <a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a> 
								                   的博客文章  <a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a> 
							                  </P> 
							                  <p>
							                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
							                  </p>
							                </div>
							            </td>
							          </tr>
									</table>
								</li>
							 </div>
						</#if>
						
						<#-- 用户回复评论-->
						<#if entity.optType=5>
							<div class="list-widget">
								<p></p>
						      	<li>
							    	<table>
							          <tr>
							            <td rowspan="2" valign="top" width="35">
							            	<div  class="feed-item-head">
							                	<a href="/blog/${entity.who.name}" target="blank" class="thumbnail">
							                		<img src="${entity.who.portraint}?imageView/1/w/25/h/25">
							                	</a>
							                </div>
							            </td>
							            <td colspan="2" width="100%">
							                 <div class="pm-item-summary">
							                  <p>
								                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 
								                   &nbsp;在博客：<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}#comments" target="_blank">${entity.posts.title}</a>
								                  	 中回复了 <a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a> 的评论
							                  </P>
							                  <p>
							                  	<a href="/blog/${entity.whose.name}" target="_blank">@${entity.whose.nickname}</a>说:
										    	<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
										    	<@ose_htmlSummary.htmlSummary html='${entity.comments.parent.content}'/>
							                  </p> 
							                  <p>
							                  	<a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a>回复:
										    	<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
										    	<@ose_htmlSummary.htmlSummary html='${entity.comments.content}'/>
							                  </p>
							                  <p>
							                  	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
							                  </p>
							                </div>
							            </td>
							          </tr>
									</table>
								</li>
							 </div>
						</#if>
						<p></p>
					</#list>
				<#-- 新加入代码 -->
                </ul>
                <div class="loading" id="feedsloading">正在加载...</div>
        </div>
        <!-- feeds end ================================================== -->
        
        <!-- posts start ================================================== -->
        <div class="span6">
			<blockquote>
				<p><img src="http://bebop.qiniudn.com/images/icons/glyphicons_184_volume_up.png">&nbsp;&nbsp;<strong>公告</strong></p>
			</blockquote>
				<ul class="unstyled" id="publicpostsitems">
					<#list publicPosts.resultList as post>
						<div class="list-widget">
							<p></p>
							 <li>
					        	<p><i class="icon-volume-up"></i><a href="/blog/${post.blogs.users.name}/${post.id}" target="_blank">${post.title}</a> </p>
					            <p>
					            	<span>
					                	<span class="timeago muted" title="${post.createTime?string('yyyy-MM-dd HH:mm:ss')}">${post.createTime}</span>
					                	<span class="muted">by @${post.blogs.users.nickname}</span>
					                </span>
					            	<span class="muted pull-right">${post.commentsNum}回/${post.viewsNum}阅</span>
					            </p> 
						      </li>
					      </div>
					      <p></p>
				   </#list>
                </ul>
                <br />
            <blockquote>
				<p><img src="http://bebop.qiniudn.com/images/icons/glyphicons_235_pen.png">&nbsp;&nbsp;<strong>博客文章列表</strong></p>
			</blockquote>
            	<ul class="unstyled" id="postsitems">
            		<#-- 新加入代码 -->
            		<#list postsPage.resultList as post>
						<div class="list-widget">
							<p></p>
							 <li>
					        	<p><i class="icon-file"></i><a href="/blog/${post.blogs.users.name}/${post.id}" target="_blank">${post.title}</a> </p>
					            <p>
					            	<span>
					                	<span class="timeago muted" title="${post.createTime?string('yyyy-MM-dd HH:mm:ss')}">${post.createTime}</span>
					                	<span class="muted">by @${post.blogs.users.nickname}</span>
					                </span>
					            	<span class="muted pull-right">${post.commentsNum}回/${post.viewsNum}阅</span>
					            </p> 
						      </li>
					      </div>
					     <p></p>
					</#list>
            		<#-- 新加入代码 -->
            		
            		
                </ul>
                <div class="loading" id="postsloading">正在加载...</div>
        </div>
        <!-- posts end ================================================== -->
        
      </div>
   </div>
   <!-- main end   ================================================== -->
   <script type="text/javascript">
		$(document).ready(function() {
			$(window).scroll(loadData);
		});
   </script>
   <script src="http://bebop.qiniudn.com/scripts/scrollpage.js"></script>
   </#compress>
    <!-- foot start ================================================== -->
    <#include "/share/foot.ftl" />
   	<!-- foot end ================================================== -->
</body>
</html>
