const mongoose = require('mongoose');

const UuDaiSchema = mongoose.Schema({
    giamGia:{type: String},
    thoiGian: {type: String},
    trangThai: {type: String}
});
module.exports = mongoose.model('uudai', UuDaiSchema);