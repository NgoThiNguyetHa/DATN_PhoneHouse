var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const axios = require('axios');
const {authenticateToken, baseUrl} = require('../middleware/index');

// require('../models/CuaHang')
// const CuaHang = mongoose.model("cuaHang");

router.get('/', authenticateToken, async function(req, res, next) {
  try {
    const account = {
      id: req.userId,
      diaChi:req.diaChi,
      username:req.username,
      email:req.email,
      sdt:req.sdt
    }

    // Gọi API để lấy danh sách hóa đơn dựa trên mã cửa hàng
    const response = await axios.get(`${baseUrl}hoadons/getHoaDonTheoCuaHang/${account.id}`);
    //
    // // Dữ liệu hóa đơn từ API
    const hoaDonList = response.data;
    // console.log("hoadon: ", hoaDonList)

    res.render('quanLyDonHang', { title: 'Express', data: hoaDonList, account: account });
  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).send('Error fetching data');
  }
});

module.exports = router;