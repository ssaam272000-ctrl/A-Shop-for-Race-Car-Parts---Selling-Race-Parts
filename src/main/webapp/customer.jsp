<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enter Customer Name | Inverse Ordering System</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .box {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            text-align: center;
            width: 350px;
        }

        h2 {
            color: #222;
            margin-bottom: 15px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin: 15px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 1rem;
        }

        button {
            background: #007bff;
            color: white;
            border: none;
            padding: 10px 18px;
            border-radius: 6px;
            font-size: 1rem;
            cursor: pointer;
            transition: 0.2s;
        }

        button:hover {
            background: #0056b3;
        }

        .note {
            font-size: 13px;
            color: #666;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="box">
    <h2>Welcome to Inverse Ordering System</h2>
    <form action="setCustomer" method="post">
        <label for="name">Enter your name:</label><br>
        <input type="text" id="name" name="customerName" placeholder="e.g. Shivani" required>
        <br>
        <button type="submit">Continue to Shop</button>
    </form>
    <p class="note">We'll remember you until you close the browser.</p>
</div>

</body>
</html>
