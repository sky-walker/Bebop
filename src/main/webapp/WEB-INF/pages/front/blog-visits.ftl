<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${user.nickname} 的最近访客</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  	<!-- header start ================================================== -->
    <#include "/share/front-top.ftl" />
   	<!-- header end ================================================== -->
   <!-- banner start ================================================== -->
   <#include "/front/blog-banner.ftl" />
   <!-- banner end ================================================== -->
   
   <!-- main start ================================================== -->
   <div id="main">
   	<div class="row">
      <div class="container" style="width:1170px">
          <!-- back-left start ================================================== -->
          <#include "/front/blog-left.ftl" />
          <!-- back-left end ================================================== -->
          <div>
  			<div id='shangxia'>
		    <div id='shang'></div>
		    <div id='xia'></div>
          </div>
          <!-- back-right start ================================================== -->
          <div class="span9 board style="float:right">
                <div class="front-right-inner">
                    <!-- visitsstart ================================================== -->
                    <div class="divbord">
                    	<#if pager.totalRecords<=0>
                    		<div class="datanull">您暂无访客记录</div>
                    	<#else>
							 <table class="table table-hover">
		                      <thead>
		                        <tr class="fc-008">
		                          <th width="10%">#</th>
		                          <th width="20%">访客</th>
		                          <th width="20%">访问时间</th>
		                          <th width="30%">最后访问地址</th>
		                          <th width="20%">当前状态</th>
		                        </tr>
		                      </thead>
		                      <tbody>
		                      	<#list pager.resultList as entity>
			                        <tr id="${entity.id}">
			                          <td>
			                          	<div style="margin-top:-3px;" class="comment-item-head">
				                          	<a href="/blog/${entity.visitor.name}" target="_blank" class="thumbnail">
				                          		<img src="${entity.visitor.portraint}" class="img-rounded" style="width:35px;height:35px;">
				                          	</a>
			                          	</div>
			                          </td>
			                          <td>
			                          	<a href="/blog/${entity.visitor.name}" target="_blank">
			                          		${entity.visitor.nickname}
			                          	</a>
			                          </td>
			                          <td><i class="icon-time"></i>&nbsp;<span class="timeago muted" title="${entity.visittime?string('yyyy-MM-dd HH:mm:ss')}">${entity.visittime?string('yyyy-MM-dd HH:mm:ss')}</span></td>
			                          <td><a href="${entity.visiturl}" target="_blank">${entity.visiturl}</a></td>
			                          <td>
			                          	<#assign ose_userstatus=JspTaglibs["/WEB-INF/UserStatusTag.tld"]/>
		          						<@ose_userstatus.userStatus uid='${entity.visitor.id}'/>
			                          </td>
			                        </tr>
		                        </#list>
		                      </tbody>
		                    </table>
				    <script language='javascript'>
					$(function() {
						$(".timeago").prettyDate();
					});
				    </script>
                    	</#if>
                    </div>
                    <!-- visits end ================================================== -->
                    <#-- 分页标签 BEGIN -->
				    <#import "/share/pager_.ftl" as p>
				    <@p.page pager=pager link="/blog/${user.name}/visits?" showPageNum=9 anchor=""/>
				    <#-- 分页标签 END -->
                </div>
                <div id="bottom"></div>
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
