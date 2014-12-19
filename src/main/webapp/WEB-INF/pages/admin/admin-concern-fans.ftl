<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我的粉丝</title>
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
                    <li ><a href="/admin/messages">站内消息<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/reminds">我的通知<i class="icon-chevron-right"></i></a></li>
                    <li ><a href="/admin/favorites">收藏管理<i class="icon-chevron-right"></i></a></li>
                    <li class="active"><a href="/admin/concern">关注管理<i class="icon-chevron-right"></i></a></li>
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
                  <li class="active">我的粉丝</li>
                </ul>
                <div class="back-right-inner">
                    <!-- concern- start ================================================== -->
                    <ul class="nav nav-tabs">
                      <li>
                        <a href="/admin/concern/follows">我的关注</a>
                      </li>
                      <li class="active"><a href="/admin/concern/fans">我的粉丝<span class="badge badge-info">${pager.totalRecords}</span></a></li>
                      <li><a href="/admin/concern/friends">互为关注</a></li>
                      <li><a href="javascript:add_concern()">+添加关注</a></li>
                    </ul>
                    <div class="tabbable tabs-right">
                      <ul class="nav nav-tabs">
                        <li <#if sortby = 'update'>class="active"</#if> ><a href="/admin/concern/fans?sortby=update">最近更新</a></li>
                        <li <#if sortby = 'time'>class="active"</#if> ><a href="/admin/concern/fans?sortby=time">关注时间</a></li>
                        <li <#if sortby = 'login'>class="active"</#if> ><a href="/admin/concern/fans?sortby=login">登录时间</a></li>
                      </ul>
                      <!-- concern-item start ================================================== -->
                       <#list pager.resultList as entity>
				  		  	  <#include "/share/concern-template.ftl" />
					  </#list>
                      <!-- concern-item end ================================================== -->
                      <#-- 分页标签 BEGIN -->
			    	  <#import "/share/pager_.ftl" as p>
					  <@p.page pager=pager link="/admin/concern/fans?" showPageNum=9 anchor=""/>
                    </div>
                    <!-- concern end ================================================== -->
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
