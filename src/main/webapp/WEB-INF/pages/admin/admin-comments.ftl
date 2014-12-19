<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>评论管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  	<!-- header start ================================================== -->
	<#include "/share/top.ftl" />
	<!-- header end ================================================== -->
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="row">
      <div class="container">
          <!-- back-left start ================================================== -->
          <div class="span2">
            <div class="back-left">
                <ul class="nav nav-tabs nav-stacked">
                	<li ><a href="/admin/home">个人中心<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/post/new">发表文章<i class="icon-chevron-right"></i></a></li>
                    <li><a href="/admin/posts">文章管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/drafts">草稿箱&nbsp;&nbsp;<span class="badge badge-info">${currentUser.blogs.draftsNum}</span><i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/post-categories">分类管理<i class="icon-chevron-right"></i></a></li>
                    <li class="active"><a href="/admin/comments">博客评论<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/favorites">收藏管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/concern">关注管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/visits">访客记录<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/privacy">隐私管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/profile">个人资料<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/portraint">头像管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/password">登陆密码<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/email">登陆邮箱<i class="icon-chevron-right"></i></a></li>
               </ul>
            </div>
          </div>
          <!-- back-left end ================================================== -->
          
          
          <!-- back-right start ================================================== -->
          <div class="span9 board back-right">
          		<ul class="breadcrumb" style="margin-bottom: 5px;">
                  <#include "/share/admin-nav.ftl" />
                  <li class="active">博客评论</li>
                </ul>
                <div class="back-right-inner">
                    <!-- pm-item start ================================================== -->
                    <ul class="nav nav-tabs">
                      <li class="active">
                        <a href="/admin/comments">博客评论&nbsp;<span class="badge badge-info">${pager.totalRecords}</span></a>
                      </li>
                      <li>
                        <a href="/admin/comments/my">我的评论</a>
                      </li>
                      <li>
                        <a href="/admin/comments/trash">垃圾评论</a>
                      </li>
                    </ul>
                    <!-- pm-item start ================================================== -->
                    <#list pager.resultList as entity>
                    <div class="pm-item">
				    	<table>
				          <tr>
				            <td rowspan="2" valign="top" width="50px">
				            	<div  class="pm-item-head">
				                	<a href="/blog/${entity.users.name}" target="blank" class="thumbnail">
				                		<img src="${entity.users.portraint}" border="0" style="width:40px;height:40px;">
				                	</a>
				                </div>
				            </td>
				            <td colspan="2" width="800px">
				                 <div class="pm-item-summary">
				                  <p>
					                   <a href="/blog/${entity.users.name}" target="_blank">@${entity.users.nickname}</a> 
					                   	发表于：
					                   <a href="/blog/${entity.blogs.users.name}/${entity.posts.id}#comments" target="_blank" title="${entity.posts.title}">
					                   		${entity.posts.title}
					                   </a>
				                  </P> 
				                  <p>
				                  		<#if entity.parent.id?exists>
						        			<p>回复来自<a href="/blog/${entity.parent.users.name}" target="blank"> @${entity.parent.users.nickname} </a>的评论:"${entity.parent.content}"</p>
						        		</#if>
						        		${jsoupUtils.commentHtmlProcess(entity.content,"25","25")}
				                  </p>
				                </div>
				            </td>
				          </tr>
				          <tr>
				            <td>
						    	<span class="timeago muted" title="${entity.createTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.createTime}</span>
				            </td>
				            <td align="right">
				            	<#if entity.visible==1>
					          		<span id="invisible_${entity.id}">
						          		<span class="gray">显示&nbsp;</span>|
						          		<a href="javascript:invisible_comment(${entity.id})" title="标记为垃圾评论">垃圾&nbsp;</a>|
					          		</span>
					          		<span id="visible_${entity.id}" style="display:none;">
						          		<a href="javascript:visible_comment(${entity.id})" >显示&nbsp;</a>|
						          		<span class="gray">垃圾&nbsp;</span>|
					          		</span>
					          	<#elseif entity.visible==0>
					          		<span id="visible_${entity.id}">
						          		<a href="javascript:visible_comment(${entity.id})" >显示&nbsp;</a>|
						          		<span class="gray">垃圾&nbsp;</span>|
					          		</span>
					          		<span id="invisible_${entity.id}" style="display:none;">
						          		<span class="gray">显示&nbsp;</span>|
						          		<a href="javascript:invisible_comment(${entity.id})" title="标记为垃圾评论">垃圾&nbsp;</a>|
					          		</span>
					          	</#if>
					          	<a href="javascript:del_comment(${entity.id})">删除</a>
				            </td>
				          </tr>
						</table>
				    </div>
				    </#list>
                    <!-- pm-item end ================================================== -->
                    <#-- 分页标签 BEGIN -->
		    	    <#import "/share/pager_.ftl" as p>
				    <@p.page pager=pager link="/admin/comments?" showPageNum=9 anchor=""/>
				    <#-- 分页标签 END -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script type="text/javascript">
			function visible_comment(id) {
				ajax_post_json("/admin/comment/show",{id:id},function(data) {
					if(data.status) {$("#visible_"+id).hide();$("#invisible_"+id).show();}
				});
			}
			function invisible_comment(id) {
				ajax_post_json("/admin/comment/hide",{id:id},function(data) {
					if(data.status) {$("#invisible_"+id).hide();$("#visible_"+id).show();}
				});
			}
			function del_comment(id) {
				if(confirm("确定要删除此评论吗?")) {
					ajax_post_json("/admin/comment/del",{id:id},function(data) {
						if(data.status) {location.reload();}
					});
				}
			}
		</script>	
      </div>
   </div>
   <!-- main end   ================================================== -->
   
   	<!-- footer start   ================================================== -->
   	<#include "/share/foot.ftl" />
   	<!-- footer end   ================================================== -->
</body>
</html>
