<%--
  Created by IntelliJ IDEA.
  User: YuChen Zhang
  Date: 17/11/07
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">


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
    <style>
        body {
            background: url("bak.jpg") no-repeat;
            background-size:cover;
        }
    </style>
    <script>
        function validateUserEmail() {
            var flag = true;
            var uemail = $("#useremail").val();
            if(uemail.trim()==''){
                $("#useremailmsg").html("<span style='color:red'>邮箱不能为空</span>");
                $("#validateUserEmail").attr("class", "form-group has-error");
                $("#tijiao").attr("class", "btn btn-primary disabled");
                return false;
            }
            var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            flag = reg.test(uemail);
            if(flag==false){
                $("#useremailmsg").html("<span style='color:red'>邮箱格式不正确</span>");
                $("#validateUserEmail").attr("class", "form-group has-error");
                $("#tijiao").attr("class", "btn btn-primary disabled");
                return false;
            }
            $.ajax({
                url: "/user/findByEmail",
                data: {"useremail": $("#useremail").val()},
                success: function (data1) {
                    if (data1 == "ok") {
                        flag = false;
                        $("#useremailmsg").html("<span style='color:red'>该邮箱不存在</span>");
                        $("#validateUserEmail").attr("class", "form-group has-error");
                        $("#sub").attr("class", "btn btn-primary disabled");
                        return false;
                    } else {
                        $("#useremailmsg").html(null);
                        $("#validateUserEmail").attr("class", "form-group has-success");
                        $("#sub").attr("class", "btn btn-primary");
                        return true;
                    }
                }
            });
        }
        function validateVerifCode() {
            if ($("#verifCode").val() == "") {
                $("#verifCodeMsg").html("<span style='color:red'>验证码不能为空</span>");
                $("#validateVerifCode").attr("class", "form-group has-error");
                $("#sub").attr("class", "btn btn-primary disabled");
                return false;
            } else {
                $("#verifCodeMsg").html(null);
                $("#validateVerifCode").attr("class", "form-group has-success");
                $("#sub").attr("class", "btn btn-primary");
                return true;
            }
        }
        function validateUpwd() {
            if ($("#upwd").val() == "") {
                $("#upwdMsg").html("<span style='color:red'>密码不能为空</span>");
                $("#validateUpwd").attr("class", "form-group has-error");
                $("#sub").attr("class", "btn btn-primary disabled");
                return false;
            } else {
                $("#upwdMsg").html("");
                $("#validateUpwd").attr("class", "form-group has-success");
                $("#sub").attr("class", "btn btn-primary");
                return true;
            }
        }
        function validateUpwdAgaid() {
            if ($("#upwdagain").val() == "") {
                $("#upwdagainMsg").html("<span style='color:red'>重复密码不能为空</span>");
                $("#validateUpwdAgaig").attr("class", "form-group has-error");
                $("#sub").attr("class", "btn btn-primary disabled");
                return false;
            } else {
                $("#upwdagainMsg").html("");
                $("#validateUpwdAgaig").attr("class", "form-group has-success");
                $("#sub").attr("class", "btn btn-primary");
            }
            if ($("#upwdagain").val() != $("#upwd").val()) {
                $("#upwdMsg").html("<span style='color:red'>两次密码不一致</span>");
                $("#upwdagainMsg").html("<span style='color:red'>两次密码不一致</span>");
                $("div[id*=validateUpwdAgaig]").attr("class", "form-group has-error");
                $("#sub").attr("class", "btn btn-primary disabled");
                return false;
            } else {
                $("#upwdMsg").html(null);
                $("#upwdagainMsg").html(null);
                $("div[id*=validateUpwdAgaig]").attr("class", "form-group has-success");
                $("#sub").attr("class", "btn btn-primary");
                return true;
            }
        }
        $(function () {
            $("#useremail").blur(function () {
                validateUserEmail();
            });
            $("#verifCode").blur(function () {
                validateVerifCode();
            });
            $("#upwd").blur(function () {
                validateUpwd();
            });
            $("#upwdagain").blur(function () {
                validateUpwdAgaid();
            });
            $("#sub").click(function () {
                var flag = true;
                flag = validateUserEmail();
                flag = validateVerifCode();
                flag = validateUpwd();
                flag = validateUpwdAgaid();
                if (flag == true) {
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
                            if (data.indexOf("true") > 0) {
                                $("#msg").append("页面即将跳转至登陆页面")
                                setTimeout('hreftologin()', 3000);
                            }
                        }
                    });
                    return false;
                } else {
                    return false;
                }
            });
        })
    </script>
    <script>
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return unescape(r[2]);
            return null;
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
        function hreftologin() {
            window.location.href = "/login?service=" + GetQueryString('service');
        }
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
                    <form action="/user/modifyPassword" method="post" class="form-horizontal" role="form"
                          id="modifyUserForm">
                        <div class="form-group" id="validateUserEmail">
                            <label for="useremail" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-lg-8">
                                <input type="text" name="useremail" id="useremail" class="form-control"
                                       placeholder="请输入邮箱"><a href="###" onclick="getVerifCodeByEmail()">获取验证码</a>
                                <div id="useremailmsg"></div>
                            </div>
                        </div>
                        <div class="form-group" id="validateVerifCode">
                            <label for="VerifCode" class="col-sm-2 control-label">验证码:</label>
                            <div class="col-lg-8">
                                <input type="text" name="verifCode" id="verifCode" class="form-control"
                                       placeholder="请输入验证码">
                            </div>
                            <div id="emailMsg"></div>
                            <div id="verifCodeMsg"></div>
                        </div>
                        <div class="form-group" id="validateUpwd">
                            <label for="upwd" class="col-sm-2 control-label">新密码:</label>
                            <div class="col-lg-8">
                                <input type="password" id="upwd" class="form-control"
                                       placeholder="请输入新密码" name="upwd">
                            </div>
                            <div id="upwdMsg"></div>
                        </div>
                        <div class="form-group" id="validateUpwdAgaig">
                            <label for="upwdagain" class="col-sm-2 control-label">重复新密码:</label>
                            <div class="col-lg-8">
                                <input type="password" id="upwdagain" class="form-control"
                                       placeholder="请重复新密码">
                            </div>
                            <div id="upwdagainMsg"></div>
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
