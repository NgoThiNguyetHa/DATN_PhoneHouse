const mongoose = require('mongoose');

const ChiTietHoaDonSchema = mongoose.Schema({
    soLuong:{type: String},
    giaTien:{type: String},
    maHoaDon:{type: mongoose.Schema.Types.ObjectId, ref: 'hoaDon'},
    maDienThoai:{type: mongoose.Schema.Types.ObjectId, ref: 'dienthoai'}
});
module.exports = mongoose.model('chiTietHoaDon', ChiTietHoaDonSchema);