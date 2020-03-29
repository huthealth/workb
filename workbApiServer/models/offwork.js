let mongoose = require('mongoose');
let Schema = mongoose.Schema;

let offworkSchema= new Schema({
    userid: String,
    year: String,
    month: String,
    day: String,
    hour: String,
    min: String,
    sec: String
});

module.exports = mongoose.model('offwork',offworkSchema);