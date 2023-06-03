var express = require('express');
var router = express.Router();

const authorizationController = require('../controllers/authorizationController')

router.post('/login', authorizationController.loginUser);

router.get('/register', function(req, res, next) {
    res.render('index', { title: 'Express' });
});

module.exports = router;
