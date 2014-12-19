<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>站内消息</title>
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
                  <li class="active">私信会话列表</li>
                </ul>
                <div class="back-right-inner">
                	<div class=" back-right-inner-title">
                    	  <p class="text-info back-right-inner-title"> 共 ${pager.totalRecords} 个联系人，每页显示 ${pager.pageSize} 个，共 ${pager.totalPages} 页
                              <span style="float:right;display: inline-block;">
                                <a href="javascript:send_msg(-1)"><img src="/images/pm_reply.gif">+发送消息</a>
                              </span>
                          </p>
                    </div>
                    <!-- pm-item start ================================================== -->
                    <#list pager.resultList as entity>
	                    <div class="pm-item" id="pm-item-${entity.lastMessage.friend.id}">
	                    	<table>
	                          <tr>
	                            <td rowspan="2" valign="top" width="50px">
	                            	<div  class="pm-item-head">
	                                	<a href="/blog/${entity.lastMessage.friend.name}" target="blank"  class="thumbnail">
	                                    	<img src="${entity.lastMessage.friend.portraint}"  style="width:40px;height:40px;">
	                                	</a>
	                                </div>
	                            </td>
	                            <td colspan="2" width="800px">
	                                 <div class="pm-item-summary">
	                                    <#if currentUser.id==entity.lastMessage.sender.id>
								    		发送给:
								    		<a href="/blog/${entity.lastMessage.friend.name}" target="blank">
								    			@${entity.lastMessage.friend.nickname}
								    		</a>
								    		:
								    		${entity.lastMessage.content?html}
								    	<#else>
								    		<a href="/blog/${entity.lastMessage.friend.name}" target="blank">
									    		@${entity.lastMessage.friend.nickname}
									    	</a>
									    	:
									    	${entity.lastMessage.content?html}
								    	</#if>
	                                </div>
	                            </td>
	                          </tr>
	                          <tr>
	                            <td>
	                            	<span class="timeago muted" title="${entity.lastMessage.createTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.lastMessage.createTime}</span>
	                            	<#if (entity.newMsgNum>0)>
						    			<img src="/images/new.gif" />
						    			<font color='#900;'>有${entity.newMsgNum}条新消息</font>
						    		</#if>
	                            </td>
	                            <td align="right">
	                            	<a href="javascript:send_msg(${entity.lastMessage.friend.id})">快速回复 </a>| 
	                            	<a href="/admin/messages/private/detail/${entity.lastMessage.friend.id}" id="pm_detail_bt_${entity.lastMessage.friend.id}">共<strong>${entity.msgCount}</strong>条记录</a>| 
	                            	<a href="javascript:del_pm_friend(${entity.lastMessage.friend.id},'${entity.lastMessage.friend.blogs.name}')">删除</a>
	                            </td>
	                          </tr>
							</table>
	                    </div>
                    </#list>
                    <!-- pm-item end ================================================== -->
                    <#-- 分页标签 BEGIN -->
		    	    <#import "/share/pager_.ftl" as p>
				    <@p.page pager=pager link="/admin/messages/private?" showPageNum=9 anchor=""/>
				    <#-- 分页标签 END -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script type="text/javascript">
			function del_pm_friend(id,name) {
				if(confirm("你确认要清除与‘"+name+"’的所有留言信息吗？")) {
					ajax_post_json("/admin/messages/private/delbyfriend",{id:id},function(data) {
						if(data.status) {
							$("#pm-item-"+id).fadeOut();
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
