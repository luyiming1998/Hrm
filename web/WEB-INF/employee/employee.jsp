<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/9/10
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<html>
<head>
    <title>人事管理系统 ——员工管理</title>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
    <meta http-equiv="description" content="This is my page" />
    <link href="css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="js/ligerUI/skins/Aqua/css/ligerui-dialog.css"/>
    <link href="js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="js/jquery-migrate-1.2.1.js"></script>
    <script src="js/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <link href="css/pager.css" type="text/css" rel="stylesheet" />

    <script type="text/javascript">
        $(function(){
            var pno=parseInt('${pno}');
            var job_id=${job_id==null?0:job_id};
            $("#job_id").find("option[value = '"+job_id+"']").attr("selected","selected");
            var name='${name}';
            if(name!=""){
                $("#name").attr("value",name);
            }
            var card_id='${card_id}';
            if(card_id!=""){
                $("#card_id").attr("value",card_id);
            }
            var sex=${sex==null?0:sex};
            $("#sex").find("option[value = '"+sex+"']").attr("selected","selected");

            var phone='${phone}';
            if(phone!=""){
                $("#phone").attr("value",phone);
            }

            var dept_id=${dept_id==null?0:dept_id};
            $("#dept_id").find("option[value = '"+dept_id+"']").attr("selected","selected");

            $("#prePage").click(function () {
                $("#formPno").attr("value",pno-1);
                $("#pageForm").submit();
            })
            $("#nextPage").click(function () {
                $("#formPno").attr("value",pno+1);
                $("#pageForm").submit();
            })
            $("#page").each(function () {
                $(this).click(function () {
                    $("#formPno").attr("value",$(this).text());
                    $("#pageForm").submit();
                })
            })
            /**页面跳转按钮*/
            $("#pager_jump_btn").click(function () {
                var page=$("#pager_jump_page_size").val();
                $("#formPno").attr("value",page)
                $("#pageForm").submit();
            })

            /** 获取上一次选中的部门数据 */
            var boxs  = $("input[type='checkbox'][id^='box_']");

            /** 给全选按钮绑定点击事件  */
            $("#checkAll").click(function(){
                // this是checkAll  this.checked是true
                // 所有数据行的选中状态与全选的状态一致
                boxs.attr("checked",this.checked);
            })

            /** 给数据行绑定鼠标覆盖以及鼠标移开事件  */
            $("tr[id^='data_']").hover(function(){
                $(this).css("backgroundColor","#eeccff");
            },function(){
                $(this).css("backgroundColor","#ffffff");
            })


            /** 删除员工绑定点击事件 */
            $("#delete").click(function(){
                /** 获取到用户选中的复选框  */
                var checkedBoxs = boxs.filter(":checked");
                if(checkedBoxs.length < 1){
                    $.ligerDialog.error("请选择一个需要删除的员工！");
                }else{
                    /** 得到用户选中的所有的需要删除的ids */
                    var ids = checkedBoxs.map(function(){
                        return this.value;
                    })

                    $.ligerDialog.confirm("确认要删除吗?","删除员工",function(r){
                        if(r){
                            // alert("删除："+ids.get());
                            // 发送请求
                            window.location = "emp/doDeleteEmployee.do?ids=" + ids.get();
                        }
                    });
                }
            })
        })
    </script>
</head>
<body>
<!-- 导航 -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr><td height="10"></td></tr>
    <tr>
        <td width="15" height="32"><img src="images/main_locleft.gif" width="15" height="32"></td>
        <td class="main_locbg font2"><img src="images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：员工管理 &gt; 员工查询</td>
        <td width="15" height="32"><img src="images/main_locright.gif" width="15" height="32"></td>
    </tr>
</table>

<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
    <!-- 查询区  -->
    <tr valign="top">
        <td height="30">
            <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
                <tr>
                    <td class="fftd">
                        <form action="emp/toShowEmployee.do" method="post">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">

                            <tr>
                                <td class="font3">
                                    职位：
                                    <select name="job_id" id="job_id" style="width:143px;">
                                        <option value="0">--请选择职位--</option>
                                        <c:forEach items="${jobList}" var="job">
                                            <option value="${job.id}">${job.name}</option>
                                        </c:forEach>
                                    </select>
                                    姓名：<input type="text" name="name" value="${name}">
                                    身份证号码：<input type="text" name="card_id" maxlength="18" value="${card_id}">
                                </td>
                            </tr>
                            <tr>
                                <td class="font3">
                                    性别：
                                    <select name="sex" style="width:143px;">
                                        <option value="0">--请选择性别--</option>
                                        <option value="1">男</option>
                                        <option value="2">女</option>
                                    </select>
                                    手机：<input type="text" name="phone" value="${phone}">
                                    所属部门：<select id="dept_id" name="dept_id" style="width:100px;">
                                    <option value="0">--部门选择--</option>
                                    <c:forEach items="${deptList}" var="dept">
                                        <option value="${dept.id }">${dept.name }</option>
                                    </c:forEach>
                                </select>&nbsp;
                                    <input type="submit" value="搜索"/>
                                    <input id="delete" type="button" value="删除"/>
                                </td>
                            </tr>
                        </table>
