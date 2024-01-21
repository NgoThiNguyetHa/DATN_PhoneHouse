const mongoose = require('mongoose');

const DungLuongSchema = mongoose.Schema({
    boNho:{type: String},
    giaTien: {type: Number},
});
module.exports = mongoose.model('dungluong', DungLuongSchema);