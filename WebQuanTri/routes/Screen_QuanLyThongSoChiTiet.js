var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
// require('../models/CuaHang')
//
// const CuaHang = mongoose.model("cuaHang");
/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('quanLyThongSoChiTiet', { title: 'Cửa hàng liên kết' });
});

module.exports = router;