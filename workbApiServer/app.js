//app.js (main)

// [LOAD PACKAGES]
let express          = require('express');
let app              = express();
let bodyParser       = require('body-parser');
let mongoose         = require('mongoose');


// [CONFIGURE APP TO USE bodyParser]
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// [CONFIGURE SERVER PORT]
let port = process.env.PORT || 8080;


// [CONFIGURE mongoose]
let db = mongoose.connection;

db.on('error',console.error);
db.once('open', function(){
    console.log("Connected to mongod server");
})

// [CONNECT TO MONGODB SERVER]
mongoose.connect('mongodb://localhost:27017/workbdb');
let User = require('./models/user');
let Attendance = require('./models/attendance');
let Offwork = require('./models/offwork');
let Company = require('./models/company');
let Contract = require('./models/contract')
// [CONFIGURE ROUTER]
let UserRouter = require('./routes/index')(app,User)
let AttendaceRouter = require('./routes/attendance')(app,Attendance)
let OffworkRouter = require('./routes/offwork')(app,Offwork)
let CommpanyRouter = require('./routes/company')(app,Company)
let BlockchainRouter = require('./routes/blockchain')(app)

// [RUN SERVER]
let server = app.listen(port, function() {
    console.log("Express server has started on port " + port)
});