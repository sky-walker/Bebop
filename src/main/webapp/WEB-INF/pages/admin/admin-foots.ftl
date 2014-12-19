<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我的足迹</title>
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
                  <li class="active">我的足迹</li>
                </ul>
                <div class="back-right-inner">
                    <!-- visits start ================================================== -->
                    <ul class="nav nav-tabs">
                      <li>
                        <a href="/admin/visits">我的访客</a>
                      </li>
                      <li class="active"><a href="/admin/foots">我的足迹</a></li>
                    </ul>
                   	 <div class="foots">
                   	 		<#if pager.totalRecords=0>
                   	 			<div class="datanull">暂无足迹记录 ...</div>
                   	 		<#else>
                   	 			<#list pager.resultList as entity>
			                        <span class="visit-item" >
			                        	<table>
			                              <tr>
			                                <td rowspan="2">
			                                	<a href="/blog/${entity.blogs.users.name}" target="_blank" class="thumbnail">
					                          		<img src="${entity.blogs.users.portraint}"  style="width:35px;height:35px;">
					                          	</a>
			                                </td>
			                                <td><a href="/blog/${entity.blogs.users.name}" target="_blank" >${entity.blogs.users.nickname}</a></td>
			                              </tr>
			                              <tr>
			                                <td><span class="timeago muted" title="${entity.visittime?string('yyyy-MM-dd HH:mm:ss')}">${entity.visittime}</span></td>
			                              </tr>
			                            </table>
			                        </span>
		                        </#list>
                   	 		</#if>
                    </div>
                    <!-- visits end ================================================== -->
                </div>
          </div>
          <!-- back-right end ================================================== -->
      </div>
   </div>
   <!-- main end   ================================================== -->
   
   <!-- footer start   ================================================== -->
   <#include "/share/foot.ftl" />
   <!-- footer end   ================================================== -->
</body>
</html>
