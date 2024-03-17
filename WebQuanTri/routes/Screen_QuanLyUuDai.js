var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
// require('../models/CuaHang')
//
// const CuaHang = mongoose.model("cuaHang");
/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('quanLyUuDai', { title: 'Express' });
});

module.exports = router;