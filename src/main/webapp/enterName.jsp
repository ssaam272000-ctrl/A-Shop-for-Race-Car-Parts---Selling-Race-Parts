<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enter Name - Inverse Ordering System</title>
    <style>
        body { font-family: 'Segoe UI'; background: #f4f4f4; text-align:center; padding-top:100px; }
        form { background:white; display:inline-block; padding:30px; border-radius:10px;
               box-shadow:0 4px 8px rgba(0,0,0,0.1); }
        input[type=text] { padding:10px; width:220px; border-radius:5px; border:1px solid #ccc; }
        button { margin-top:15px; background:#007bff; color:white; border:none;
                 padding:10px 20px; border-radius:5px; cursor:pointer; }
        button:hover { background:#0056b3; }
    </style>
</head>
<body>
    <h2>Enter your name to proceed with the order</h2>
    <form action="setCustomer" method="post">
        <input type="hidden" name="productId" value="${productId}">
        <input type="text" name="customerName" placeholder="Your Name" required>
        <br><button type="submit">Continue</button>
    </form>
</body>
</html>
