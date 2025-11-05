#!/bin/bash
# ============================================
# Interactive API Tests for Order & Inventory
# ============================================

# Base URLs
ORDER_SERVICE_URL="http://localhost:8080/orders"
INVENTORY_SERVICE_URL="http://localhost:8090/inventory"

# Test 1: Create a new order
echo "==> Test 1: Creating a new order..."
curl -s -X POST $ORDER_SERVICE_URL \
-H "Content-Type: application/json" \
-d '{
  "id":"100",
  "userId":"user_test",
  "items":[{"productId":"A101","quantity":1}],
  "totalAmount":50
}' | jq
echo -e "\n"

# Test 2: Create another order
echo "==> Test 2: Creating another order..."
curl -s -X POST $ORDER_SERVICE_URL \
-H "Content-Type: application/json" \
-d '{
  "id":"101",
  "userId":"user_test2",
  "items":[{"productId":"B202","quantity":2}],
  "totalAmount":120
}' | jq
echo -e "\n"

# Test 3: Optionally query inventory service (if endpoint exists)
# echo "==> Test 3: Query inventory..."
# curl -s $INVENTORY_SERVICE_URL | jq
# echo -e "\n"

echo "==> All curl tests executed."
