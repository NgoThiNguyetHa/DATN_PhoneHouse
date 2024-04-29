const mongoose = require('mongoose');

const ThongBaoSchema = mongoose.Schema({
  noiDung: {type: String},
  thoiGian: {type: String},
  trangThai: {type: String},
  maTaiKhoan: {type: String},
  phanQuyen: {type: String},
  maHoaDon: {type: mongoose.Schema.Types.ObjectId, ref: 'hoaDon'}
});
module.exports = mongoose.model('thongbao', ThongBaoSchema);
