module.exports = function(app,Offwork){

    // CREATE OFFWORK
    app.post('/api/offwork', function(req,res){
        let offwork = new Offwork();
        offwork.userid = req.body.userid;
        offwork.year = req.body.year;
        offwork.month = req.body.month;
        offwork.day = req.body.day;
        offwork.hour = req.body.hour;
        offwork.min = req.body.min;
        offwork.sec = req.body.sec;
        
        offwork.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});
        });
        
    });

   
    // GET OFFWORK BY ID
    app.get('/api/offwork/:userid', function(req,res){
        Offwork.find({userid: req.params.userid}, function(err, offworks){
            if(err) return res.json({result:0})
            if(offworks.length === 0) return res.json({result:0});
            res.json(offworks);
        })
    });

    // GET OFFWORK BY ID + DAY
    app.post('/api/offwork/day', function(req,res){
        Offwork.find({userid: req.body.userid, day: req.body.day}, function(err, offworks){
            if(err) return res.json({result: 0});
            if(offworks.length === 0) return res.json({result: 0})
            res.json(offworks)
        })
    });
    
  
}