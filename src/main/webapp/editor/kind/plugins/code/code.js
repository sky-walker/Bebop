/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

// google code prettify: http://google-code-prettify.googlecode.com/
// http://google-code-prettify.googlecode.com/

KindEditor.plugin('code', function(K) {
	var self = this, name = 'code';
	self.clickToolbar(name, function() {
		var lang = self.lang(name + '.'),
			html = ['<div style="padding:10px 20px;">',
				'<div class="ke-dialog-row">',
				'<select class="ke-code-type">',
				'<option value="Java">Java</option>',
				'<option value="SQL">SQL</option>',
				'<option value="C/C++">C/C++</option>',
				'<option value="HTML">HTML</option>',
				'<option value="CSS">CSS</option>',
				'<option value="PHP">PHP</option>',
				'<option value="Perl">Perl</option>',
				'<option value="Python">Python</option>',
				'<option value="Ruby">Ruby</option>',
				'<option value="ASP/VB">ASP/VB</option>',
				'<option value="C#">C#</option>',
				'<option value="XML">XML</option>',
				'<option value="JavaScript">JavaScript</option>',
				'<option value="Scala">Scala</option>',
				'</select>',
				'</div>',
				'<textarea class="ke-textarea" style="width:408px;height:260px;"></textarea>',
				'</div>'].join(''),
			dialog = self.createDialog({
				name : name,
				width : 450,
				title : self.lang(name),
				body : html,
				yesBtn : {
					name : self.lang('yes'),
					click : function(e) {
						if(textarea.val()=="") {
							alert("请输入程序代码或脚本");
							return;
						}
						var type = K('.ke-code-type', dialog.div).val(),
							code = textarea.val(),
							cls = type === '' ? '' :  '' + type,
							html ='<pre class="brush:java">' + K.escape(code) + '</pre></br>';
						self.insertHtml(html).hideDialog().focus();
					}
				}
			}),
			textarea = K('textarea', dialog.div);
		textarea[0].focus();
	});
});
