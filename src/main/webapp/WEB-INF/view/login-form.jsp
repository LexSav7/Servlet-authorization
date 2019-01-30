<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored ="false" %>

<!DOCTYPE>
<html lang="en">
<head>
	<title>Login Form</title>
	<meta charset="utf-8">
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous" />

	<!-- Custom CSS -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/form.css"/>" />

</head>
<body>

	<div id="container">
		<div class="mainbox col-md-offset-1 col-md-3">
			<div class="panel panel-info ">
				<div class="panel-heading">
					<span class="panel-title">Login Form</span>
				</div> 
			
				<div class="panel-body">				
					<form action="" method="post" class="form-horizontal col-md-offset-1 col-md-10">

					<c:if test="${param.login != null}">
						<div class="alert alert-danger">
							<i>Invalid username and/or password!</i>
						</div>
					</c:if>

					<c:if test="${param.logout != null}">
						<div class="alert alert-success">
							<i>You have been logged out!</i>
						</div>
					</c:if>

					<c:if test="${param.registration != null}">
						<div class="alert alert-danger">
							<i>User with this username already exists!</i>
						</div>
					</c:if>

							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input name="username" class="form-control" placeholder="Username" required/>
							</div>
						
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input name="password" type="password" class="form-control" placeholder="Password" required/>
							</div>

							<div class="input-group table table-login">
								<label><input name="remember" type="checkbox"/>  Remember me</label>
								<button name="action" value="login" class="btn btn-success">Login</button>
							</div>

                            <div class="input-group table table-registration">
                                <button name="action" value="registration" class="btn btn-link">Register new user</button>
                            </div>
                            </div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>