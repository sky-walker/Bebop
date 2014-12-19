<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我的通知</title>
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
                    <li class="active"><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
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
                  <#if type==''>
                  	<li class="active">通知</li>
                  </#if>
                  <#if type==2>
                  	<li><a href="/admin/reminds">通知</a> <span class="divider">/</span></li>
                  	<li class="active">博客评论</li>
                  </#if>
                  <#if type==3>
                  	<li><a href="/admin/reminds">通知</a> <span class="divider">/</span></li>
                  	<li class="active">关注我</li>
                  </#if>
                  <#if type==4>
                  	<li><a href="/admin/reminds">通知</a> <span class="divider">/</span></li>
                  	<li class="active">收藏我的文章</li>
                  </#if>
                  <#if type==5>
                  	<li><a href="/admin/reminds">通知</a> <span class="divider">/</span></li>
                  	<li class="active">评论回复</li>
                  </#if>
                </ul>
                <div class="back-right-inner">
                    <!-- pm-item start ================================================== -->
                    <ul class="nav nav-tabs">
                      <li <#if type==''>class="active"</#if>>
                        <a href="/admin/reminds">全部&nbsp;<#if type==''><span class="badge badge-info">${pager.totalRecords}</span></#if></a>
                      </li>
                      <li <#if type==2>class="active"</#if>><a href="/admin/reminds?type=2">博客评论&nbsp;<#if type==2><span class="badge badge-info">${pager.totalRecords}</span></#if></a></li>
                      <li <#if type==3>class="active"</#if>><a href="/admin/reminds?type=3">关注我&nbsp;<#if type==3><span class="badge badge-info">${pager.totalRecords}</span></#if></a></li>
                      <li <#if type==4>class="active"</#if>><a href="/admin/reminds?type=4">收藏我的文章&nbsp;<#if type==4><span class="badge badge-info">${pager.totalRecords}</span></#if></a></li>
                      <li <#if type==5>class="active"</#if>><a href="/admin/reminds?type=5">评论回复&nbsp;<#if type==5><span class="badge badge-info">${pager.totalRecords}</span></#if></a></li>
                    </ul>
                    <!-- pm-item start ================================================== -->
                    <#list pager.resultList as entity>
		  		  	  <#include "/admin/admin-reminds-temp.ftl" />
				  	</#list>
                    <!-- pm-item end ================================================== -->
                    <#-- 分页标签 BEGIN -->
		    	    <#import "/share/pager_.ftl" as p>
				    <@p.page pager=pager link="/admin/reminds?" showPageNum=9 anchor=""/>
				    <#-- 分页标签 END -->
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
