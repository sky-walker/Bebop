<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div id="fav_add_div_hide">
		<input type="hidden" name="id" value="${id}" id="fav_hidden_id">
	    <input type="hidden" name="type" value="${type}" id="fav_hidden_type">
	  	<div id="fav_add_div">
	  	  <div class="input-prepend ">
			  <span class="add-on">标签*</span>
			  <input name="tags" type="text" style="width:260px" id="favorite_add_tags">
		  	  <button class="btn" type="button" onclick="javascript:getTags()">
			  	<a id="TagsSwitcher" status="off">选择标签↓</a>
			  </button>
		  </div>
		  <p style="text-indent:50px;" class="text-info">多个标签请以'空格'分开,最多5个标签.</p>
		  <div id="fav_tags_list" style="margin-top:10px;"></div>
	    </div>
	</div>
</body>