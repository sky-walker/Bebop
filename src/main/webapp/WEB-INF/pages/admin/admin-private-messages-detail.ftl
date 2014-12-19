<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>与${friend.nickname}的私信记录</title>
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
                    <li ><a href="/admin/comments">博客评论<i class="icon-chevron-right"></i></a></li>
                    <li class="active"><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
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
                  <li class=""><a href="/admin/messages">私信</a><span class="divider">/</span></li>
                  <li class="active">@${friend.nickname}</li>
                </ul>
                <div class="back-right-inner">
                	<div class=" back-right-inner-title">
                    	 <p class="text-info">共 ${pager.totalRecords} 条记录，每页显示  ${pager.pageSize} 个，共 ${pager.totalPages} 页
                            <span style="margin-left:100px;display: inline-block;">
                              <a href="javascript:send_msg(${friend.id})" title="回复${friend.nickname}"><img src="/images/pm_reply.gif">@${friend.nickname}</a>
                            </span>
                            <span style="float:right;display: inline-block;">
                              <a href="/admin/messages"><i class="icon-backward"></i> 返回 </a>
                            </span>
                        </p>
                    </div>
                    <!-- pm-item start ================================================== -->
                    <#list pager.resultList as entity>
	                    <div  id="pm-${entity.id}" class="pm-item">
	                    	<table>
	                          <tr>
	                            <td rowspan="2" valign="top" width="50px">
	                            	<div  class="pm-item-head">
	                            		<#if currentUser.id==entity.sender.id>
									    	<a href="/blog/${entity.user.name}" target="blank">
									    		<img src="${entity.user.portraint}" class="img-rounded"  style="width:35px;height:35px;">
									    	</a>
									    <#else>
									    	<a href="/blog/${entity.friend.name}" target="blank">
									    		<img src="${entity.friend.portraint}" class="img-rounded"  style="width:35px;height:35px;">
									    	</a>
								    	</#if>
	                                </div>
	                            </td>
	                            <td colspan="2" width="800px">
	                                 <div class="pm-item-summary">
	                                    <#if currentUser.id==entity.sender.id>
								    		<a href="/blog/${entity.user.name}" target="blank">
									    		@${entity.user.nickname}
									    	</a>
								    	<#else>
								    		<a href="/blog/${entity.friend.name}" target="blank">
									    		@${entity.friend.nickname}
									    	</a>
								    	</#if>
								    	:
								    	${entity.content?html}
	                                </div>
	                            </td>
	                          </tr>
	                          <tr>
	                            <td>
	                            	<span class="timeago muted" title="${entity.createTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.createTime}</span>
	                            	<#if entity.readStatus==0 && currentUser.id==entity.receiver.id>
						    			<img src="/images/new.gif" />
						    		</#if>
						    		
	                            </td>
	                            <td align="right">
	                            	<#if currentUser.id==entity.receiver.id>
							    		<a href="javascript:send_msg(${friend.id})">快速回复 </a>| 
							    	</#if>
							    	<a href="javascript:del_pm(${entity.id})">删除</a>
	                            </td>
	                          </tr>
							</table>
	                    </div>
	                </#list>
                    <!-- pm-item end ================================================== -->
                    <#-- 分页标签 BEGIN -->
		    	    <#import "/share/pager_.ftl" as p>
				    <@p.page pager=pager link="/admin/messages/private/detail/${friend.id}?" showPageNum=9 anchor=""/>
				    <#-- 分页标签 END -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script type="text/javascript">
			function del_pm(id) {
				if(confirm("确定删除此留言吗?")) {
					ajax_post_json("/admin/messages/private/del",{id:id},function(data) {
						if(data.status) {
							$("#pm-"+id).fadeOut();
						}
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
