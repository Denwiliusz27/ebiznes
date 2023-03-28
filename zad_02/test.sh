curl -v -d '{"name": "test", "author": "test author", "price": 234, "amount": 334, "description": "test"}' -H 'Content-Type: application/json' -X POST localhost:9000/products/create
