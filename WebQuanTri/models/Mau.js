const mongoose = require('mongoose');

const MauSchema = mongoose.Schema({
    tenMau:{type: String},
    giaTien: {type: Number},
});
module.exports = mongoose.model('mau', MauSchema);