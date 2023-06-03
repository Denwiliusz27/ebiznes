const fs = require('fs');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const loginUser = async(req, res) => {
    email = req.body.email
    password = req.body.password

    console.log("mam: " + email + ", " + password)

    let rawdata = fs.readFileSync('data.json');
    let users = JSON.parse(rawdata);

    const foundUser = users.find(user =>user.email === email);

    if (!foundUser) {
        console.log("Użytkownik o mailu " + email + " nie istnieje")
        res.status(404).send({})
    } else {
        if (!bcrypt.compareSync(password, foundUser.password)) {
            console.log("Podano nieprawidłowe hasło")
            res.status(401).send({})
        } else {
            const token = jwt.sign({ email: foundUser.email, password: foundUser.password }, 'key', { expiresIn: '1h' }, null);
            res.json({ "user": foundUser.name, "token": token})
        }
    }
}

module.exports = {
    loginUser
}
