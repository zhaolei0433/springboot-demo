<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<script type="text/javascript" src="/jslib/utils.js" charset="utf-8"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理系统</title>
<link rel="stylesheet" type="text/css" href="/style/default.css"/>
<link rel="stylesheet" type="text/css" href="/style/common.css"/>
<link rel="stylesheet" href="/style/login.css" type="text/css"/>
<link id="easyuiTheme" rel="stylesheet" href="/jslib/jquery-easyui-1.7.0/themes/gray/easyui.css"
      type="text/css"/>
<link rel="stylesheet" href="/jslib/jquery-easyui-1.7.0/themes/icon.css" type="text/css"/>
<script type="text/javascript" src="/jslib/jquery-easyui-1.7.0/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/jslib/jquery-easyui-1.7.0/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/jslib/utils.js" charset="utf-8"></script>
<!-- cookie插件 -->
<script type="text/javascript" src="/jslib/jquery.cookie.js" charset="utf-8"></script>
<!-- 自己定义的样式和JS扩展 -->
<script type="text/javascript" src="/jslib/common.js" charset="utf-8"></script>

<script type="text/javascript" src="/jslib/menu.js"></script>
<!-- 文件异步上传插件 -->
<!-- <script type="text/javascript" src="/jslib/ajaxfileupload.js" charset="utf-8"></script>-->
 
<script type="text/javascript">
$(document).ready(function(){
	panelLoad();
});
	/*
	*添加选项卡方法
	*/
	function addTab(title,url){
		var tab=$('#tt').tabs('exists',title);
		if(tab){
			//若存在，则直接打开
			$('#tt').tabs('close',title);
		}
		$('#tt').tabs('add',{
			title:title,
			href:url,
			closable:true
		});
	}

	function panelLoad(){
		setPageContent("&nbsp;&nbsp;首页","/page/welcome.html");
	}

	function setPageContent(title,url){
		$('#operator_admin_panel').panel({
			title: title,
			href:url
		});
		return false;	
	}
	/*
	*根据title,选中Accordion对应的面板
	*/
	function selectAccordion(title){
		$('#accordionPanel').accordion('select',title);
	}
	/*
	*刷新时间
	*/
	function showTime(){
		var date=new Date();
		$('#nowTime').html();
		$('#nowTime').html(date.toLocaleString()+"&nbsp;&nbsp;");
		
	}
	setInterval(showTime,1000);
	
	/*
	*检测浏览器窗口大小改变,来改变页面layout大小
	*/
	$(function(){
		$(window).resize(function(){
			$('#cc').layout('resize');
		});

		 $('#skin').combobox({  
			 onChange:function(skin,oldValue){  
				
		            if(skin == "metro"){
		    			$("#themesSkin").attr("href","/jslib/jquery-easyui-1.7.0//themes/metro/easyui.css");
		    		}
		    		if(skin == "default"){
		    			$("#themesSkin").attr("href","/jslib/jquery-easyui-1.7.0//themes/default/easyui.css");
		    		}
		    		if(skin == "black"){
		    			$("#themesSkin").attr("href","/jslib/jquery-easyui-1.7.0//themes/black/easyui.css");
		    		}
		    		if(skin == "gray"){
		    			$("#themesSkin").attr("href","/jslib/jquery-easyui-1.7.0//themes/gray/easyui.css");
		    		}
		    		if(skin == "bootstrap"){
		    			$("#themesSkin").attr("href","/jslib/jquery-easyui-1.7.0//themes/bootstrap/easyui.css");
		    		}
		        }
		    }); 

	});
	function logoutFun() {
		$.messager.confirm('确认', '确定要退出系统吗？', function(r) {
			if (r) {
				$.getJSON('/sysUserController/logout', function(result) {
					location.replace('/login.html');
				});
			}
		});
	}

	function userInfoFun() {
		$('<div/>').dialog({
			href : '/page/admin/updateUserPwd.html',
			width : 560,
			height : 230,
			modal : true,
			title : '修改密码',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					if(passFlag == false)
						return false;
					if(passFormatFlag == false)
						return false;
					$.post('/sysUserController/modifyPwd',
						$("#adminPwdUpdateForm").serializeArray(),function(r){
							if (r.success) {	
								d.dialog('destroy');
								$.messager.confirm('确认',  r.msg+"密码已修改,是否重新登录？", function(r) {
								if (r) {
									$.getJSON('/sysUserController/logout', function(result) {
										location.replace('/login.html');
									});
									}
								});
							}else{
								$.messager.alert('提示', '密码修改失败:'+r.msg);
							}
						}
					);
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
	}
	
</script>
</head>
<body  style="border:none;visibility:visible;" onload="showTime();">
	
	<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
		<!-- 页面顶部top及菜单栏 -->  
	   <div class="body_header" data-options="region:'north',border:false" style="height:60px; padding:5px; background:#F3F3F3"> 
	    <a href="#"><span  class="northTitle"><img src="/style/images/logo.png" width="266" height="46"></span></a>
	     <span class="header-right">
	       <p><strong class="easyui-tooltip">${session_user_name}</strong>，欢迎您！</p>
            <p><a onclick="javascript:userInfoFun()" href="#">修改密码</a>|<a onclick="javascript:logoutFun()" href="#">安全退出</a></p>   
			<!-- 	皮肤切换:<select class="easyui-combobox" id="skin" value="metro" panelHeight="auto" style="width: 80px;" >
				<option value="gray">gray</option>
				<option value="metro">metro</option>
				<option value="default">default</option>
				<option value="black">black</option>
				<option value="bootstrap">bootstrap</option>
				</select> -->
	     </span>
	    </div>  
	  
		<!-- 左侧导航菜单 -->	    
	    <div region="west" iconCls="icon-chart-organisation"  title="菜单导航" style="width:180px;top:59px;border-bottom: none;border-right: none">
			<div class="easyui-accordion" fit="true" style="text-align: center;" id="accordionPanel" >
				<sec:authorize url="/systemManage">
					<div title="系统管理" id="menu_2"  >
						<ul style="text-align: left;" >
						<sec:authorize url="/sysUserController/querySysUser"><li><a onmouseover="this.style.cursor='hand'" onclick="javascript:setPageContent('&nbsp;&nbsp;系统管理&gt&gt用户管理','/page/admin/userList');">用户管理</a></li></sec:authorize>
						<sec:authorize url="/sysUserController/queryRole"><li><a onmouseover="this.style.cursor='hand'" onclick="javascript:setPageContent('&nbsp;&nbsp;系统管理&gt&gt角色权限管理','/page/admin/roleList');">角色权限管理</a></li></sec:authorize>
						 <sec:authorize url="/sysUserController/querySystemLog"><li><a onmouseover="this.style.cursor='hand'" onclick="javascript:setPageContent('&nbsp;&nbsp;系统管理&gt&gt操作日志查询','/page/admin/logList');">操作日志查询</a></li></sec:authorize>
						</ul>
					</div>
				</sec:authorize>
			</div>	   
	    </div>  
	  <!-- 主显示区域选项卡界面 title="主显示区域"-->
	    <div id="operator_admin_panel" iconCls="icon-tip" region="center" style="border: none;padding-left: 3px;padding-bottom: 30px">
	    </div>  
	    
	      <!-- 页面底部信息 -->
	   
<!-- 页脚信息 -->
<div data-options="region:'south',border:false" style="height:20px; background:#F3F3F3; padding:2px; vertical-align:middle;">
	<span id="sysVersion">系统版本：V1.0</span>
    <span id="nowTime"></span>
</div>
	</div>
</body>
</html>
