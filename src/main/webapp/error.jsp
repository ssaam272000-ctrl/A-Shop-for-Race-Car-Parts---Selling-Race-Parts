<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error - A Shop for Race Car Parts</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 80px auto;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            padding: 40px;
            text-align: center;
        }

        h1 {
            color: #dc3545;
            font-size: 28px;
            margin-bottom: 15px;
        }

        p {
            color: #333;
            font-size: 16px;
            margin-bottom: 25px;
        }

        .btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 25px;
            margin: 8px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .small {
            color: #777;
            font-size: 14px;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>⚠️ Oops! Something went wrong</h1>

    <p><%= request.getAttribute("error") != null ? request.getAttribute("error") : "An unexpected error occurred." %></p>

    <div>
        <a href="products"><button class="btn">🛒 Back to Shop</button></a>
        <a href="viewHistory"><button class="btn">📜 View Order History</button></a>
    </div>

    <p class="small">If this keeps happening, please contact the developer.</p>
</div>

</body>
</html>
