var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/KhachHang')

const KhachHang = mongoose.model("khachhang");
router.post('/addKhachHang', async (req, res, next) =>{
    try {
        const {username, password, diaChi, email, sdt} = req.body;
        const newKhachHang = new KhachHang({
            username: username,
            password: password,
            email: email,
            diaChi: diaChi,
            sdt: sdt
        });
        if (!username || !password || !email || !diaChi || !sdt) {
            const errorMessage = "Vui lòng nhập đủ thông tin";
            return res.status(201).json({
                errorMessage: errorMessage,
                data: newKhachHang,
            });
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailRegex.test(email)) {
            const errorMessage = "Email không hợp lệ";
            return res.status(201).json({
                errorMessage: errorMessage,
                data: newKhachHang,
            });
        }
        const successMessage = "Successfully!";
        const saveKH = await newKhachHang.save();
        return res.status(201).json({
            successMessage: successMessage,
            data: saveKH,
        });
    }catch (err) {
        res.status(500).json(err);
    }
});

router.get('/getAllKhachHang', async (req,res) => {
    try {
      const khachHang = await KhachHang.find();
      res.json(khachHang);
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  })

  
router.post('/dangNhapKhachHang', async (req, res) => {
    try {
        const { email, password } = req.body;
        if (!email || !password) {
            return res.status(400).json({ errorMessage: 'Vui lòng nhập email và mật khẩu.' });
        }

        const khachHang = await KhachHang.findOne({ email });

        if (!khachHang) {
            return res.status(401).json({ errorMessage: 'Email không tồn tại.' });
        }
        console.log(khachHang.password)
        const isPasswordValid = await bcrypt.compare(password, khachHang.password)

        if (password !== khachHang.password) {
            return res.status(401).json({ errorMessage: 'Mật khẩu không đúng.' });
        }

        return res.status(200).json({ successMessage: 'Đăng nhập thành công.', khachHang });
    } catch (error) {
        console.error(error);
        res.status(500).json({ errorMessage: 'Lỗi server.' });
    }
});

module.exports = router;
