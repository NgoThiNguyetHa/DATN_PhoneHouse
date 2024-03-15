var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const axios = require('axios');
// require('../models/CuaHang')
//
// const CuaHang = mongoose.model("cuaHang");
/* GET home page. */
const baseUrl = 'http://localhost:8686/'
router.get('/', async function(req, res, next) {
  // try {
  //   const response = await axios.get(`${baseUrl}getHoaDonTheoCuaHang/${accountId}`);
  //   const hoaDonList = response.data;
    res.render('quanLyDonHang', { title: 'Express' });
  // } catch (error) {
  //   console.error(error);
  //   res.status(500).send('Internal Server Error');
  // }
});

module.exports = router;