const mongoose = require('mongoose');

const HangSanXuatSchema = mongoose.Schema({
    tenHang:{type: String},
    anh: {type: String},
    maCuaHang:{type: mongoose.Schema.Types.ObjectId, ref: 'cuaHang'}
});
module.exports = mongoose.model('hangSanXuat', HangSanXuatSchema);