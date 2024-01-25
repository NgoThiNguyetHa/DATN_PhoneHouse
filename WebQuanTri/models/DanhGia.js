const mongoose = require('mongoose');

const DanhGiaSchema = mongoose.Schema({
    noiDung:{type: String},
    hinhAnh:{type: String},
    diemDanhGia:{type: Number},
    ngayTao:{type: String},
    idKhachHang:{type: mongoose.Schema.Types.ObjectId, ref: 'khachhang'},
    idDienThoai:{type: mongoose.Schema.Types.ObjectId, ref: 'dienthoai'}
});
module.exports = mongoose.model('danhgia', DanhGiaSchema);