</form>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- 数据展示区 -->
    <tr valign="top">
        <td height="20">
            <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
                <tbody><tr class="main_trbg_tit" align="center">
                    <td><input type="checkbox" name="checkAll" id="checkAll"></td>
                    <td>姓名</td>
                    <td>性别</td>
                    <td>手机号码</td>
                    <td>邮箱</td>
                    <td>职位</td>
                    <td>学历</td>
                    <td>身份证号码</td>
                    <td>部门</td>
                    <td>联系地址</td>
                    <td>建档日期</td>

                    <td align="center">操作</td>

                </tr>
                <c:forEach items="${empList}" var="emp">
                <tr id="data_${emp.id}" class="main_trbg" align="center" style="background-color: rgb(255, 255, 255);">
                    <td><input type="checkbox" id="box_${emp.id}" value="${emp.id}"></td>
                    <td>${emp.name}</td>
                    <td>
                        ${emp.sex==0?"女":"男"}
                    </td>
                    <td>${emp.tel}</td>
                    <td>${emp.email}</td>
                    <td>${emp.job.name}</td>
                    <td>${emp.education}</td></td>
                    <td>${emp.card_id}</td>
                    <td>${emp.dept.name}</td>
                    <td>${emp.address}</td>
                    <td>
                        <fmt:formatDate value="${emp.create_date}" pattern="yyyy年MM月dd日"/>
                    </td>

                    <td align="center" width="40px;"><a href="emp/toShowUpdateEmployee.do?id=${emp.id}">
                        <img title="修改" src="images/update.gif"></a>
                    </td>

                </tr>
                </c:forEach>
                </tbody></table>
        </td>
    </tr>
    <!-- 分页标签 -->
    <form action="emp/toShowEmployee.do" method="post" id="pageForm">
        <input type="hidden" name="dept_id" value="${dept_id==null?0:dept_id}">
        <input type="hidden" name="sex" value="${sex==null?0:sex}">
        <input type="hidden" name="job_id" value="${job_id==null?0:job_id}">
        <input type="hidden" name="name" id="name">
        <input type="hidden" name="phone" id="phone">
        <input type="hidden" name="card_id" id="card_id">
        <input type="hidden" name="pno" value="${pno}" id="formPno">
    </form>
    <tr valign="top">
        <td align="center" class="font3">
            <table width="100%" align="center" style="font-size:13px;" class="digg">
                <tbody>
                <tr>
                    <td style="COLOR: #0061de; MARGIN-RIGHT: 3px; PADDING-TOP: 2px; TEXT-DECORATION: none">
                        <c:if test="${pno==1}">
                            <span class="disabled">上一页</span>
                        </c:if>
                        <c:if test="${pno!=1}">
                            <a id="prePage">上一页</a>
                        </c:if>

                        <c:forEach begin="1" end="${totalPage}" var="p">
                            <c:if test="${p==pno}">
                                <span class="current">${p}</span>
                            </c:if>
                            <c:if test="${p!=pno}">
                                <a id="page">${p}</a>
                            </c:if>
                        </c:forEach>

                        <c:if test="${pno==totalPage}"><span class="disabled">下一页</span></c:if>
                        <c:if test="${pno!=totalPage}"><a id="nextPage">下一页</a></c:if>
                        &nbsp;跳转到&nbsp;&nbsp;<input
                            style="text-align: center;BORDER-RIGHT: #aaaadd 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #aaaadd 1px solid; PADDING-LEFT: 5px; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #aaaadd 1px solid; COLOR: #000099; PADDING-TOP: 2px; BORDER-BOTTOM: #aaaadd 1px solid; TEXT-DECORATION: none"
                            type="text" size="2" id="pager_jump_page_size">&nbsp;
                        <input type="button" style="text-align: center;BORDER-RIGHT: #dedfde 1px solid; PADDING-RIGHT: 6px; BACKGROUND-POSITION: 50% bottom; BORDER-TOP: #dedfde 1px solid; PADDING-LEFT: 6px; PADDING-BOTTOM: 2px; BORDER-LEFT: #dedfde 1px solid; COLOR: #0061de; MARGIN-RIGHT: 3px; PADDING-TOP: 2px; BORDER-BOTTOM: #dedfde 1px solid; TEXT-DECORATION: none"
                               value="确定" id="pager_jump_btn">
                    </td>
                </tr>
                <tr align="center">
                    <td style="font-size:13px;"></td>
                </tr>
                <tr>
                    <td style="COLOR: #0061de; MARGIN-RIGHT: 3px; PADDING-TOP: 2px; TEXT-DECORATION: none">总共<span
                            color="red">${totalcount}</span>条记录，当前显示1-${size}条记录。
                    </td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>
<div style="height:10px;"></div>
</body>
</html>