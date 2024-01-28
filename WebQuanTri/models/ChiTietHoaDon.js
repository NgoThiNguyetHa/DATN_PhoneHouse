const mongoose = require('mongoose');

const ChiTietHoaDonSchema = mongoose.Schema({
    soLuong:{type: Number},
    giaTien:{type: String},
    maHoaDon:{type: mongoose.Schema.Types.ObjectId, ref: 'hoaDon'},
    maDienThoai:{type: mongoose.Schema.Types.ObjectId, ref: 'dienthoai'}
});
module.exports = mongoose.model('chiTietHoaDon', ChiTietHoaDonSchema);