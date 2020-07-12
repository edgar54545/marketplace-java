./mongo localhost:27017/marketplace --eval "db.createUser({user: \"product-service\",pwd: \"password\",roles: [\"readWrite\"]});"
