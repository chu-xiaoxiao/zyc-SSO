<%--
  Created by IntelliJ IDEA.
  User: YuChen Zhang
  Date: 17/11/07
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script
        src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- Bootstrap Core CSS -->
<link href="/assets/vendor/bootstrap/css/bootstrap.min.css"
      rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="/assets/vendor/metisMenu/metisMenu.min.css"
      rel="stylesheet">

<!-- DataTables CSS -->
<link
        href="/assets/vendor/datatables-plugins/dataTables.bootstrap.css"
        rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
        href="/assets/vendor/datatables-responsive/dataTables.responsive.css"
        rel="stylesheet">

<!-- Custom CSS -->
<link href="/assets/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="/assets/vendor/font-awesome/css/font-awesome.min.css"
      rel="stylesheet" type="text/css">

<link rel="stylesheet" href="/css/jquery-confirm.css">
<head>
    <title>修改密码</title>
    <script>
        function GetQueryString(name)

        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }

        function getVerifCodeByEmail() {
            $.ajax({
                url: "/user/getVerifyCodeFromMail.do?useremail=" + $("#useremail").val(),
                type: "get",
                beforeSend: function () {
                    $("#emailMsg").html("正在发送验证码...");
                },
                success: function (data) {
                    $("#emailMsg").html("验证码发送成功...");
                },
                error: function () {
                    $("#emailMsg").html("验证码发送失败...");
                    $("#emailMsg").html('<a href="###" onclick="">重新发送验证码</a>');
                }
            });
        }
        function hreftologin(){
            window.location.href="/login?service="+GetQueryString('service');
        }
        $(function () {
            $("#sub").click(function () {
                $.ajax({
                    url: "/user/modifyPassword",
                    type: "get",
                    data: {
                        "useremail": $("#useremail").val(),
                        "verifCode": $("#verifCode").val(),
                        "upwd": $("#upwd").val()
                    },
                    success: function (data) {
                        $("#msg").html(data);
                        if(data.indexOf("true")>0){
                            $("#msg").append("页面即将跳转至登陆页面")
                            setTimeout('hreftologin()',3000);
                        }
                    }
                });
                return false;
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-5 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">修改密码</h3>
                </div>
                <div class="panel-body">
                    <form action="/user/modifyPassword" method="post" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="useremail" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-lg-8">
                                <input type="text" name="useremail" id="useremail"class="form-control"
                                       placeholder="请输入邮箱"><a href="###" onclick="getVerifCodeByEmail()">获取验证码</a>
                            </div>
                            <span id="emailMsg"></span>
                        </div>
                        <div class="form-group">
                            <label for="VerifCode" class="col-sm-2 control-label">验证码:</label>
                            <div class="col-lg-8">
                                <input type="text" name="VerifCode" id="verifCode"  class="form-control"
                                       placeholder="请输入验证码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="upwd" class="col-sm-2 control-label" >新密码:</label>
                            <div class="col-lg-8">
                                <input type="password" id="upwd" class="form-control"
                                       placeholder="请输入新密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="upwdagain" class="col-sm-2 control-label">重复新密码:</label>
                            <div class="col-lg-8">
                                <input type="password" id="upwdagain" class="form-control"
                                       placeholder="请重复新密码">
                            </div>
                        </div>
                        <div id="msg"></div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <input type="button" class="btn btn-info" value="修改" id="sub"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
