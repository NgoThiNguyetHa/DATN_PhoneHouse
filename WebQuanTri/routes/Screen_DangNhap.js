var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/CuaHang')

const CuaHang = mongoose.model("cuaHang");
/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('dangNhap', { title: 'Express' });
});

router.post('/', async (req, res, next) => {
  try {
    const { email, password } = req.body;
    if (!email || !password) {
      // return res.status(400).json({ errorMessage: 'Vui lòng nhập email và mật khẩu.' });
      return res.render('dangNhap', {
        title: "Đăng nhập",
        errorMessage: "Vui lòng nhập email và mật khẩu.",
      })
    }

    const cuaHang = await CuaHang.findOne({ email });

    if (!cuaHang) {
      // return res.status(401).json({ errorMessage: 'Email không tồn tại.' });
      return res.render('dangNhap', {
        title: "Đăng nhập",
        errorMessage: "Email không tồn tại.",
      })
    }

    if (password !== cuaHang.password) {
      // return res.status(401).json({ errorMessage: 'Mật khẩu không đúng.' });
      return res.render('dangNhap', {
        title: "Đăng nhập",
        errorMessage: "Mật khẩu không đúng.",
      })
    }
    if (cuaHang.phanQuyen !== "admin"){
      return res.render('dangNhap', {
        title: "Đăng nhập",
        errorMessage: "Bạn chưa được cấp quyền.",
      })
    }

    // return res.status(200).json({ successMessage: 'Đăng nhập thành công.', cuaHang });
    return res.render('index', {
      title: "Đăng nhập",
      successMessage: 'Đăng nhập thành công.',
      account: cuaHang
    })
  } catch (error) {
    console.error(error);
    res.status(500).json({ errorMessage: 'Lỗi server.' });
  }
});


module.exports = router;
