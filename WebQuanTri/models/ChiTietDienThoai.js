const mongoose = require('mongoose');

const ChiTietDienThoaiSchema = mongoose.Schema({
    dienThoai:{type: String},
    kichThuoc:{type: String},
    manHinh:{type: String},
    camera:{type: String},
    pin:{type: String},
    heDieuHanh:{type: String},
    cpu:{type: String},
    doPhanGiai:{type: String},
    namSanXuat:{type: String},
    thoiGianBaoHanh:{type: String},
    moTaThem:{type: String},
});
module.exports = mongoose.model('chitietdienthoai', ChiTietDienThoaiSchema);