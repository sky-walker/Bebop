<#-- 用户注册加入-->
<#if entity.optType=0>
	<div class="pm-item">
    	<table>
          <tr>
            <td rowspan="2" valign="top" width="50px">
            	<div  class="pm-item-head">
                	<a href="/blog/${entity.who.name}" target="blank">
                		<img src="${entity.who.portraint}" class="img-rounded" width="35px" height="35px" border="0">
                	</a>
                </div>
            </td>
            <td colspan="2" width="800px">
                 <div class="pm-item-summary">
                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 加入了 Bebeop
                </div>
            </td>
          </tr>
          <tr>
            <td>
		    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    	 
		    	<#if (entity.readStatus==0)>
		    		<img src="/images/new.gif" />
		    	</#if>
            </td>
            <td align="right"></td>
          </tr>
		</table>
    </div>
</#if>
<#-- 博客评论的添加-->
<#if entity.optType=2>
	<div class="pm-item">
    	<table>
          <tr>
            <td rowspan="2" valign="top" width="50px">
            	<div  class="pm-item-head">
                	<a href="/blog/${entity.who.name}" target="blank">
                		<img src="${entity.who.portraint}" class="img-rounded" width="35px" height="35px" border="0">
                	</a>
                </div>
            </td>
            <td colspan="2" width="800px">
                 <div class="pm-item-summary">
                   <p><a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 在你的文章  <a href="/blog/${entity.whose.name}/${entity.posts.id}" target="_blank">${entity.posts.title}</a> 中发表了评论：</p>
                   <p>${entity.comments.content}</p>
                </div>
            </td>
          </tr>
          <tr>
            <td>
		    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    	 
		    	<#if (entity.readStatus==0)>
		    		<img src="/images/new.gif" />
		    	</#if>
            </td>
            <td align="right"></td>
          </tr>
		</table>
    </div>
</#if>
<#-- 添加关注-->
<#if entity.optType=3>
	<div class="pm-item">
    	<table>
          <tr>
            <td rowspan="2" valign="top" width="50px">
            	<div  class="pm-item-head">
                	<a href="/blog/${entity.who.name}" target="blank">
                		<img src="${entity.who.portraint}" class="img-rounded" width="35px" height="35px" border="0">
                	</a>
                </div>
            </td>
            <td colspan="2" width="800px">
                 <div class="pm-item-summary">
                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 关注了您
                </div>
            </td>
          </tr>
          <tr>
            <td>
		    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    	 
		    	<#if (entity.readStatus==0)>
		    		<img src="/images/new.gif" />
		    	</#if>
            </td>
            <td align="right"></td>
          </tr>
		</table>
    </div>
</#if>
<#-- 添加收藏-->
<#if entity.optType=4>
	<div class="pm-item">
    	<table>
          <tr>
            <td rowspan="2" valign="top" width="50px">
            	<div  class="pm-item-head">
                	<a href="/blog/${entity.who.name}" target="blank">
                		<img src="${entity.who.portraint}" class="img-rounded" width="35px" height="35px" border="0">
                	</a>
                </div>
            </td>
            <td colspan="2" width="800px">
                 <div class="pm-item-summary">
                   <a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 收藏了您的博客文章  <a href="/blog/${entity.whose.name}/${entity.posts.id}" target="_blank">  ${entity.posts.title}  </a>
                </div>
            </td>
          </tr>
          <tr>
            <td>
		    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    	 
		    	<#if (entity.readStatus==0)>
		    		<img src="/images/new.gif" />
		    	</#if>
            </td>
            <td align="right"></td>
          </tr>
		</table>
    </div>
</#if>
<#-- 回复评论-->
<#if entity.optType=5>
	<div class="pm-item">
    	<table>
          <tr>
            <td rowspan="2" valign="top" width="50px">
            	<div  class="pm-item-head">
                	<a href="/blog/${entity.who.name}" target="blank">
                		<img src="${entity.who.portraint}" class="img-rounded" width="35px" height="35px" border="0">
                	</a>
                </div>
            </td>
            <td colspan="2" width="800px">
                 <div class="pm-item-summary">
                   <p><a href="/blog/${entity.who.name}" target="_blank">@${entity.who.nickname}</a> 在文章<a href="/blog/${entity.posts.blogs.users.name}/${entity.posts.id}" target="_blank">  ${entity.posts.title}  </a>中回复了你的评论</p>
                   <p>
                   	<a href="/blog/${entity.comments.parent.users.name}">你说</a>：${entity.comments.parent.content}
                   </p>
                   <p>
                   	<a href="/blog/${entity.who.name}" target="_blank">
	    				@${entity.who.nickname}
	    			</a>回复：
		    		${entity.comments.content}
                   </p>
                </div>
            </td>
          </tr>
          <tr>
            <td>
		    	<span class="timeago muted" title="${entity.optTime?string('yyyy-MM-dd HH:mm:ss')}">${entity.optTime}</span>
		    	 
		    	<#if (entity.readStatus==0)>
		    		<img src="/images/new.gif" />
		    	</#if>
            </td>
            <td align="right"></td>
          </tr>
		</table>
    </div>
</#if>
