var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const axios = require('axios');
const {authenticateToken, baseUrl} = require('../middleware/index');
// require('../models/CuaHang')
// const CuaHang = mongoose.model("cuaHang");

router.get('/', authenticateToken, async function(req, res, next) {
  try {
    // Nhận mã cửa hàng từ yêu cầu của client
    const userId = req.userId;
    console.log(baseUrl)
    console.log(userId)
    // Gọi API để lấy danh sách hóa đơn dựa trên mã cửa hàng
    // const response = await axios.get(`${baseUrl}dienthoais/getHoaDonTheoCuaHang/${userId}`);
    //
    // // Dữ liệu hóa đơn từ API
    // const hoaDonList = response.data;
    // console.log("hoadon: ", hoaDonList)
    //
    // res.render('quanLyDienThoai', { title: 'Express', data: hoaDonList });
    res.render('quanLyDienThoai', { title: 'Express' });

  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).send('Error fetching data');
  }
});

module.exports = router;