let mongoose = require('mongoose');
let Schema = mongoose.Schema;

let userSchema = new Schema({
    userid: String,
    passwd: String,
    name: String,
    phone: String,
    auth: {type:Number, default:-1}, // 고용주 0 근로자 1
});

module.exports = mongoose.model('user',userSchema);