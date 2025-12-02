<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Select User</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; text-align: center; margin-top: 120px; }
        h2 { color: #007bff; }
        button {
            margin: 10px;
            padding: 10px 20px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            border: none;
        }
        .continue {
            background-color: #007bff;
            color: white;
        }
        .new-user {
            background-color: #28a745;
            color: white;
        }
        button:hover { opacity: 0.85; }
    </style>
</head>
<body>
    <h2>Welcome back, <%= session.getAttribute("customerName") %>!</h2>
    <p>Would you like to continue as this user or start as a new user?</p>

    <form action="products" method="get" style="display:inline;">
        <input type="hidden" name="fromChoice" value="true">
        <button type="submit" class="continue">Continue as <%= session.getAttribute("customerName") %></button>
    </form>

    <form action="logout" method="post" style="display:inline;">
        <button type="submit" class="new-user">Start as New User</button>
    </form>
</body>
</html>
