var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const axios = require('axios');
const {authenticateToken, baseUrl} = require('../middleware/index');
// require('../models/CuaHang')
// const CuaHang = mongoose.model("cuaHang");

router.get('/', authenticateToken, async function(req, res, next) {
  const account = {
    id: req.userId,
    diaChi:req.diaChi,
    username:req.username,
    email:req.email,
    sdt:req.sdt
  }
  res.render('thongKe', {
    title: 'Express',
    account: account
  });
});

module.exports = router;