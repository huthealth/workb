//index.js
let randomstring = require('randomstring');

module.exports = function(app,Company){
    
    // GET COMPANY
    app.get('/api/company/:userid', function(req,res){
        Company.find({userid: req.params.userid}, function(err, companys){
            if(err) return res.json({result: 0});
            if(companys.length === 0) return res.json({result: 0});
            res.json({result: 1 , "companyname" : companys[0].companyname, "address":companys[0].address});
        })
    });



    // CREATE NEW COMPANY
    app.post('/api/company', function(req,res){
        let company = new Company();
        company.userid = req.body.userid;
        company.companyname = req.body.companyname;
        company.address = req.body.address;

        company.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }
            

            res.json({result: 1 });
        });
    });


}