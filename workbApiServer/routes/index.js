//index.js
let randomstring = require('randomstring');

module.exports = function(app,User){
    
    // GET ALL USERS
    app.get('/api/users', function(req,res){
        User.find(function(err, users){
            if(err) return res.status(500).send({error: 'database failure'});
            res.json(users);
        })
    });

    // GET SINGLE USERS
    app.get('/api/users/:userid', function(req,res){
        User.find({userid: req.params.userid}, function(err, users){
            if(err) return res.json({result:0});
            if(users.length === 0) return res.json({result : 0});
            res.json({result: 1 , "name" : users[0].name, "phone": users[0].phone});
        })
    });

    // LOGIN
    app.post('/api/users/login', function(req,res){
        User.find({userid: req.body.userid, passwd: req.body.passwd}, function(err, users){
            if(err) return res.json({result: 0});
            if(users.length === 0) return res.json({result: 0})
            res.json({result: 1, "userid" : users[0].userid , "auth" : users[0].auth})
        })
    });

    // CREATE NEW USER
    app.post('/api/users', function(req,res){
        let user = new User();
        user.userid = req.body.userid;
        user.passwd = req.body.passwd;
        user.name = req.body.name;
        user.auth = req.body.auth;
        user.phone = req.body.phone;

        user.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});
        });
    });


}