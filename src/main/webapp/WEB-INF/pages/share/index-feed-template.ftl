 <#-- 用户注册加入-->
<#if entity.optType=0>
	<table width="100%" class="pm_table" align="center">
	  	  <tr><td colspan="3" height="6px;"></td></tr>
	  	  <tr>
		    <td width="25px" height="25px">
		    	<a href="/blog/${entity.who.blogs.name}" target="_blank">
	    			<img src="${entity.who.portraint}" width="25px" height="25px" border="0">
	    		</a>
		    </td>
		    <td align="left" class="action">
		    	&nbsp;<a href="/blog/${entity.who.blogs.name}" target="_blank">@${entity.who.blogs.name}</a> 加入了 Bebeop
		    </td>
		    <td align="right">
		    	<span class="timeago" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    </td>
		  </tr>
		  <tr><td colspan="3" class="pm_table_td" height="6px;"></td></tr>
	 </table>
</#if>
<#if entity.optType=1>
	<table width="100%" class="pm_table" align="center">
	  	  <tr><td colspan="3" height="6px;"></td></tr>
	  	  <tr>
		    <td width="25px" height="25px">
		    	<a href="/blog/${entity.who.blogs.name}" target="blank">
	    			<img src="${entity.who.portraint}" width="25px" height="25px" border="0">
	    		</a>
		    </td>
		    <td align="left" class="action">
		    	&nbsp;<a href="/blog/${entity.who.blogs.name}" target="_blank">@${entity.who.blogs.name}</a> 发表了博客文章 : <a href="/blog/${entity.posts.blogs.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a>
		    </td>
		    <td align="right">
		    	<span class="timeago" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    </td>
		  </tr>
		  <tr>
		    <td></td>
		    <td colspan="2" class="action_summury"><#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/><@ose_htmlSummary.htmlSummary html='${entity.posts.content}'/></td>
		  </tr>
		  <tr><td colspan="3" class="pm_table_td" height="6px;"></td></tr>
	 </table>
</#if>
<#if entity.optType=2>
	<table width="100%" class="pm_table" align="center">
  	  	  <tr><td colspan="3" height="6px;"></td></tr>
  	  	  <tr>
		    <td width="25px" height="25px">
		    	<a href="/blog/${entity.who.blogs.name}" target="blank">
	    			<img src="${entity.who.portraint}" width="25px" height="25px" border="0">
	    		</a>
		    </td>
		    <td align="left" class="action">
		    	&nbsp;<a href="/blog/${entity.who.blogs.name}" target="_blank">@${entity.who.blogs.name}</a> 在博客 <a href="/blog/${entity.posts.blogs.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a> 发表评论
		    </td>
		    <td align="right">
		    	<span class="timeago" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    </td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td colspan="2" class="action_summury">
		    	<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
		    	<@ose_htmlSummary.htmlSummary html='${entity.comments.content}'/>
		    </td>
		  </tr>
		  <tr><td colspan="3" class="pm_table_td" height="6px;"></td></tr>
	 </table>
</#if>
<#if entity.optType=3>
	<table width="100%" class="pm_table" align="center">
  	  	  <tr><td colspan="3" height="6px;"></td></tr>
  	  	  <tr>
		    <td width="25px" height="25px">
		    	<a href="/blog/${entity.who.blogs.name}" target="blank">
	    			<img src="${entity.who.portraint}" width="25px" height="25px" border="0">
	    		</a>
		    </td>
		    <td align="left" class="action">
		    	&nbsp;<a href="/blog/${entity.who.blogs.name}" target="_blank">@${entity.who.blogs.name}</a> 关注了 <a href="/blog/${entity.whose.blogs.name}" target="_blank">${entity.whose.blogs.name}</a> 的动态
		    </td>
		    <td align="right">
		    	<span class="timeago" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    </td>
		  </tr>
		  <tr><td colspan="3" class="pm_table_td" height="6px;"></td></tr>
	 </table>
</#if>
<#if entity.optType=4>
	<table width="100%" class="pm_table" align="center">
  	  	  <tr><td colspan="3" height="6px;"></td></tr>
  	  	  <tr>
		    <td width="25px" height="25px">
		    	<a href="/blog/${entity.who.blogs.name}" target="blank">
	    			<img src="${entity.who.portraint}" width="25px" height="25px" border="0">
	    		</a>
		    </td>
		    <td align="left" class="action">
		    	&nbsp;<a href="/blog/${entity.who.blogs.name}" target="_blank">@${entity.who.blogs.name}</a> 收藏了 <a href="/blog/${entity.whose.blogs.name}" target="_blank">@${entity.whose.blogs.name}</a> 的博客文章：<a href="/blog/${entity.posts.blogs.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a>
		    </td>
		    <td align="right">
		    	<span class="timeago" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    </td>
		  </tr>
		  <tr><td colspan="3" class="pm_table_td" height="6px;"></td></tr>
	 </table>
</#if>
<#if entity.optType=5>
	<table width="100%" class="pm_table" align="center">
  	  	  <tr><td colspan="3" height="6px;"></td></tr>
  	  	  <tr>
		    <td width="25px" height="25px">
		    	<a href="/blog/${entity.who.blogs.name}" target="blank">
	    			<img src="${entity.who.portraint}" width="25px" height="25px" border="0">
	    		</a>
		    </td>
		    <td align="left" class="action">
		    	&nbsp;<a href="/blog/${entity.who.blogs.name}" target="_blank">@${entity.who.blogs.name}</a> 在博客文章：<a href="/blog/${entity.posts.blogs.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a>中回复了 <a href="/blog/${entity.whose.blogs.name}" target="_blank">@${entity.whose.blogs.name}</a> 的评论
		    </td>
		    <td align="right">
		    	<span class="timeago" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    </td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td colspan="2" class="action_summury">
		    	&nbsp;<a href="/blog/${entity.whose.blogs.name}" target="_blank">@${entity.whose.blogs.name}说:</a>
		    	<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
		    	<@ose_htmlSummary.htmlSummary html='${entity.comments.parent.content}'/>
		    </td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td colspan="2" class="action_summury">
		    	&nbsp;<a href="/blog/${entity.who.blogs.name}" target="_blank">@${entity.who.blogs.name}回复:</a>
		    	<#assign ose_htmlSummary=JspTaglibs["/WEB-INF/HtmlSummaryTag.tld"]/>
		    	<@ose_htmlSummary.htmlSummary html='${entity.comments.content}'/>
		    </td>
		  </tr>
		  <tr><td colspan="3" class="pm_table_td" height="6px;"></td></tr>
	 </table>
</#if>