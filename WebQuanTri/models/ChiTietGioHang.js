const mongoose = require('mongoose');

const ChiTietGioHangSchema = mongoose.Schema({
    soLuong:{type: String},
    giaTien:{type: String},
    maChiTietDienThoai:{type: mongoose.Schema.Types.ObjectId, ref: 'chitietdienthoai'},
    maGioHang:{type: mongoose.Schema.Types.ObjectId, ref: 'gioHang'}
    
});
module.exports = mongoose.model('chiTietGioHang', ChiTietGioHangSchema);