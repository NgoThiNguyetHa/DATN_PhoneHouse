var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const moment = require('moment');
require('../models/UuDai')

const UuDai = mongoose.model("uudai")

/* GET DienThoai listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});
router.post('/addUuDai',async function (req, res, next) {
  try {
    const uudai = new UuDai({
      giamGia: req.body.giamGia,
      thoiGian: req.body.thoiGian,
      trangThai: req.body.trangThai,
      maCuaHang: req.body.maCuaHang,
    });

    const savedUuDai = await uudai.save(); // Lưu đối tượng
    const populatedUuDai = await UuDai.findById(savedUuDai._id).populate("maCuaHang");

    // console.log(populatedUuDai);
    res.send(populatedUuDai);
  } catch (err) {
    console.log(err);
    res.status(500).send(err); // Trả về lỗi nếu có lỗi xảy ra
  }
});
// lay uu dai theo cua hang
router.get('/getUuDai/:id', async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const uudai = await UuDai.find({maCuaHang: idCuaHang}).populate("maCuaHang");
    res.json(uudai);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})


router.delete('/deleteUuDai/:id', async (req, res) => {
  try {
    const data = await UuDai.findByIdAndDelete(req.params.id)
    if (!data) {
      return res.status(404).json({message: "delete failed"})
    } else {
      return res.status(200).json({message: "delete successful"})
    }
  } catch (err) {
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateUuDai/:id", async (req, res) => {
  try {
    const data = await UuDai.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if (!data) {
      return res.status(404).json({message: "update failed"})

    } else {
      return res.status(200).json({message: "update successful"})

    }
  } catch (err) {
    console.log(err);
    return res.status(500).json({message: err.message})
  }
})

router.put('/updateExpiredStatus', async (req, res) => {
  try {
    const currentDate = moment().startOf('day').format('DD-MM-YYYY'); // Lấy ngày hiện tại
    const expiredVouchers = await UuDai.find({ thoiGian: { $lte: currentDate }, trangThai: 'Hoạt động' });

    for (const voucher of expiredVouchers) {
      await UuDai.findByIdAndUpdate(voucher._id, { trangThai: 'Không hoạt động' });
    }

    res.status(200).json({ message: 'Cập nhật trạng thái voucher thành công' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/getUuDai-Active/:id', async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const uudai = await UuDai.find({maCuaHang: idCuaHang, trangThai: "Hoạt động"}).populate("maCuaHang");
    res.json(uudai);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})

router.get('/searchUuDaiByDiscount/:id', async (req, res) => {
  try {
    const idCuaHang = req.params.id
    const { minDiscount, maxDiscount } = req.query;

    // Kiểm tra xem người dùng đã nhập đúng thông tin chưa
    if (!minDiscount || !maxDiscount) {
      return res.status(400).json({ message: 'Vui lòng nhập đầy đủ thông tin minDiscount và maxDiscount' });
    }

    const uudai = await UuDai.find({
      giamGia: { $gte: parseFloat(minDiscount), $lte: parseFloat(maxDiscount) },
      maCuaHang: idCuaHang
    }).populate("maCuaHang");

    res.json(uudai);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

module.exports = router;
