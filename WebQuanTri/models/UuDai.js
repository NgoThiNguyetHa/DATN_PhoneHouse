const mongoose = require('mongoose');

const UuDaiSchema = mongoose.Schema({
    giamGia:{type: String},
    soLuong: {type: Number},
    thoiGian: {type: String},
    trangThai: {type: String}
});
module.exports = mongoose.model('uudai', UuDaiSchema);