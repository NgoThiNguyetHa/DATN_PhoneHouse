const mongoose = require('mongoose');

const GioHangSchema = mongoose.Schema({
    soLuong:{type: String},
    tongTien:{type: String},
    maKhachHang:{type: mongoose.Schema.Types.ObjectId, ref: 'khachhang'},
});
module.exports = mongoose.model('gioHang', GioHangSchema);