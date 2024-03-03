const mongoose = require('mongoose');

const DienThoaiSchema = mongoose.Schema({
    tenDienThoai: {type: String},
    camera: {type:String},
    kichThuoc : {type: String},
    CPU : {type: String},
    pin : {type : String},
    heDieuHanh: {type :String},
    namSanXuat :{type:String},
    congNgheManHinh: {type:String},
    doPhanGiai:{type:String},
    hinhAnh: {type: String},
    thoiGianBaoHanh:{type : String},
    moTaThem:{type:String},
    maHangSX: {type: mongoose.Schema.Types.ObjectId, ref: 'hangSanXuat'},
    maUuDai: {type: mongoose.Schema.Types.ObjectId, ref: 'uudai'},
});
module.exports = mongoose.model('dienthoai', DienThoaiSchema);