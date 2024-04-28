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
    const response = await axios.get(`${baseUrl}cuahangs/getCuaHang/${account.id}`);

    // Dữ liệu từ API
    const hoaDonList = response.data;

    res.render('cuaHangLienKet', { title: 'Express', data: hoaDonList, account: account });
  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).send('Error fetching data');
  }
});

router.get('/searchCuaHang', authenticateToken, async function(req, res, next) {
  try {
    const account = {
      id: req.userId,
      diaChi:req.diaChi,
      username:req.username,
      email:req.email,
      sdt:req.sdt
    }

    const queryParams = new URLSearchParams({
      trangThai: req.query.trangThai.trim(),
      diaChi: req.query.diaChi.trim(),
      username: req.query.username.trim(),
      email: req.query.email.trim(),
      sdt: req.query.sdt.trim()
    });
    // console.log("formData: ",queryParams)
    const response = await axios.get(`${baseUrl}cuahangs/searchCuaHang/${account.id}?${queryParams}`);
    const list = response.data;

    res.render('cuaHangLienKet', { title: 'Express', data: list, account: account });
  } catch (error) {
    console.error('Error fetching data:', error);
    res.status(500).send('Error fetching data');
  }
});

module.exports = router;
