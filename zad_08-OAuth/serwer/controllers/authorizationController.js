const fs = require('fs');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const loginUser = async (req, res) => {
    email = req.body.email
    password = req.body.password

    let rawdata = fs.readFileSync('data.json');
    let users = JSON.parse(rawdata);
    const foundUser = users.find(user => user.email === email);

    if (!foundUser) {
        res.status(404).send({})
    } else {
        if (!bcrypt.compareSync(password, foundUser.password)) {
            res.status(401).send({})
        } else {
            const token = jwt.sign({
                email: foundUser.email,
                password: foundUser.password
            }, 'key', {expiresIn: '1h'}, null);
            foundUser.token = token

            fs.writeFileSync('data.json', JSON.stringify(users));
            res.json({"user": foundUser.name, "id": foundUser.id, "token": token})
        }
    }
}

const logoutUser = async (req, res) => {
    user_id = req.body.id

    let rawdata = fs.readFileSync('data.json');
    let users = JSON.parse(rawdata);
    const foundUser = users.find(user => user.id === user_id);

    if (!foundUser) {
        res.status(404).send({})
    } else {
        foundUser.token = ""
        fs.writeFileSync('data.json', JSON.stringify(users));
        res.status(200).send({})
    }
}

module.exports = {
    loginUser,
    logoutUser
}
