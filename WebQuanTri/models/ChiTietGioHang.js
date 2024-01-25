const mongoose = require('mongoose');

const ChiTietGioHangSchema = mongoose.Schema({
    soLuong:{type: String},
    giaTien:{type: String},
    maDienThoai:{type: mongoose.Schema.Types.ObjectId, ref: 'dienthoai'},
    maGioHang:{type: mongoose.Schema.Types.ObjectId, ref: 'gioHang'}
    
});
module.exports = mongoose.model('chiTietGioHang', ChiTietGioHangSchema);