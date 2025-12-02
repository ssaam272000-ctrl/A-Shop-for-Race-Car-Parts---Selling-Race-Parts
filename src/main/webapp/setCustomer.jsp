<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enter Your Name</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; text-align: center; margin-top: 150px; }
        input, button {
            padding: 10px;
            margin-top: 15px;
            font-size: 16px;
            border-radius: 5px;
        }
        input { width: 250px; border: 1px solid #ccc; }
        button {
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            padding: 10px 20px;
        }
        button:hover { background-color: #0056b3; }
    </style>
</head>
<body>
    <h2>Please enter your name to place the order</h2>
    <form action="setCustomer" method="post">
        <input type="text" name="customerName" placeholder="Enter your name" required><br>
        <button type="submit">Continue</button>
    </form>
</body>
</html>
