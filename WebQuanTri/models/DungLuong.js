const mongoose = require('mongoose');

const DungLuongSchema = mongoose.Schema({
    boNho:{type: String},
});
module.exports = mongoose.model('dungluong', DungLuongSchema);