<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${user.nickname} 的最近动态</title>
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
                    <!-- post-item start ================================================== -->
                    <div>
                    	<#if pager.totalRecords<=0>
                    		<div class="datanull">
                    			暂无动态
                    		</div>
                    	<#else>
                    		<div class="divbord">
	                    		<h6 style="margin-left:15px;">@${user.nickname}&nbsp;&nbsp;的最近动态</h6>
                    			<#list pager.resultList as entity>
								   <#include "/share/blog-feed-template.ftl" />
							    </#list>
                			</div>
                    	</#if>
                    	<#-- 分页标签 BEGIN -->
					    <#import "/share/pager_.ftl" as p>
					    <@p.page pager=pager link="/blog/${user.name}/feeds?" showPageNum=9 anchor=""/>
					    <#-- 分页标签 END -->
				    </div>
                    <!-- post-item end ================================================== -->
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
