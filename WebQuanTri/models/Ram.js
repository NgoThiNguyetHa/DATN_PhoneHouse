const mongoose = require('mongoose');

const RamSchema = mongoose.Schema({
    RAM:{type: String},
    giaTien: {type: Number},
});
module.exports = mongoose.model('ram', RamSchema);