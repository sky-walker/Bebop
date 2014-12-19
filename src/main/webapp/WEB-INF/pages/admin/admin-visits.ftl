<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我的访客</title>
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
                    <li><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/favorites">收藏管理<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/concern">关注管理<i class="icon-chevron-right"></i></a></li>
                    <li class="active"><a href="/admin/visits">访客记录<i class="icon-chevron-right"></i></a></li>
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
                  <li class="active">我的访客</li>
                </ul>
                <div class="back-right-inner">
                    <!-- visits start ================================================== -->
                    <ul class="nav nav-tabs">
                      <li class="active">
                        <a href="/admin/visits">我的访客&nbsp;<span class="badge badge-info">${pager.totalRecords}</span></a>
                      </li>
                      <li><a href="/admin/foots">我的足迹</a></li>
                    </ul>
                    <div class="divbord">
                    	<table class="table table-hover">
	                      <thead>
	                        <tr class="fc-008">
	                          <th width="5%">#</th>
	                          <th width="20%">访客</th>
	                          <th width="15%">访问时间</th>
	                          <th width="30%">最后访问地址</th>
	                          <th width="20%">当前状态</th>
	                          <th width="10%">操作</th>
	                        </tr>
	                      </thead>
	                      <tbody>
	                      	<#list pager.resultList as entity>
		                        <tr id="${entity.id}">
		                          <td>
		                          	<div style="margin-top:-3px;">
			                          	<a href="/blog/${entity.visitor.name}" target="_blank">
			                          		<img src="${entity.visitor.portraint}?imageView/1/w/30/h/30" class="img-rounded">
			                          	</a>
		                          	</div>
		                          </td>
		                          <td>
		                          	<a href="/blog/${entity.visitor.name}" target="_blank">
		                          		${entity.visitor.nickname}
		                          	</a>
		                          </td>
		                          <td><i class="icon-time"></i>&nbsp;<span class="timeago muted" title="${entity.visittime?string('yyyy-MM-dd HH:mm:ss')}">${entity.visittime}</span></td>
		                          <td><a href="${entity.visiturl}" target="_blank">${entity.visiturl}</a></td>
		                          <td>
		                          	<#assign ose_userstatus=JspTaglibs["/WEB-INF/UserStatusTag.tld"]/>
	          						<@ose_userstatus.userStatus uid='${entity.visitor.id}'/>
		                          </td>
		                          <td class="visits-remove" name="${entity.id}"><a href="javascript:;" title="删除"><i class="icon-remove"></a></i></td>
		                        </tr>
	                        </#list>
	                      </tbody>
	                    </table>
	                    <#-- 分页标签 BEGIN -->
					    <#import "/share/pager_.ftl" as p>
					    <@p.page pager=pager link="/admin/visits?" showPageNum=9 anchor=""/>
					    <#-- 分页标签 END -->
	                    <!-- visits end ================================================== -->
				    </div>
                </div>
          </div>
          <!-- back-right end ================================================== -->
          <script type="text/javascript">
      		$(function() {
      			$(".visits-remove").click(function() {
      				var id = $(this).attr("name");
      				if(id!=null && id!="") {
      					if(confirm("确定要删除此访客记录?")) {
							ajax_post_json("/admin/visit/del",{id:id},function(data) {
								if(data.status) {
									$("#"+id).fadeOut();
								}					
							});
						}
      				}
      			});
      		});
		  </script>
      </div>
   </div>
   <!-- main end   ================================================== -->
   
   <!-- footer start   ================================================== -->
   <#include "/share/foot.ftl" />
   <!-- footer end   ================================================== -->
</body>
</html>
