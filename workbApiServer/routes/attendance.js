module.exports = function(app,Attendance){

    // CREATE ATTENDANCE
    app.post('/api/attend', function(req,res){
        let attendance = new Attendance();
        attendance.userid = req.body.userid;
        attendance.year = req.body.year;
        attendance.month = req.body.month;
        attendance.day = req.body.day;
        attendance.hour = req.body.hour;
        attendance.min = req.body.min;
        attendance.sec = req.body.sec;
        
        attendance.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});
        });
        
    });

   
    // GET ATTENDANCE
    app.get('/api/attend/:userid', function(req,res){
        Attendance.find({userid: req.params.userid}, function(err, attendances){
            if(err) return res.json({result:0})
            if(attendances.length === 0) return res.json({result:0});
            res.json(attendances);
        })
    });

}