const mongoose = require('mongoose');

const RamSchema = mongoose.Schema({
    RAM:{type: String},
});
module.exports = mongoose.model('ram', RamSchema);