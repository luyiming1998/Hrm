<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/9/27
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>DataGrid Pagination - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="https://www.jeasyui.com/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="https://www.jeasyui.com/easyui/themes/icon.css">
    <script type="text/javascript" src="https://www.jeasyui.com/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
</head>
<body>
<div style="margin-bottom:10px">
</div>

<table id="tt" class="easyui-datagrid" style="width:700px;height:250px"
       data-options="rownumbers:true,singleSelect:true,url:'datagrid_data1.json',method:'get',toolbar:toolbar">
    <thead>
    <tr>
        <th field="id" width="80" align="center">用户ID</th>
        <th field="loginName" width="120" align="center">登录名</th>
        <th field="password" width="80" align="center">密码</th>
        <th field="username" width="80" align="center">用户名</th>
        <th field="status" width="60" align="center">状态</th>
        <th field="create_date" width="200" align="center">创建时间</th>
    </tr>
    </thead>
</table>
<script type="text/javascript">
    var toolbar = [{
        id:'button-import',
        text:'导入',
        iconCls:'icon-redo',
        handler:function(){alert('import')}
    },{
        id:'button-export',
        text:'导出',
        iconCls:'icon-undo',
        handler:function(){alert('export')}
    }
    ];
    function changeP(){
        var dg = $('#tt');
        dg.datagrid('loadData',[]);
        dg.datagrid({pagePosition:$('#p-pos').val()});
        dg.datagrid('getPager').pagination({
            layout:['list','sep','first','prev','sep',$('#p-style').val(),'sep','next','last','sep','refresh','info']
        });
    }
</script>
</body>
</html>